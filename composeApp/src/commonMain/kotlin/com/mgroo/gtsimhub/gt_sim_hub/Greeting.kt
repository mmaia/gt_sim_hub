package com.mgroo.gtsimhub.gt_sim_hub

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}