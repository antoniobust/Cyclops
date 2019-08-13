package com.mdmobile.cyclops.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.CyclopsApplication.Companion.applicationContext
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.db.DeviceDao
import com.mdmobile.cyclops.db.InstanceDao
import com.mdmobile.cyclops.db.MobiControlDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetModules::class, ViewModelModules::class])
class ApplicationModules {

    @Singleton
    @Provides
    fun providesApplicationContext(app:Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideMcDb(applicationContext: Context): MobiControlDB {
        return Room.databaseBuilder(applicationContext, MobiControlDB::class.java, "MobiControl.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideDeviceDao(db: MobiControlDB): DeviceDao {
        return db.deviceDao()
    }

    @Singleton
    @Provides
    fun provideInstanceDao(db: MobiControlDB): InstanceDao {
        return db.instanceDao()
    }

    @Provides
    fun provideInstanceInfo(): InstanceInfo {
        return InstanceInfo()
    }
}