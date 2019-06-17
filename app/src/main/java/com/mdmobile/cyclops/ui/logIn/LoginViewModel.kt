package com.mdmobile.cyclops.ui.logIn

import android.widget.EditText
import androidx.lifecycle.*
import com.mdmobile.cyclops.dataModel.Instance
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.dataModel.api.newDataClass.Token
import com.mdmobile.cyclops.repository.InstanceRepository
import com.mdmobile.cyclops.testing.OpenForTesting
import javax.inject.Inject


fun EditText.validate(errorMessage: String, validator: (String) -> Boolean) {
    if (validator(this.text.toString())) {
        this.error = errorMessage
    }
}

@OpenForTesting
class LoginViewModel @Inject constructor(final val repository: InstanceRepository) : ViewModel() {

    private val _instanceInfo: MutableLiveData<InstanceInfo> = MutableLiveData()
    private val _instanceName: MutableLiveData<String> = MutableLiveData()
    private val _instanceAddress: MutableLiveData<String> = MutableLiveData()
    private val _clientId: MutableLiveData<String> = MutableLiveData()
    private val _apiSecret: MutableLiveData<String> = MutableLiveData()
    private val _userName: MutableLiveData<String> = MutableLiveData()
    private val _password: MutableLiveData<String> = MutableLiveData()
    private val _user: MutableLiveData<User> = MutableLiveData()
    private val _token: MutableLiveData<Resource<Token>> = MutableLiveData()
    val userMediatorLiveData = MediatorLiveData<User>()
    val instanceMediatorLiveData = MediatorLiveData<InstanceInfo>()

    val instanceInfo: LiveData<InstanceInfo>
        get() = _instanceInfo
    val instanceName: LiveData<String>
        get() = _instanceName
    val instanceAddress: LiveData<String>
        get() = _instanceAddress
    val clientId: LiveData<String>
        get() = _clientId
    val apiSecret: LiveData<String>
        get() = _apiSecret
    val user: LiveData<User>
        get() = _user

    val token: LiveData<Resource<Token>>
        get() = _token


//    private val _user: MutableLiveData<User> = MutableLiveData()
//    private val _instanceList: LiveData<List<InstanceInfo>> = repository.loadAllInstances()
//    private var _token: MutableLiveData<Resource<Token>> = MutableLiveData()
//    final val instanceInfo: LiveData<InstanceInfo>
//        get() = _instanceInfo

//    val instanceList: LiveData<List<InstanceInfo>>
//        get() = _instanceList


    init {
        _instanceInfo.postValue(InstanceInfo())
        _instanceName.postValue("")
        _instanceAddress.postValue("")
        _clientId.postValue("")
        _apiSecret.postValue("")
        _userName.postValue("")
        _password.postValue("")

        userMediatorLiveData.addSource(_userName) {
            val current = _user.value
            _user.value = current?.copy(userName = it)
        }

        userMediatorLiveData.addSource(_password) {
            val current = _user.value
            _user.value = current?.copy(password = it)
        }
        instanceMediatorLiveData.addSource(_instanceName) {
            _instanceInfo.value = _instanceInfo.value?.copy(serverName = it)
        }
        instanceMediatorLiveData.addSource(_instanceAddress) {
            _instanceInfo.value = _instanceInfo.value?.copy(serverAddress = InstanceInfo.validateAddress(it))
        }
        instanceMediatorLiveData.addSource(_clientId) {
            _instanceInfo.value = _instanceInfo.value?.copy(clientId = it)
        }
        instanceMediatorLiveData.addSource(_apiSecret) {
            _instanceInfo.value = _instanceInfo.value?.copy(apiSecret = it)
        }
    }

//    fun addServer(): Long {
//        val instanceInfo = _instance.value ?: return -1
//        return repository.saveInstance(instanceInfo)
//    }

    fun updateClientId(clientId: String) {
        _clientId.value = clientId
    }

    fun updateApiSecret(apiSecret: String) {
        _apiSecret.value = apiSecret
    }

    fun updateInstanceName(instanceName: String) {
        _instanceName.value =  InstanceInfo.validateAddress(instanceName)
    }

    fun updateInstanceAddress(address: String) {
        _instanceAddress.value = address
    }

    fun updateUserName(userName: String) {
        _user.value = _user.value?.copy(userName = userName)
    }

    fun updatePassword(password: String) {
        _user.value = _user.value?.copy(password = password)
    }

    fun logIn() {
        val instance = _instanceInfo.value ?: return
        val token = repository.getToken(instance)
    }

    fun instanceNotDefault(instanceInfo: InstanceInfo): Boolean {
        return !(instanceInfo.serverAddress.isEmpty() || instanceInfo.serverAddress == "https:\\\\N/A/"
                || instanceInfo.apiSecret.isEmpty() || instanceInfo.apiSecret == "N/A"
                || instanceInfo.clientId.isEmpty() || instanceInfo.clientId == "N/A"
                || instanceInfo.serverName.isEmpty() || instanceInfo.serverName == "N/A")
    }

    fun userNotDefault(user: User): Boolean {
        return !(user.userName.isBlank() || user.userName == "N/A"
                || user.password.isBlank() || user.password == "N/A")
    }

    data class User(val userName: String = "N/A", val password: String = "N/A")
}


