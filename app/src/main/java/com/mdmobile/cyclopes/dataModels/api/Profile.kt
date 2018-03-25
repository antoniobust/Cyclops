package com.mdmobile.cyclopes.dataModels.api

import android.content.ContentValues
import android.os.Parcelable
import com.mdmobile.cyclopes.provider.McContract
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Profile(val ReferenceId: String = "N/A", val Name: String = "N/A", val Status: String = "N/A",
                   val AssignmentDate: String = "N/A", val VersionNumber: Int = -1, val Mandatory: Boolean = false) : Parcelable {

    private val mandatory
        get() : Int {
            return if (Mandatory) 0 else 1
        }

    fun toContentValues(): ContentValues {
        val values = ContentValues(6)
        values.put(McContract.Profile.NAME, Name)
        values.put(McContract.Profile.STATUS, Status)
        values.put(McContract.Profile.REFERENCE_ID, ReferenceId)
        values.put(McContract.Profile.ASSIGNMENT_DATE, AssignmentDate)
        values.put(McContract.Profile.VERSION_NUMBER, VersionNumber)
        values.put(McContract.Profile.IS_MANDATORY, mandatory)
        return values
    }
}
