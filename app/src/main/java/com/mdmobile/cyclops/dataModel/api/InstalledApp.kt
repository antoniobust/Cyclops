package com.mdmobile.cyclops.dataModel.api

import android.content.ContentValues
import android.os.Parcelable
import com.mdmobile.cyclops.provider.McContract
import kotlinx.android.parcel.Parcelize

@Parcelize
class InstalledApp(val DeviceId: String = "N/A", val ApplicationId: String = "N/A", val Name: String = "N/A", val Status: String = "N/A")
    : Parcelable {

    //    val Version: String = "N/A", /**val ShortVersion: String = "N/A"*,*/ val SizeInBytes: String = "N/A",
//    val DataSizeInBytes: String = "N/A",
    fun toContentValues(): ContentValues {
        val values = ContentValues(6)
        values.put(McContract.InstalledApplications.DEVICE_ID, DeviceId)
        values.put(McContract.InstalledApplications.APPLICATION_ID, ApplicationId)
        values.put(McContract.InstalledApplications.APPLICATION_NAME, Name)
//        values.put(McContract.InstalledApplications.APPLICATION_VERSION, Version)
//        values.put(McContract.InstalledApplications.APPLICATION_BUILD_NUMBER, ShortVersion)
//        values.put(McContract.InstalledApplications.APPLICATION_SIZE, SizeInBytes)
//        values.put(McContract.InstalledApplications.APPLICATION_DATA_USED, DataSizeInBytes)
        values.put(McContract.InstalledApplications.APPLICATION_STATUS, Status)
        return values
    }
}
