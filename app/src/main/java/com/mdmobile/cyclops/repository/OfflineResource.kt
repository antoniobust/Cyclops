package com.mdmobile.cyclops.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.dataModel.Resource

abstract class OfflineResource<T>(private val applicationExecutors: ApplicationExecutors) {

    private val liveData: MutableLiveData<Resource<T>> = MutableLiveData()
    private val liveDataManager = MediatorLiveData<Resource<T>>()
    private val dbData: LiveData<T>

    init {
        liveDataManager.value = Resource.loading(null)
        dbData = loadFromDB()
        liveDataManager.addSource(dbData) {
            liveDataManager.addSource(dbData) { newData ->
                setValue(Resource.success(newData))
            }

        }
        liveData.value = Resource.success(dbData.value)
    }

    abstract fun loadFromDB(): LiveData<T>

    @MainThread
    private fun setValue(newData: Resource<T>) {
        if (newData != liveDataManager.value) {
            liveDataManager.value = newData
        }
    }

    fun asLiveData() = liveDataManager as LiveData<Resource<T>>
}