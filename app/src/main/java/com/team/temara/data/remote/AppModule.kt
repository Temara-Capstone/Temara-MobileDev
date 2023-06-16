package com.team.temara.data.remote

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.team.temara.data.datastore.AuthPreferences
import com.team.temara.data.remote.retrofit.ApiConfig
import com.team.temara.data.remote.retrofit.ApiService
import com.team.temara.data.repository.ArticleRepository
import com.team.temara.data.repository.AuthRepository
import com.team.temara.data.repository.ForumRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

private const val LOGIN_PREFERENCES = "login"

object AppModule {
    private fun provideApiService(): ApiService {
        return ApiConfig.getApiService()
    }

    private fun provideAuthPreferences(context: Context): AuthPreferences = AuthPreferences.getInstance(
        providePreferencesDataStore(context)
    )

    fun provideAuthRepository(context: Context): AuthRepository {
        return AuthRepository.getInstance(
            provideApiService(),
            provideAuthPreferences(context)
        )
    }

    fun provideArticleRepository(): ArticleRepository {
        return ArticleRepository.getInstance(provideApiService())
    }

    fun provideForumRepository(context: Context): ForumRepository {
        return ForumRepository.getInstance(provideApiService())
    }

    private fun providePreferencesDataStore(context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            migrations = listOf(SharedPreferencesMigration(context, LOGIN_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(LOGIN_PREFERENCES) })
    }
}