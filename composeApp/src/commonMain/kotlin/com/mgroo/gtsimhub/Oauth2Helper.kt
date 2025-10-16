package com.mgroo.gtsimhub

import androidx.compose.runtime.mutableStateOf

val discordAuth = mutableStateOf(false)
const val clientId = "client_id=1426838693150326825"
const val responseType = "response_type=code"
const val redirectUri = "redirect_uri=http%3A%2F%2Flocalhost%3A8888%2Fapi%2Fauth%2Fdiscord%2Fredirect"
const val scope = "scope=identify+email"
fun discordAuthUrl(): String {
    val discordAuthUrl = "https://discord.com/oauth2/authorize?$clientId&$responseType&$redirectUri&$scope"
    return discordAuthUrl
}