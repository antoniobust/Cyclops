package com.mdmobile.cyclops.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mdmobile.cyclops.dataModel.Resource

abstract class OfflineResource<T> {

    private val liveData: MutableLiveData<Resource<T>> = MutableLiveData()
    private val dbData: LiveData<T>

    init {
        liveData.postValue(Resource.loading(null))
        dbData = loadFromDB()
        Transformations.switchMap(dbData){ newData ->
            val dt = MutableLiveData<Resource<T>>()
            dt.value = Resource.success(newData)
            dt
        }
    }

    abstract fun loadFromDB(): LiveData<T>

    fun asLiveData() = liveData
}