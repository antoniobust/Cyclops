package mobicontrol.mcApiService.dataModel

import com.google.gson.annotations.SerializedName

data class User(
        @field:SerializedName("Name")
        val name: String,
        @field:SerializedName("DisplayName")
        val displayName: String,
        @field:SerializedName("Kind")
        val kind: String,
        @field:SerializedName("EulaAcceptanceDate")
        val eulaAcceptanceDate: String,
        @field:SerializedName("IsEulaAccepted")
        val isEulaAccepted: Boolean,
        @field:SerializedName("IsAccountLocked")
        val isAccountLocked: Boolean,
        @field:SerializedName("NumberOfFailedLogins")
        val numberOfFailedLogins: Int,
        val instanceId: Int
)
