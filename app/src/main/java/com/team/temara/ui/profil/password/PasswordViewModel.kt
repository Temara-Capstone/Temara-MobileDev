package com.team.temara.ui.profil.password

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.team.temara.data.remote.AppModule
import com.team.temara.data.remote.response.Result
import com.team.temara.data.remote.response.resultData
import com.team.temara.data.repository.AuthRepository

class PasswordViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun updatePassword(userId: String, password: String, ) =
        authRepository.updatePassword(userId, password).also {
            Log.d("UpdatePassword", "UserID: $userId, Password: $password")
        }

    fun checkToken(): LiveData<String> {
        return authRepository.getToken()
    }

    fun checkId(): LiveData<String> {
        return authRepository.getId().asLiveData()
    }

    fun getUser(token: String, userId: String): LiveData<Result<resultData>> =
        authRepository.getUser(token, userId)

    class PasswordViewModelFactory private constructor(
        private val authRepository: AuthRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(com.team.temara.ui.profil.password.PasswordViewModel::class.java)) {
                return PasswordViewModel(authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var instance: PasswordViewModelFactory? = null

            fun getInstance(context: Context): PasswordViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: PasswordViewModelFactory(
                        AppModule.provideAuthRepository(context)
                    )
                }.also { instance = it }
        }

    }
}
