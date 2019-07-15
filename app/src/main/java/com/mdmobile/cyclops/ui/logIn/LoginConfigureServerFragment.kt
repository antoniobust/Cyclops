package com.mdmobile.cyclops.ui.logIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mdmobile.cyclops.R
import kotlinx.android.synthetic.main.fragment_server_name.*


class LoginConfigureServerFragment : Fragment(), View.OnFocusChangeListener {
    companion object {
        fun newInstance(): LoginConfigureServerFragment {
            return LoginConfigureServerFragment()
        }
    }

    //Interfaces
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus) {
            return
        }
        val text = (v as EditText).text.toString()
        when (v.id) {
            R.id.instance_name_edit_text -> (activity as LoginActivity).viewModel.updateInstanceName(text)
            R.id.instance_address_edit_text -> (activity as LoginActivity).viewModel.updateInstanceAddress(text)
            else -> throw IllegalArgumentException("Unknown view ID: " + v.id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_server_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instance_address_edit_text.onFocusChangeListener = this
        instance_name_edit_text.onFocusChangeListener = this
    }

//    fun serverConnectionTest(v: View) {
//        val requestQueue = Volley.newRequestQueue(context)
//        var serverUrl = serverAddressEditText!!.text.toString()
//
//        serverUrl = validateUrl(serverUrl)
//
//        val stringRequest = StringRequest(Request.Method.GET, serverUrl,
//                Response.Listener { }, Response.ErrorListener { })
//    }

}
