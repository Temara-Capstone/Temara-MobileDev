package com.team.temara.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.team.temara.data.datastore.AuthPreferences
import com.team.temara.data.remote.response.ArticleList
import com.team.temara.data.remote.response.Result
import com.team.temara.data.remote.retrofit.ApiService

class ArticleRepository(
    private val apiService: ApiService,
) {
    fun getArticle(token: String): LiveData<Result<List<ArticleList>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getArticle(token)
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                val article = response.resultArticle
                emit(Result.Success(article))
            }
        } catch(e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null

        fun getInstance(
            apiService: ApiService,
        ): ArticleRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(apiService)
            }.also { instance = it }
    }
}