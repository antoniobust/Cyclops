package com.mdmobile.cyclops.dataModel.api.devices.test

import com.google.gson.annotations.SerializedName

data class DeviceUserInfo(
        @SerializedName("UserName") val userName: Any = Any(),
        @SerializedName("FirstName") val firstName: Any = Any(),
        @SerializedName("MiddleName") val middleName: Any = Any(),
        @SerializedName("LastName") val lastName: Any = Any(),
        @SerializedName("DomainName") val domainName: Any = Any(),
        @SerializedName("UPN") val uPN: Any = Any(),
        @SerializedName("PhoneNumber") val phoneNumber: Any = Any(),
        @SerializedName("Email") val email: Any = Any(),
        @SerializedName("CustomProperty1") val customProperty1: Any = Any(),
        @SerializedName("CustomProperty2") val customProperty2: Any = Any(),
        @SerializedName("CustomProperty3") val customProperty3: Any = Any(),
        @SerializedName("Identifier") val identifier: Any = Any()
)