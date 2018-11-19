package com.mdmobile.cyclops.dataModel.api.newDataClass

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mdmobile.cyclops.provider.McContract

@Entity(tableName = McContract.SCRIPT_TABLE_NAME)
data class Script(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val title: String,
        val description: String,
        val script: String)