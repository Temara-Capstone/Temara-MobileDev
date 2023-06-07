package com.team.temara.data.remote

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.team.temara.data.remote.AuthPreferences.Companion.LOGIN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.authentication: DataStore<Preferences> by preferencesDataStore(name = LOGIN)

class AuthPreferences (private val context: Context) {

    private val key = stringPreferencesKey(TOKEN)

    suspend fun setToken(token: String) {
        context.authentication.edit {
            it[key] = token
        }
    }

    suspend fun deleteToken() {
        context.authentication.edit {
            it.clear()
        }
    }

    fun getToken(): Flow<String> {
        return context.authentication.data.map {
            it[key] ?: "Not Found"
        }
    }


    companion object {
        const val LOGIN = "login"
        const val TOKEN = "token"
    }
}

