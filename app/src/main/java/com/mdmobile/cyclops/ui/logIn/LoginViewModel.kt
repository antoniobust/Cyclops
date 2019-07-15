package com.mdmobile.cyclops.ui.logIn

import android.util.Log
import androidx.lifecycle.*
import com.mdmobile.cyclops.dataModel.Resource
import com.mdmobile.cyclops.dataModel.User
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.repository.InstanceRepository
import com.mdmobile.cyclops.testing.OpenForTesting
import com.mdmobile.cyclops.util.Logger
import okhttp3.internal.Internal
import javax.inject.Inject

@OpenForTesting
class LoginViewModel @Inject constructor(final val repository: InstanceRepository) : ViewModel() {

    private val logTag = LoginViewModel::class.java.simpleName
    private val _instanceName: MutableLiveData<String> = MutableLiveData()
    private val _instanceAddress: MutableLiveData<String> = MutableLiveData()
    private val _clientId: MutableLiveData<String> = MutableLiveData()
    private val _apiSecret: MutableLiveData<String> = MutableLiveData()
    private val _userName: MutableLiveData<String> = MutableLiveData()
    private val _password: MutableLiveData<String> = MutableLiveData()
    private val _user = MediatorLiveData<User>()
    private val _instanceInfo = MediatorLiveData<InstanceInfo>()
    private val _login = MutableLiveData<Int>()
    val serverInfo: LiveData<Resource<InstanceInfo>> = Transformations.switchMap(_login) {
        login()
    }

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
    val userName: LiveData<String>
        get() = _userName
    val password: LiveData<String>
        get() = _password
    val user: LiveData<User>
        get() = _user
//    val serverInfo: LiveData<Resource<InstanceInfo>>
//        get() = serverInfo

    init {
        _login.value = 0
        _instanceInfo.addSource(_instanceName) {
            if (_instanceInfo.value == null) {
                _instanceInfo.value = InstanceInfo(instanceName = it)
            } else {
                _instanceInfo.value = _instanceInfo.value?.copy(instanceName = it)
            }
        }
        _instanceInfo.addSource(_instanceAddress) {
            if (_instanceInfo.value == null) {
                _instanceInfo.value = InstanceInfo(serverAddress = it)
            } else {
                _instanceInfo.value = _instanceInfo.value?.copy(serverAddress = InstanceInfo.validateAddress(it))
            }
        }
        _instanceInfo.addSource(_clientId) {
            if (_instanceInfo.value == null) {
                _instanceInfo.value = InstanceInfo(clientId = it)
            } else {
                _instanceInfo.value = _instanceInfo.value?.copy(clientId = it)
            }
        }
        _instanceInfo.addSource(_apiSecret) {
            if (_instanceInfo.value == null) {
                _instanceInfo.value = InstanceInfo(apiSecret = it)
            } else {
                _instanceInfo.value = _instanceInfo.value?.copy(apiSecret = it)
            }
        }

        _user.addSource(_userName) {
            if (_user.value == null) {
                _user.value = User(userName = it)
            } else {
                _user.value = _user.value?.copy(userName = it)
            }
        }
        _user.addSource(_password) {
            if (_user.value == null) {
                _user.value = User(password = it)
            } else {
                _user.value = _user.value?.copy(password = it)
            }
        }
    }


    fun updateClientId(clientId: String) {
        _clientId.value = clientId
    }

    fun updateApiSecret(apiSecret: String) {
        _apiSecret.value = apiSecret
    }

    fun updateInstanceName(instanceName: String) {
        _instanceName.value = instanceName
    }

    fun updateInstanceAddress(address: String) {
        _instanceAddress.value = address
    }

    fun updateUserName(userName: String) {
        _userName.value = userName
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun loginButtonClick() {
        _login.value = _login.value?.plus(1)
    }

    private fun login(): LiveData<Resource<InstanceInfo>>? {
        val info = _instanceInfo.value
        val u = _user.value
//        return  if (info == null || u?.userName.isNullOrBlank() || u?.password.isNullOrBlank() || !instanceNotDefault(info)) {
//             Resource.error("Invalid credentials", null)
//        } else {
        Logger.log(logTag, "Attempting log-in ${Internal.instance} by server request", Log.INFO)
        return if (info == null) {
            null
        } else {
            Transformations.map(repository.getServerInfo(info)) {
                it
            }
        }
    }
}


