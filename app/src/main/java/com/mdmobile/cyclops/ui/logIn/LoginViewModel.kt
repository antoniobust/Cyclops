package com.mdmobile.cyclops.ui.logIn

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataModel.api.newDataClass.Token
import com.mdmobile.cyclops.repository.InstanceRepository
import com.mdmobile.cyclops.util.AbsentLiveData
import javax.inject.Inject


fun EditText.validate(errorMessage: String, validator: (String) -> Boolean) {
    if (validator(this.text.toString())) {
        this.error = errorMessage
    }
}

class LoginViewModel @Inject constructor(val repository: InstanceRepository) : ViewModel() {

    private val _instance: MutableLiveData<InstanceInfo> = MutableLiveData()
    private val _user: MutableLiveData<User> = MutableLiveData()
    val token: LiveData<Token>
    private val instanceList: LiveData<List<InstanceInfo>> = repository.loadAllInstances()
    val isDuplicate: LiveData<Boolean>
    val instance: LiveData<InstanceInfo>
        get() = _instance
    val user: LiveData<User>
        get() = _user

    init {
        _instance.postValue(InstanceInfo())
        _user.postValue(User())
        token = Transformations.switchMap(_instance) {
            if (it.token.token.isEmpty()) {
                val tmp = MutableLiveData<Token>()
                tmp.value = it.token
                tmp
            } else {
                AbsentLiveData.create<Token>()
            }
        }
        isDuplicate = Transformations.switchMap(_instance) { instanceInfo ->
            val flag = MutableLiveData<Boolean>()
            flag.value = false
            when {
                instanceList == null -> flag
                instanceList.value.isNullOrEmpty() -> flag
                instanceList.value?.find { it.serverName == instanceInfo.serverName } != null -> {
                    flag.value = true
                    flag
                }
                else -> {
                    flag
                }
            }
        }
    }

//    fun isFirstInstance(): Boolean? {
//        return instanceList.value?.isEmpty()
//    }

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
        _user.value = _user.value?.copy(userName = userName)
    }

    fun updatePassword(password: String) {
        _user.value = _user.value?.copy(password = password)
    }

    fun logIn() {
        repository.getToken()
    }

    data class User(val userName: String = "N/A", val password: String = "N/A")
}


