package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.TOKEN_TABLE_NAME)
data class Token(
        @PrimaryKey
        @field:SerializedName("access_token")
        var token: String = "N/A",
        @Ignore
        val token_type: String = "N/A",
        @Ignore
        val tokenExpiration: Int= -1,
        @Ignore
        val refreshToken: String= "N/A")
