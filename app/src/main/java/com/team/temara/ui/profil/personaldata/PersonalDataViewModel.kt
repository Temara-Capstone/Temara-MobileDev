package com.team.temara.ui.profil.personaldata

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
import java.io.File

class PersonalDataViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun updateUser(token: String, userId: String, name: String, email: String, dateOfBirth: String, gender: String, no_hp: String) =
        authRepository.updateUser(token, userId, name, email, dateOfBirth, gender, no_hp).also {
            Log.d("UpdateUser", " Token: $token, UserID: $userId, Name: $name, Email: $email, Date of Birth: $dateOfBirth, Gender: $gender, Phone Number: $no_hp")
        }

    fun checkToken(): LiveData<String> {
        return authRepository.getToken()
    }

    fun checkId(): LiveData<String> {
        return authRepository.getId().asLiveData()
    }

    fun getUser(token: String, userId: String): LiveData<Result<resultData>> = authRepository.getUser(token, userId)

    class PersonalDataViewModelFactory private constructor(
        private val authRepository: AuthRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(PersonalDataViewModel::class.java)) {
                return PersonalDataViewModel(authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var instance: PersonalDataViewModelFactory? = null

            fun getInstance(context: Context): PersonalDataViewModelFactory = instance ?: synchronized(this) {
                instance ?: PersonalDataViewModelFactory(
                    AppModule.provideAuthRepository(context)
                )
            }.also { instance = it }
        }

    }
}