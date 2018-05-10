package com.mdmobile.cyclops.ui.main.dashboard.statistics

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Java class that represent a single data entry for a statistic
 */
@Parcelize
data class StatDataEntry(@JvmField val label: String, @JvmField var value: Int) : Parcelable
