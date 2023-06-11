package com.team.temara.ui.splash

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.team.temara.data.remote.AppModule
import com.team.temara.data.repository.AuthRepository

class SplashViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun checkToken(): LiveData<String> {
        return authRepository.getToken()
    }

    fun checkId(): LiveData<String> {
        return authRepository.getId().asLiveData()
    }

    class SplashViewModelFactory private constructor(
        private val authRepository: AuthRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SplashViewModel::class.java)) {
                return SplashViewModel(authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }


        companion object {
            @Volatile
            private var instance: SplashViewModelFactory? = null

            fun getInstance(
                context: Context,
            ): SplashViewModelFactory = instance ?: synchronized(this) {
                instance ?: SplashViewModelFactory(
                    AppModule.provideAuthRepository(context)
                )
            }.also { instance = it }
        }

    }

}