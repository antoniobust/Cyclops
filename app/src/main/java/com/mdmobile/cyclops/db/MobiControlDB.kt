package com.mdmobile.cyclops.db

import android.annotation.SuppressLint
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mdmobile.cyclops.ApplicationLoader.applicationContext
import com.mdmobile.cyclops.dataModel.api.newDataClass.*

@Database(version = 1, exportSchema = false,
        entities = [Device::class, DeploymentServer::class, InstalledApps::class, InstanceInfo::class, ManagmentServer::class,
            Script::class, User::class])
abstract class MobiControlDB : RoomDatabase() {

    private val dbName = "MobiControlDB"

    @SuppressWarnings("LinkageError")

    private object HOLDER {
        @SuppressLint("StaticFieldLeak")
        val instance = Room.databaseBuilder(applicationContext, MobiControlDB::class.java, "MobiControlDB")
                .fallbackToDestructiveMigration()
                .build()
    }

    companion object {
        val database by lazy { HOLDER.instance }
    }

    abstract fun deviceDao(): DeviceDao
    abstract fun deploymentServerDao(): DeploymentServerDao
    abstract fun installedAppsDao(): InstalledAppsDao
    abstract fun instanceDao(): InstanceDao
    abstract fun managementServerDao(): ManagementServerDao
    abstract fun scriptDao(): ScriptDao
    abstract fun userDao(): UserDao
}