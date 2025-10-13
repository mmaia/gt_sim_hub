package com.mgroo.gtsimhub.gt_sim_hub

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mgroo.gtsimhub.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "GT League Hub",
    ) {
        App()
    }
}