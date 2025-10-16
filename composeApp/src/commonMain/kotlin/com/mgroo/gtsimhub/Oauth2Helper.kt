package com.mgroo.gtsimhub

import androidx.compose.runtime.mutableStateOf
import org.kotlincrypto.random.CryptoRand
import kotlin.io.encoding.Base64

val discordAuth = mutableStateOf(false)
const val clientId = "client_id=1426838693150326825"
const val responseType = "response_type=code"
const val redirectUri = "redirect_uri=http%3A%2F%2Flocalhost%3A8888%2Fapi%2Fauth%2Fdiscord%2Fredirect"
const val scope = "scope=identify+email"
fun discordAuthUrl(): String {
    val state = generateSecureOAuthState()
    val discordAuthUrl = "https://discord.com/oauth2/authorize?$clientId&$responseType&$redirectUri&$scope&state=$state"
    return discordAuthUrl
}

fun generateSecureOAuthState(byteLength: Int = 32): String {
    val bytes = CryptoRand.Default.nextBytes(ByteArray(byteLength))
    return Base64.encode(bytes)
}