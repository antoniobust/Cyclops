package com.mdmobile.cyclops.db

import androidx.room.RoomDatabase

abstract class MobiControlDB : RoomDatabase() {

    abstract fun deviceDao() :DeviceDao
}