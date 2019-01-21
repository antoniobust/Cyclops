package com.mdmobile.cyclops.ui.logIn

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.repository.InstanceRepository
import com.mdmobile.cyclops.repository.OfflineResource
import javax.inject.Inject


fun EditText.validate(errorMessage: String, validator: (String) -> Boolean) {
    if (validator(this.text.toString())) {
        this.error = errorMessage
    }
}

class LoginViewModel @Inject constructor(val repository: InstanceRepository,
                                         val applicationExecutors: ApplicationExecutors) : ViewModel() {

    private var _instance: MutableLiveData<InstanceInfo> = MutableLiveData()
    private val instanceList: LiveData<Resource<List<InstanceInfo>>>
    val isDuplicate: LiveData<Boolean>


    val instance: LiveData<InstanceInfo>
        get() = _instance
    private var user: MutableLiveData<User> = MutableLiveData()

    init {
        instanceList = loadInstances()
        isDuplicate = Transformations.switchMap(_instance) { instanceInfo ->
            if (instanceList.value?.data?.find {
                        it.serverName == instanceInfo.serverName
                    } != null) {
                val check = MutableLiveData<Boolean>()
                check.value = true
                check

            } else {
                val check = MutableLiveData<Boolean>()
                check.value = true
                check
            }
        }
        _instance.value = InstanceInfo()
    }


    fun updateClientId(clientId: String) {
        _instance.value = _instance.value?.copy(clientId = clientId)
    }

    fun updateApiSecret(apiSecret: String) {
        _instance.value = _instance.value?.copy(apiSecret = apiSecret)
    }

    fun updateInstanceName(instanceName: String) {
        _instance.value = _instance.value?.copy(serverName = instanceName)
    }

    fun updateInstanceAddress(address: String) {
        _instance.value = _instance.value?.copy(serverAddress = address)
    }

    fun updateUserName(userName: String) {
        user.value = user.value?.copy(userName = userName)
    }

    fun updatePassword(password: String) {
        user.value = user.value?.copy(password = password)
    }

    fun logIn() {
        repository.refreshToken()
    }

    private fun loadInstances(): LiveData<Resource<List<InstanceInfo>>> {
        return object : OfflineResource<List<InstanceInfo>>(applicationExecutors) {
            override fun loadFromDB(): LiveData<List<InstanceInfo>> {
                return repository.loadAllInstances()
            }
        }.asLiveData()
    }

    private data class User(val userName: String?, val password: String?)
}


