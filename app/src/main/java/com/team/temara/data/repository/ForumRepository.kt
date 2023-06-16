package com.team.temara.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.team.temara.data.remote.response.ForumList
import com.team.temara.data.remote.response.PostForumResponse
import com.team.temara.data.remote.response.Result
import com.team.temara.data.remote.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
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

    fun postForum(token: String, userId: String, image: File? = null, text: String): LiveData<Result<PostForumResponse>> = liveData {
        emit(Result.Loading)
        val textMediaType = "text/plain".toMediaType()
        val imageMediaType = "image/jpeg".toMediaTypeOrNull()

        val imagePart: MultipartBody.Part? = if (image != null) {
            MultipartBody.Part.createFormData(
                "image",
                image.name,
                image.asRequestBody(imageMediaType)
            )
        } else {
            null
        }

        val descriptionBody = text.toRequestBody(textMediaType)

        try {
            val response = apiService.postForum(
                token,
                userId,
                imagePart,
                descriptionBody
            )

            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
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