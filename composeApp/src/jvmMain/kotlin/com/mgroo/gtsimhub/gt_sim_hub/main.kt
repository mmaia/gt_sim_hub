package com.mgroo.gtsimhub.gt_sim_hub

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "gt_sim_hub",
    ) {
        App()
    }
}