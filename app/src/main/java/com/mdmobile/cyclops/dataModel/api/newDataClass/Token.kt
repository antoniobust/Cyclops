package com.mdmobile.cyclops.dataModel.api.newDataClass

import com.google.gson.annotations.SerializedName


data class Token(
        @field:SerializedName("access_token")
        val token: String,
        val token_type: String?,
        val tokenExpiration: Int?, val refreshToken: String?)
