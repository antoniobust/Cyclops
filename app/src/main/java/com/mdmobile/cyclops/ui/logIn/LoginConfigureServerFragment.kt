package com.mdmobile.cyclops.ui.logIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mdmobile.cyclops.R


class LoginConfigureServerFragment : Fragment(), View.OnFocusChangeListener {

    private lateinit var serverAddressEditText: EditText
    private lateinit var serverNameEditText: EditText
    private lateinit var viewModel: LoginViewModel


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, (activity as LoginActivity).viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_server_name, container, false)
        serverAddressEditText = rootView.findViewById(R.id.instance_address_edit_text)
        serverNameEditText = rootView.findViewById(R.id.instance_name_edit_text)

        serverNameEditText.onFocusChangeListener = this
        serverAddressEditText.onFocusChangeListener = this

        return rootView
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

    companion object {
        fun newInstance(): LoginConfigureServerFragment {
            return LoginConfigureServerFragment()
        }
    }
}
