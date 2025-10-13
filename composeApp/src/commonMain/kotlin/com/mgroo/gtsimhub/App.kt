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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mgroo.gtsimhub.gt_sim_hub.openUrl
import gt_sim_hub.composeapp.generated.resources.Discord_Symbol_Blurple
import gt_sim_hub.composeapp.generated.resources.Res
import gt_sim_hub.composeapp.generated.resources.bkground_blurred
import gt_sim_hub.composeapp.generated.resources.gt_league_hub_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    // Replace with your actual client ID from the Discord Developer Portal
    val discordClientId = "1426838693150326825"
    val discordAuthUrl = "https://discord.com/oauth2/authorize?client_id=$discordClientId&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A8888%2Fapi%2Fauth%2Fdiscord%2Fredirect&scope=identify+email"
    // A function to trigger the authentication flow
    val openUrl = { url: String -> openUrl(url) }

    Box(modifier = Modifier.Companion.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.bkground_blurred),
            contentDescription = "Background",
            contentScale = ContentScale.Companion.Crop, // This scales the image to fill the screen, cropping excess
            modifier = Modifier.Companion.fillMaxSize()
        )
        Column(
            modifier = Modifier.Companion
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Welcome, let's go race!",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Companion.White
            )
            Spacer(modifier = Modifier.Companion.height(32.dp))
            Image(
                painter = painterResource(Res.drawable.gt_league_hub_logo),
                contentDescription = "GT League Hub Logo",
                modifier = Modifier.Companion.size(150.dp)
            )
            Spacer(modifier = Modifier.Companion.height(32.dp))
            Button(onClick = { openUrl(discordAuthUrl) }) {
                Icon(
                    painterResource(Res.drawable.Discord_Symbol_Blurple),
                    contentDescription = "Discord Logo",
                    modifier = Modifier.Companion.size(24.dp)
                )
                Spacer(modifier = Modifier.Companion.width(8.dp))
                Text("Login with Discord")
            }
        }
    }
}