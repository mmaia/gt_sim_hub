package com.mgroo.gtsimhub

import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.kotlincrypto.random.CryptoRand
import kotlin.io.encoding.Base64

val discordAuth = mutableStateOf(false)
const val discordClientId = "client_id=1426838693150326825"
const val responseType = "response_type=code"
const val redirectUri = "redirect_uri=http%3A%2F%2Flocalhost%3A8888%2Fapi%2Fauth%2Fdiscord%2Fredirect"
const val scope = "scope=identify+email"
const val backendBaseUrl = "http://localhost:8888"

private val dataStore: DataStore<Preferences> = createDataStore { dataStoreFileName }
private val OAUTH_STATE_KEY = stringPreferencesKey("oauth_state")
private val SESSION_TOKEN_KEY = stringPreferencesKey("session_token")
private val USERNAME_KEY = stringPreferencesKey("username")
private val AVATAR_URL_KEY = stringPreferencesKey("avatar_url")

@Serializable
data class AuthStatusResponse(
    val status: String,
    val sessionToken: String? = null,
    val username: String? = null,
    val avatarUrl: String? = null
)

@Serializable
data class UserValidationResponse(
    val username: String,
    val globalName: String? = null,
    val avatarUrl: String? = null,
    val discordId: String
)

val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        })
    }
}

fun discordAuthUrl(state: String): String {
    return "https://discord.com/oauth2/authorize?$discordClientId&$responseType&$redirectUri&$scope&state=$state"
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

suspend fun saveSession(sessionToken: String, username: String, avatarUrl: String) {
    dataStore.edit { preferences ->
        preferences[SESSION_TOKEN_KEY] = sessionToken
        preferences[USERNAME_KEY] = username
        preferences[AVATAR_URL_KEY] = avatarUrl
    }
}

suspend fun clearSession() {
    dataStore.edit { preferences ->
        preferences.remove(SESSION_TOKEN_KEY)
        preferences.remove(USERNAME_KEY)
        preferences.remove(AVATAR_URL_KEY)
    }
}

suspend fun readSessionToken(): String? {
    return dataStore.data.map { preferences ->
        preferences[SESSION_TOKEN_KEY]
    }.first()
}

suspend fun readSavedUsername(): String? {
    return dataStore.data.map { preferences ->
        preferences[USERNAME_KEY]
    }.first()
}

suspend fun pollAuthStatus(state: String): AuthStatusResponse? {
    // Poll every 2 seconds, with a timeout limit of 60 attempts (2 minutes)
    for (attempt in 1..60) {
        try {
            val response: AuthStatusResponse = httpClient.get("$backendBaseUrl/api/auth/discord/status") {
                parameter("state", state)
            }.body()

            if (response.status == "SUCCESS") {
                return response
            }
        } catch (e: Exception) {
            println("Polling status check error: ${e.message}")
        }
        delay(2000)
    }
    return null
}

suspend fun validateSession(token: String): UserValidationResponse? {
    try {
        val response: HttpResponse = httpClient.get("$backendBaseUrl/api/auth/validate") {
            header("Authorization", "Bearer $token")
        }
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        }
    } catch (e: Exception) {
        println("Session validation error: ${e.message}")
    }
    return null
}
