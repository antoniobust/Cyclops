package mobicontrol.mcApiService.dataModel

import com.google.gson.annotations.SerializedName

data class Token(
        @field:SerializedName("access_token")
        var token: String,
        @field:SerializedName("token_type")
        val token_type: String,
        @field:SerializedName("token_expiration")
        val tokenExpiration: Int,
        @field:SerializedName("refresh_token")
        val refreshToken: String)
