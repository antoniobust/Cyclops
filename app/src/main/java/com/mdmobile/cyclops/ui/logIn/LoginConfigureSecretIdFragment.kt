package com.mdmobile.cyclops.ui.logIn


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mdmobile.cyclops.R

class LoginConfigureSecretIdFragment : Fragment(), View.OnFocusChangeListener {

    private lateinit var clientIdEditText: EditText
    private lateinit var apiSecretEditText: EditText
    private lateinit var viewModel: LoginViewModel


    //Interfaces
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus) {
            return
        }
        val text = (v as EditText).text.toString()
        when (v.id) {
            R.id.instance_client_id_edit_text -> (activity as LoginActivity).viewModel.updateClientId(text)
            R.id.instance_secret_edit_text -> (activity as LoginActivity).viewModel.updateApiSecret(text)
            else -> throw IllegalArgumentException("View not known: ${v.id}")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, (activity as LoginActivity).viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_api_client_secret, container, false)
        clientIdEditText = rootView.findViewById(R.id.instance_client_id_edit_text)
        apiSecretEditText = rootView.findViewById(R.id.instance_secret_edit_text)

        clientIdEditText.onFocusChangeListener = this
        apiSecretEditText.onFocusChangeListener = this

        return rootView
    }

    companion object {

        fun newInstance(): LoginConfigureSecretIdFragment {
            return LoginConfigureSecretIdFragment()
        }
    }
}
