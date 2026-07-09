package com.example.lifemap.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.lifemap.domain.entity.User
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferencesImpl @Inject constructor(
    private val context: Context
) : UserPreferences {

    companion object {
        private val UID = stringPreferencesKey("uid")
        private val NAME = stringPreferencesKey("name")
        private val EMAIL = stringPreferencesKey("email")
    }

    override suspend fun saveUser(user: User) {

        context.dataStore.edit { preferences ->

            preferences[UID] = user.uid
            preferences[NAME] = user.fullName
            preferences[EMAIL] = user.email

        }
    }

    override suspend fun clearUser() {

        context.dataStore.edit {

            it.clear()

        }

    }

    override suspend fun getUser(): User? {

        val preferences = context.dataStore.data.first()

        val uid = preferences[UID] ?: return null

        return User(
            uid = uid,
            fullName = preferences[NAME] ?: "",
            email = preferences[EMAIL] ?: ""
        )

    }

    override suspend fun isLoggedIn(): Boolean {

        val preferences = context.dataStore.data.first()

        return preferences[UID] != null

    }
}