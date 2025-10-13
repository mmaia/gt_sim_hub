package com.mgroo.gtsimhub.gt_sim_hub

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
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
import gt_sim_hub.composeapp.generated.resources.gt_league_hub_logo

@Composable
@Preview
fun App() {
    MaterialTheme {
        HomeScreen()
    }
}

val discordAuth = mutableStateOf(false)
@Composable
fun HomeScreen() {
    // Replace with your actual client ID from the Discord Developer Portal
    val discordClientId = "1426838693150326825"
    val discordAuthUrl = "https://discord.com/oauth2/authorize?client_id=$discordClientId&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A8888%2Fapi%2Fauth%2Fdiscord%2Fredirect&scope=identify+email"



    if(discordAuth.value) {
        openUrl(discordAuthUrl)
    }

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
            Text("Welcome, let's go race!",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White)
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(Res.drawable.gt_league_hub_logo),
                contentDescription = "GT League Hub Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { discordAuth.value = true } ) {
                Icon(painterResource(Res.drawable.Discord_Symbol_Blurple), contentDescription = "Discord Logo", modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Login with Discord")
            }
        }
    }
}