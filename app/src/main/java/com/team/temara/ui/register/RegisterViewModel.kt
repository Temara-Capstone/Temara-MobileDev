package com.team.temara.ui.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.temara.data.remote.AppModule
import com.team.temara.data.repository.AuthRepository

class RegisterViewModel (private val usersRepository: AuthRepository) : ViewModel() {
    fun registerUsers(name: String, email: String, password: String) = usersRepository.register(name, email, password)

    class RegisterViewModelFactory private constructor(private val usersRepository: AuthRepository) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                return RegisterViewModel(usersRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var instance: RegisterViewModelFactory? = null

            fun getInstance(context: Context): RegisterViewModelFactory = instance ?: synchronized(this) {
                instance ?: RegisterViewModelFactory(AppModule.provideAuthRepository(context))
            }
        }
    }
}