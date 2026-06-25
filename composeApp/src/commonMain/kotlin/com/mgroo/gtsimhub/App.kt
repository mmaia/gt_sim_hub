package com.mgroo.gtsimhub

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var checkingSession by remember { mutableStateOf(true) }
        var signedIn by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            val token = readSessionToken()
            if (token != null) {
                val validation = validateSession(token)
                if (validation != null) {
                    signedIn = true
                } else {
                    clearSession()
                }
            }
            checkingSession = false
        }

        if (checkingSession) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (signedIn) {
                HomeScreen()
            } else {
                AuthScreen(onSignInSuccessful = { signedIn = true })
            }
        }
    }
}