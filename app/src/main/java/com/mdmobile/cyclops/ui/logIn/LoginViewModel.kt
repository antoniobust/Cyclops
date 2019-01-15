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

    private var instance: MutableLiveData<InstanceInfo> = MutableLiveData()
    private var user: MutableLiveData<User> = MutableLiveData()


    fun logIn() {
    }


    fun updateClientId(clientId: String) {
        val newInstance = instance.value?.copy(clientId = clientId)
        instance.value = newInstance
    }

    fun updateApiSecret(apiSecret: String) {
        val newInstance = instance.value?.copy(apiSecret = apiSecret)
        instance.value = newInstance
    }

    fun updateInstanceName(instanceName: String) {
        val newInstance = instance.value?.copy(serverName = instanceName)
        instance.value = newInstance
    }

    fun updateInstanceAddress(address: String) {
        val newInstance = instance.value?.copy(serverAddress = address)
        instance.value = newInstance
    }

    fun updateUserName(userName: String) {
        val newUser = user.value?.copy(userName = userName)
        user.value = newUser
    }

    fun updatePassword(password: String) {
        val newUser = user.value?.copy(password = password)
        user.value = newUser
    }


    private fun exists(instanceInfo: InstanceInfo) {
        repository.loadInstance(instanceInfo)
    }

    private fun loadInstances(): LiveData<List<InstanceInfo>> {
        return repository.loadAllInstances()
    }

    data class User(val userName: String?, val password: String?)
}


