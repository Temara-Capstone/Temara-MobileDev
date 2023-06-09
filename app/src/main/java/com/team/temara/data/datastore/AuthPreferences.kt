package com.team.temara.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreferences (private val dataStore: DataStore<Preferences>) {

    private val tokenKey = stringPreferencesKey("token")
    private val userIdKey = stringPreferencesKey("userId")

    fun getToken(): Flow<String> { return dataStore.data.map {
        it[tokenKey] ?: "null"
    } }

    fun getId(): Flow<String> { return dataStore.data.map {
        it[userIdKey] ?: "null"
    }}

    suspend fun setUserToken(token: String) { dataStore.edit {
        it [this.tokenKey] = token
    }}

    suspend fun setUserId(userId: String) { dataStore.edit {
        it [this.userIdKey] = userId
    }}

    suspend fun deleteUserData() { dataStore.edit {
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

