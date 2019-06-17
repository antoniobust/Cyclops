package com.mdmobile.cyclops.repository

import android.accounts.AccountManager
import android.util.Log
import androidx.lifecycle.LiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.CyclopsApplication
import com.mdmobile.cyclops.api.*
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataModel.api.newDataClass.Token
import com.mdmobile.cyclops.db.MobiControlDB
import com.mdmobile.cyclops.testing.OpenForTesting
import com.mdmobile.cyclops.util.Logger
import javax.inject.Inject

@OpenForTesting
class InstanceRepository @Inject constructor(
        private val apiService: McApiService,
        private val db: MobiControlDB,
        private val appExecutors: ApplicationExecutors,
        private val instanceInfo: InstanceInfo) {


    fun loadInstance(instanceName: String): LiveData<InstanceInfo> =
            db.instanceDao().getInstanceByName(instanceName)

    fun loadInstance(instanceInfo: InstanceInfo): LiveData<InstanceInfo> =
            db.instanceDao().getInstanceById(instanceInfo.id)

    fun loadAllInstances(): LiveData<List<InstanceInfo>> =
            db.instanceDao().getAllInstances()


    fun getToken(): LiveData<Resource<Token>> {

        return object : NetworkBoundResource<Token,Token>(appExecutors){
            override fun saveApiResult(item: Token) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun shouldFetch(data: Token?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun loadFromDb(): LiveData<Token> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createCall(): LiveData<ApiResponse<Token>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }.asLiveData()
    }
}
