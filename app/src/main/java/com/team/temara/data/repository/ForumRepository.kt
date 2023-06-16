package com.team.temara.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.team.temara.data.remote.response.ForumList
import com.team.temara.data.remote.response.Result
import com.team.temara.data.remote.retrofit.ApiService
import java.lang.Exception

class ForumRepository(
    private val apiService: ApiService,
) {
    fun getForum(token: String): LiveData<Result<List<ForumList>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getForum(token)
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                val forum = response.resultForum
                emit(Result.Success(forum))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile

        private var instance: ForumRepository? = null

        fun getInstance(
            apiService: ApiService,
        ): ForumRepository =
            instance ?: synchronized(this) {
                instance ?: ForumRepository(apiService)
            }.also { instance = it }
    }
}