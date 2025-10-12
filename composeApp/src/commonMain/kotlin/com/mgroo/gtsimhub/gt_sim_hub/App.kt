package com.mgroo.gtsimhub.gt_sim_hub

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import gt_sim_hub.composeapp.generated.resources.Discord_Symbol_Blurple
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import gt_sim_hub.composeapp.generated.resources.Res
import gt_sim_hub.composeapp.generated.resources.bkground_blurred

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
    val discordClientId = "YOUR_DISCORD_CLIENT_ID"
    val discordAuthUrl = "https://discord.com/api/oauth2/authorize?client_id=$discordClientId&response_type=code&redirect_uri=gtsimhub%3A%2F%2Fcallback&scope=identify"

    // A function to trigger the authentication flow
    val openUrl = @Composable { url: String -> openUrl(url) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.bkground_blurred),
            contentDescription = "Background",
            contentScale = ContentScale.Crop, // This scales the image to fill the screen, cropping excess
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("GT League Hub",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White)
            Spacer(modifier = Modifier.height(64.dp))
            Button(onClick = { openUrl(discordAuthUrl) }) {
                Icon(painterResource(Res.drawable.Discord_Symbol_Blurple), contentDescription = "Discord Logo", modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Login with Discord")
            }
        }
    }
}