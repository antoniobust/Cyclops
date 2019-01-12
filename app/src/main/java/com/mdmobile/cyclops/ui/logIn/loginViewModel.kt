package com.mdmobile.cyclops.ui.logIn

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mdmobile.cyclops.dataModel.api.newDataClass.InstanceInfo
import com.mdmobile.cyclops.repository.InstanceRepository
import javax.inject.Inject


fun EditText.validate(errorMessage: String, validator: (String) -> Boolean) {
    if (validator(this.text.toString())) {
        this.error = errorMessage
    }
}

class loginViewModel @Inject constructor(private val repository: InstanceRepository) : ViewModel() {

    private lateinit var instance :InstanceInfo

    fun logIn() {

    }

    private fun validateInstance(){

    }

    private fun exists(instanceInfo: InstanceInfo){
        repository.loadInstance(instanceInfo)
    }

    private fun loadInstances(): LiveData<List<InstanceInfo>> {
        return repository.loadAllInstances()
    }


}