package com.mdmobile.cyclops.ui.logIn

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.repository.InstanceRepository
import javax.inject.Inject


fun EditText.validate(errorMessage: String, validator: (String) -> Boolean) {
    if (validator(this.text.toString())) {
        this.error = errorMessage
    }
}

class LoginViewModel @Inject constructor(val repository: InstanceRepository) : ViewModel() {

    private var _instance: MutableLiveData<InstanceInfo> = MutableLiveData()
    val instance: LiveData<InstanceInfo>
        get() = _instance
    private var user: MutableLiveData<User> = MutableLiveData()

    init {
        _instance.value = InstanceInfo()
    }

    fun logIn() {
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


    private fun exists(instanceInfo: InstanceInfo) {
        repository.loadInstance(instanceInfo)
    }

    private fun loadInstances(): LiveData<List<InstanceInfo>> {
        return repository.loadAllInstances()
    }

    data class User(val userName: String?, val password: String?)
}


