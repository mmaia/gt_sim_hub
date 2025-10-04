package com.mgroo.gtsimhub.gt_sim_hub

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform