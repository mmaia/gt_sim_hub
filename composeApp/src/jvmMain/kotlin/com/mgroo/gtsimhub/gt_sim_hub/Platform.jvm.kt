package com.mgroo.gtsimhub.gt_sim_hub

import androidx.compose.runtime.Composable
import java.awt.Desktop
import java.net.URI

@Composable
internal actual fun openUrl(url: String) {
    val desktop = Desktop.getDesktop()
    desktop.browse(URI.create(url))
}