package com.mgroo.gtsimhub

import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.kotlincrypto.random.CryptoRand
import kotlin.io.encoding.Base64

val discordAuth = mutableStateOf(false)
const val discordClientId = "client_id=1426838693150326825"
const val responseType = "response_type=code"
const val redirectUri = "redirect_uri=http%3A%2F%2Flocalhost%3A8888%2Fapi%2Fauth%2Fdiscord%2Fredirect"
const val scope = "scope=identify+email"

private val dataStore: DataStore<Preferences> = createDataStore { dataStoreFileName }
private val OAUTH_STATE_KEY = stringPreferencesKey("oauth_state")

fun discordAuthUrl(state: String): String {
    val discordAuthUrl = "https://discord.com/oauth2/authorize?$discordClientId&$responseType&$redirectUri&$scope&state=$state"
    return discordAuthUrl
}

fun generateSecureOAuthState(byteLength: Int = 32): String {
    val bytes = CryptoRand.nextBytes(ByteArray(byteLength))
    return Base64.encode(bytes)
}

fun saveState(state: String) {
    runBlocking {
        dataStore.edit { preferences ->
            preferences[OAUTH_STATE_KEY] = state
        }
    }
}

fun readState(state: String): String? = runBlocking {
    dataStore.data.map { preferences ->
        preferences[OAUTH_STATE_KEY]
    }.first()
}
