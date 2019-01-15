package com.mdmobile.cyclops.ui.logIn


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.mdmobile.cyclops.R

class LoginConfigureSecretIdFragment : Fragment(), View.OnFocusChangeListener {

    private var clientIdEditText: EditText? = null
    private var apiSecretEditText: EditText? = null

    //Interfaces
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus) {
            return
        }

        when (v.id) {
            R.id.client_id_text_view -> (activity as LoginActivity).viewModel.updateClientId(((v as EditText).text.toString()))
            R.id.api_secret_text_view -> (activity as LoginActivity).viewModel.updateApiSecret(((v as EditText).text.toString()))
            else -> throw IllegalArgumentException("View not known: ${v.id}")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_api_client_secret, container, false)
        clientIdEditText = rootView.findViewById(R.id.client_id_text_view)
        apiSecretEditText = rootView.findViewById(R.id.api_secret_text_view)

        clientIdEditText!!.onFocusChangeListener = this
        apiSecretEditText!!.onFocusChangeListener = this

        return rootView
    }

    companion object {

        fun newInstance(): LoginConfigureSecretIdFragment {
            return LoginConfigureSecretIdFragment()
        }
    }
}
