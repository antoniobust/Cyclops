package mobicontrol.mcApiService.dataModel

import java.net.URL

data class InstanceInfo(
        val managementServerAddress: URL,
        val clientID: String,
        val secret: String)

