package com.team.temara.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.team.temara.data.datastore.AuthPreferences
import com.team.temara.data.remote.response.LoginResponse
import com.team.temara.data.remote.response.RegisterResponse
import com.team.temara.data.remote.response.Result
import com.team.temara.data.remote.response.UpdateUserResponse
import com.team.temara.data.remote.response.resultData
import com.team.temara.data.remote.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import kotlin.coroutines.CoroutineContext


class AuthRepository(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) : CoroutineScope {

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(
                email, password
            )
            if (response.error) {
                emit(Result.Error(response.status))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(
                name, email, password
            )
            if (response.error) {
                emit(Result.Error(response.status))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUser(token: String, userId: String): LiveData<Result<resultData>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(token, userId)
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                val user = response.result
                emit(Result.Success(user))
            }
        } catch(e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun updateUser(userId: String, name: String, email: String, dateofbirth: String, gender: String, no_hp: String): LiveData<Result<UpdateUserResponse>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.updateUser(
                userId,
                name,
                email,
                dateofbirth,
                gender,
                no_hp
            )

            Log.d("UpdateUserResponse", response.toString())

            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }


    fun getToken(): LiveData<String> = authPreferences.getToken().asLiveData()
    fun getId(): Flow<String> = authPreferences.getId()
    fun setUserToken(token: String) { launch(Dispatchers.IO) {
        authPreferences.setUserToken(token)
    } }
    fun setUserId(userId: String) { launch(Dispatchers.IO) {
        authPreferences.setUserId(userId)
    }}
    fun deleteUserData() { launch(Dispatchers.IO) {
        authPreferences.deleteUserData()
    }}

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(apiService: ApiService, authPreferences: AuthPreferences) =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService, authPreferences)
            }.also { instance = it }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

}