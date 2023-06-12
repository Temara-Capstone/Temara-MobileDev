package com.team.temara.ui.main.fragment.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.team.temara.data.remote.AppModule
import com.team.temara.data.remote.response.Result
import com.team.temara.data.remote.response.resultData
import com.team.temara.data.repository.AuthRepository

class HomeViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun checkToken(): LiveData<String> {
        return authRepository.getToken()
    }

    fun checkId(): LiveData<String> {
        return authRepository.getId().asLiveData()
    }

    fun getUser(token: String, userId: String): LiveData<Result<resultData>> = authRepository.getUser(token, userId)

    class HomeViewModelFactory private constructor(
        private val authRepository: AuthRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var instance: HomeViewModelFactory? = null

            fun getInstance(context: Context): HomeViewModelFactory = instance ?: synchronized(this) {
                instance ?: HomeViewModelFactory(
                    AppModule.provideAuthRepository(context)
                )
            }.also { instance = it }
        }
    }

}