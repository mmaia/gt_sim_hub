package com.mgroo.gtsimhub

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import gt_sim_hub.composeapp.generated.resources.Discord_Symbol_Blurple
import gt_sim_hub.composeapp.generated.resources.Res
import gt_sim_hub.composeapp.generated.resources.bkground_blurred
import gt_sim_hub.composeapp.generated.resources.gt_league_hub_logo
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun AuthScreen(onSignInSuccessful: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    var isAuthenticating by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.bkground_blurred),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Welcome, let's go race!",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(Res.drawable.gt_league_hub_logo),
                contentDescription = "GT League Hub Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            if (isAuthenticating) {
                CircularProgressIndicator(color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Waiting for Discord authentication...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            } else {
                val uriHandler = LocalUriHandler.current
                Button(
                    onClick = {
                        coroutineScope.launch {
                            isAuthenticating = true
                            errorMessage = null
                            val state = generateSecureOAuthState()
                            saveState(state)
                            uriHandler.openUri(discordAuthUrl(state))
                            
                            val result = pollAuthStatus(state)
                            if (result != null && result.sessionToken != null) {
                                saveSession(result.sessionToken, result.username ?: "User", result.avatarUrl ?: "")
                                onSignInSuccessful()
                            } else {
                                errorMessage = "Authentication failed or timed out. Please try again."
                            }
                            isAuthenticating = false
                        }
                    }
                ) {
                    Icon(
                        painterResource(Res.drawable.Discord_Symbol_Blurple),
                        contentDescription = "Discord Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Login with Discord")
                }
            }

            errorMessage?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    error,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}