package com.team.temara.ui.main.fragment.forum

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.team.temara.data.remote.AppModule
import com.team.temara.data.remote.response.Result
import com.team.temara.data.remote.response.resultData
import com.team.temara.data.repository.AuthRepository
import com.team.temara.data.repository.ForumRepository

class ForumViewModel(
    private val authRepository: AuthRepository,
    private val forumRepository: ForumRepository
) : ViewModel() {

    fun checkToken(): LiveData<String> {
        return authRepository.getToken()
    }

    fun checkId(): LiveData<String> {
        return authRepository.getId().asLiveData()
    }

    fun getUser(token: String, userId: String): LiveData<Result<resultData>> = authRepository.getUser(token, userId)

    fun getForum(token: String) = forumRepository.getForum(token)



    class ForumViewModelFactory private constructor(
        private val authRepository: AuthRepository,
        private val forumRepository: ForumRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ForumViewModel::class.java)) {
                return ForumViewModel(authRepository, forumRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var instance: ForumViewModelFactory? = null

            fun getInstance(context: Context): ForumViewModelFactory = instance ?: synchronized(this) {
                instance ?: ForumViewModelFactory(
                    AppModule.provideAuthRepository(context),
                    AppModule.provideForumRepository(context)
                )
            }.also { instance = it }
        }
    }

}
