package com.todayquest.data.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.todayquest.data.UserPrincipal
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(
    private var dataStore: DataStore<Preferences>,
){
    private val principalKey = stringPreferencesKey("user_principal")

    suspend fun savePrincipal(userPrincipal: UserPrincipal){
        dataStore.edit { preferences ->
            preferences[principalKey] = Gson().toJson(userPrincipal)
        }
    }

    suspend fun deletePrincipal() {
        dataStore.edit { preferences ->
            preferences[principalKey] = ""
        }
    }

    suspend fun existPrincipal(): Boolean {
        return dataStore.data.map {
            val json = it[principalKey] ?: ""
            val principal = Gson().fromJson(json, UserPrincipal::class.java)
            principal?.hasToken() ?: false
        }.first()
    }
}