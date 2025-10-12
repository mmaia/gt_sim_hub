package com.mgroo.gtsimhub.gt_sim_hub

import java.awt.Desktop
import java.net.URI

internal actual fun openUrl(url: String) {
    val desktop = Desktop.getDesktop()
    desktop.browse(URI.create(url))
}