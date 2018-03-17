package com.mdmobile.pocketconsole.dataModels.api

import android.content.ContentValues
import android.os.Parcelable
import com.mdmobile.pocketconsole.provider.McContract
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InstalledApp(val DeviceId: String = "N/A", val ApplicationId: String = "N/A", val Name: String = "N/A",
                        val Version: String = "N/A", val ShortVersion: String? = "N/A", val SizeInBytes: String = "N/A",
                        val DataSizeInBytes: String? = "N/A", val Status: String = "N/A")
    : Parcelable {
    fun toContentValues(): ContentValues {
        //TODO:excluding some data as MC return null which is parsed and saved in DB -> when read from kotlin null causes issues
        val values = ContentValues(6)
        values.put(McContract.InstalledApplications.DEVICE_ID, DeviceId)
        values.put(McContract.InstalledApplications.APPLICATION_ID, ApplicationId)
        values.put(McContract.InstalledApplications.APPLICATION_NAME, Name)
        values.put(McContract.InstalledApplications.APPLICATION_VERSION, Version)
//        values.put(McContract.InstalledApplications.APPLICATION_BUILD_NUMBER, ShortVersion)
        values.put(McContract.InstalledApplications.APPLICATION_SIZE, SizeInBytes)
//        values.put(McContract.InstalledApplications.APPLICATION_DATA_USED, DataSizeInBytes)
        values.put(McContract.InstalledApplications.APPLICATION_STATUS, Status)

        return values
    }
}
