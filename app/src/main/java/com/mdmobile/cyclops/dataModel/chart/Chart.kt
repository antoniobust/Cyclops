package com.mdmobile.cyclops.dataModel.chart

import com.google.gson.annotations.SerializedName

/**
 * POJO class for GSON in order to save the list of charts in SharedPref
 */

class Chart(chartType: Int, property1: String, property2: String = "N/A") {
    @JvmField  val property1: String
    @JvmField val property2: String
    @JvmField val type: Int

    init {
        @SerializedName("Type")
        this.type = chartType
        @SerializedName("FirstProperty")
        this.property1 = property1
        @SerializedName("SecondProperty")
        this.property2 = property2
    }
}