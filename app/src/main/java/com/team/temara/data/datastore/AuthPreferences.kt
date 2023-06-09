package com.team.temara.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreferences (private val dataStore: DataStore<Preferences>) {

    private val tokenKey = stringPreferencesKey("token")

    fun getToken(): Flow<String> { return dataStore.data.map {
        it[tokenKey] ?: "null"
    } }

    suspend fun setUserToken(token: String) { dataStore.edit {
        it [this.tokenKey] = token
    }}

    suspend fun deleteUserToken() { dataStore.edit {
        it.clear()
    }}


    companion object {
        @Volatile
        private var instance: AuthPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences =
            instance ?: synchronized(this) {
                instance ?: AuthPreferences(dataStore)
            }.also { instance = it }
    }

}

