package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.SCRIPT_TABLE_NAME)
data class Script(
        val id: Int,
        val title: String,
        val description: String,
        val script: String)