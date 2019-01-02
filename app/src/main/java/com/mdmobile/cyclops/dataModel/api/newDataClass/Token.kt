package com.mdmobile.cyclops.dataModel.api.newDataClass


data class Token(val access_token: String, val token_type: String,
                 val tokenExpiration: Int, val refreshToken: String)
