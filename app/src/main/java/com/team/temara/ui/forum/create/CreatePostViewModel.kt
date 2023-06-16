package com.team.temara.ui.forum.create

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.team.temara.data.remote.AppModule
import com.team.temara.data.repository.AuthRepository
import com.team.temara.data.repository.ForumRepository
import java.io.File

class CreatePostViewModel(
    private val forumRepository: ForumRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    fun checkToken(): LiveData<String> {
        return authRepository.getToken()
    }

    fun checkId(): LiveData<String> {
        return authRepository.getId().asLiveData()
    }

    fun postForum(token: String, userId: String, image: File? = null, text: String) = forumRepository.postForum(token, userId, image, text)

    class CreatePostViewModelFactory private constructor(
        private val forumRepository: ForumRepository,
        private val authRepository: AuthRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CreatePostViewModel::class.java)) {
                return CreatePostViewModel(forumRepository, authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var instance: CreatePostViewModelFactory? = null

            fun getInstance(
                context: Context,
            ): CreatePostViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: CreatePostViewModelFactory(
                        AppModule.provideForumRepository(context),
                        AppModule.provideAuthRepository(context)
                    )
                }.also { instance = it }
        }
    }

}