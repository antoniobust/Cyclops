package com.mdmobile.cyclops.db

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mdmobile.cyclops.ApplicationLoader.applicationContext
import com.mdmobile.cyclops.dataModel.api.newDataClass.*
import com.mdmobile.cyclops.provider.McContract

@Database(version = 1, exportSchema = false,
        entities = [Device::class, DeploymentServer::class, InstalledApps::class, InstanceInfo::class, ManagementServer::class,
            Script::class, User::class])
abstract class MobiControlDB : RoomDatabase() {


    @SuppressWarnings("LinkageError")

    private object HOLDER {
        @SuppressLint("StaticFieldLeak")
        val instance = Room.databaseBuilder(applicationContext, MobiControlDB::class.java, dbName)
                .fallbackToDestructiveMigration()
                .addCallback(DB_CALLBACK)
                .build()
    }

    companion object {
        val database by lazy { HOLDER.instance }
        private const val dbName = "MobiControlDB"

        fun getInMemoryDB(context: Context): MobiControlDB {
            return Room.inMemoryDatabaseBuilder(context, MobiControlDB::class.java)
                    .addCallback(DB_CALLBACK)
                    .build()
        }

        private val DB_CALLBACK: RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

//              //Whenever we delete an instance  we delete all info (Devices, MS&DS, Users) related to that instance
                db.execSQL("CREATE TRIGGER DeleteInstance BEFORE DELETE ON " + McContract.INSTANCE_INFO_TABLE_NAME
                        + " BEGIN "
                        + " DELETE FROM " + McContract.DEVICE_TABLE_NAME
                        + " WHERE " + McContract.DEVICE_TABLE_NAME + ".instanceId"
                        + "=OLD.id;"
                        + " DELETE FROM " + McContract.MANAGEMENT_SERVER_TABLE_NAME
                        + " WHERE " + McContract.MANAGEMENT_SERVER_TABLE_NAME + ".instanceId"
                        + "=OLD.id;"
                        + " DELETE FROM " + McContract.DEPLOYMENT_SERVER_TABLE_NAME
                        + " WHERE " + McContract.DEPLOYMENT_SERVER_TABLE_NAME + ".instanceId"
                        + "=OLD.id;"
                        + " DELETE FROM " + McContract.USER_TABLE_NAME
                        + " WHERE " + McContract.USER_TABLE_NAME + ".instanceId"
                        + "=OLD.id;"
                        + "END;")

                //Whenever we delete a device we delete related installed apps
                db.execSQL("CREATE TRIGGER RemoveDeviceApps BEFORE DELETE ON " + McContract.DEVICE_TABLE_NAME
                        + " BEGIN "
                        + "DELETE FROM " + McContract.INSTALLED_APPLICATION_TABLE_NAME
                        + " WHERE " + McContract.INSTALLED_APPLICATION_TABLE_NAME + "." + McContract.InstalledApplications.DEVICE_ID
                        + "= OLD." + McContract.Device.COLUMN_DEVICE_ID + ";"
                        + "END;")

                //Whenever we delete a device we delete references in PROFILE-DEVICE lookup table
                db.execSQL("CREATE TRIGGER RemoveDeviceProfiles BEFORE DELETE ON " + McContract.DEVICE_TABLE_NAME
                        + " BEGIN "
                        + "DELETE FROM " + McContract.PROFILE_DEVICE_TABLE_NAME
                        + " WHERE " + McContract.PROFILE_DEVICE_TABLE_NAME + "." + McContract.ProfileDevice.DEVICE_ID
                        + "= OLD." + McContract.Device.COLUMN_DEVICE_ID + ";"
                        + "END;")
            }
        }
    }


    abstract fun deviceDao(): DeviceDao
    abstract fun deploymentServerDao(): DeploymentServerDao
    abstract fun installedAppsDao(): InstalledAppsDao
    abstract fun instanceDao(): InstanceDao
    abstract fun managementServerDao(): ManagementServerDao
    abstract fun scriptDao(): ScriptDao
    abstract fun userDao(): UserDao
}