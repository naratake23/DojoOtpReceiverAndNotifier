package com.liberty.dojontkotpreceiverandnotifier.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.liberty.dojontkotpreceiverandnotifier.core.PreferenceKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SmsCodeRepositoryImpl @Inject constructor(
    // @Inject constructor is required because we are using @Binds.
    private val dataStore: DataStore<Preferences>,
): SmsCodeRepository {

    // Preference key used to store the last OTP code as a String in DataStore.
    private val lastCodeKey = stringPreferencesKey(PreferenceKeys.LAST_CODE)

    // Public stream (Flow) of the last saved code.
    // DataStore exposes a Flow<Preferences>; map to read the key, default to "" if absent.
    override val lastCodeFlow: Flow<String> =
        dataStore.data.map { it[lastCodeKey].orEmpty() }

    // Persist a new code.
    // Marked suspend; does I/O work on Dispatchers.IO and writes atomically via edit { }.
    override suspend fun saveCode(code: String) {
        withContext(Dispatchers.IO) {
            dataStore.edit { prefs -> prefs[lastCodeKey] = code }
        }
    }
}