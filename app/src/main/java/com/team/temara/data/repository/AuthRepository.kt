package com.team.temara.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.team.temara.data.datastore.AuthPreferences
import com.team.temara.data.remote.response.LoginResponse
import com.team.temara.data.remote.response.RegisterResponse
import com.team.temara.data.remote.response.Result
import com.team.temara.data.remote.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception
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