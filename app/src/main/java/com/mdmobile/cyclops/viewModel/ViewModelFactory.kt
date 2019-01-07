package com.mdmobile.cyclops.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mdmobile.cyclops.ui.logIn.loginViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(loginViewModel::class.java)){
            loginViewModel() as T
        } else {
            throw IllegalArgumentException("Unsupported view model class: $modelClass")
        }
    }
}