package com.team.temara.ui.main.fragment.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.team.temara.data.remote.AppModule
import com.team.temara.data.remote.response.Result
import com.team.temara.data.remote.response.resultData
import com.team.temara.data.repository.AuthRepository

class ProfileFragmentViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun logout() {
        authRepository.deleteUserData()
    }

    fun checkToken(): LiveData<String> {
        return authRepository.getToken()
    }

    fun checkId(): LiveData<String> {
        return authRepository.getId().asLiveData()
    }

    fun getUser(token: String, userId: String): LiveData<Result<resultData>> = authRepository.getUser(token, userId)

    class ProfileViewModelFactory private constructor(
        private val authRepository: AuthRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileFragmentViewModel::class.java)) {
                return ProfileFragmentViewModel(authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var instance: ProfileViewModelFactory? = null

            fun getInstance(context: Context): ProfileViewModelFactory = instance ?: synchronized(this) {
                instance ?: ProfileViewModelFactory(
                    AppModule.provideAuthRepository(context)
                )
            }.also { instance = it }
        }
    }
}