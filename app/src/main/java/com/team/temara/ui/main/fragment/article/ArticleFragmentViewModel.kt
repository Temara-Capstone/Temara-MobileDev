package com.team.temara.ui.main.fragment.article

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.temara.data.remote.AppModule
import com.team.temara.data.repository.ArticleRepository
import com.team.temara.data.repository.AuthRepository

class ArticleFragmentViewModel(private val articleRepository: ArticleRepository, private val authRepository: AuthRepository) : ViewModel() {

    fun getStory(token: String) = articleRepository.getArticle(token)

    fun checkToken(): LiveData<String> {
        return authRepository.getToken()
    }

    class ArticleFragmentViewModelFactory private constructor(
        private val articleRepository: ArticleRepository,
        private val authRepository: AuthRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ArticleFragmentViewModel::class.java)) {
                return ArticleFragmentViewModel(articleRepository, authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var instance: ArticleFragmentViewModelFactory? = null
            fun getInstance(context: Context): ArticleFragmentViewModelFactory = instance ?: synchronized(this) {
                instance ?: ArticleFragmentViewModelFactory(
                    AppModule.provideArticleRepository(),
                    AppModule.provideAuthRepository(context)
                )
            }.also { instance = it }
        }
    }
}