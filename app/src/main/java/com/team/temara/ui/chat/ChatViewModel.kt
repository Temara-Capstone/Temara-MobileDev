package com.team.temara.ui.chat

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.team.temara.data.remote.AppModule
import com.team.temara.data.remote.response.Result
import com.team.temara.data.remote.response.resultData
import com.team.temara.data.repository.AuthRepository

class ChatViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun checkToken(): LiveData<String> {
        return authRepository.getToken()
    }

    fun checkId(): LiveData<String> {
        return authRepository.getId().asLiveData()
    }

    fun getUser(token: String, userId: String): LiveData<Result<resultData>> = authRepository.getUser(token, userId)

    class ChatViewModelFactory private constructor(
        private val authRepository: AuthRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ChatViewModel::class.java)) {
                return ChatViewModel(authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var instance: ChatViewModelFactory? = null

            fun getInstance(context: Context): ChatViewModelFactory = instance ?: synchronized(this) {
                instance ?: ChatViewModelFactory(
                    AppModule.provideAuthRepository(context)
                )
            }.also { instance = it }
        }

    }
}