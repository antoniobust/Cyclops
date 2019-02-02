package com.mdmobile.cyclops.ui.logIn

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.api.ApiRequestManager
import com.mdmobile.cyclops.interfaces.NetworkCallBack
import com.mdmobile.cyclops.security.ServerNotFound
import com.mdmobile.cyclops.utils.Logger
import com.mdmobile.cyclops.utils.ServerUtility
import kotlinx.android.synthetic.main.fragment_add_user.*
import java.util.*


class LogIngConfigureUserFragment : Fragment(), View.OnFocusChangeListener {
    private lateinit var userNameView: EditText
    private lateinit var passwordView: EditText
    private lateinit var viewModel: LoginViewModel
    private val pwdVisibilityListener = View.OnTouchListener { view, motionEvent ->
        view.performClick()
        if (motionEvent.rawX >= passwordView.right - passwordView.compoundDrawables[2].bounds.width()) {
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    passwordView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, view.context.getDrawable(R.drawable.ic_visibility_off), null)
                    passwordView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_DOWN -> {
                    passwordView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, view.context.getDrawable(R.drawable.ic_visibility_on), null)
                    passwordView.inputType = InputType.TYPE_CLASS_TEXT
                    return@OnTouchListener true
                }
                else -> return@OnTouchListener false
            }
        }
        false
    }

    //Interfaces
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus) {
            return
        }
        val text = (v as EditText).text.toString()
        when (v.id) {
            R.id.user_name_text_view -> viewModel.updateUserName(text)
            R.id.password_text_view -> viewModel.updatePassword(text)
            else -> throw IllegalArgumentException("Unknown view ID: " + v.id)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, (activity as LoginActivity).viewModelFactory).get(LoginViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_add_user, container, false)
        userNameView = rootView.findViewById(R.id.user_name_text_view)
        passwordView = rootView.findViewById(R.id.password_text_view)

        userNameView.onFocusChangeListener = this
        passwordView.onFocusChangeListener = this

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(USER_NAME_KEY)) {
                userNameView.setText(savedInstanceState.getString(USER_NAME_KEY))
            }
            if (savedInstanceState.containsKey(PASSWORD_KEY)) {
                passwordView.setText(savedInstanceState.getString(PASSWORD_KEY))
            }
        }
        val hosting = Objects.requireNonNull<FragmentActivity>(activity) as LoginActivity
        hosting.actionChip.setText(R.string.logIn_label)
        hosting.actionChip
                .setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        passwordView.setOnTouchListener(pwdVisibilityListener)
        return rootView
    }

    fun logIn() {
        val userName = userNameView.text.toString()
        val password = passwordView.text.toString()
        if (!(userName.isNotEmpty() && password.isNotEmpty())) {
            return
        }

        Logger.log(LOG_TAG, "Requesting token...", Log.VERBOSE)
        (activity as LoginActivity).actionChip.visibility = View.GONE
        (activity as LoginActivity).progressBar.visibility = View.VISIBLE
        try {
            ApiRequestManager.getInstance().getToken(
                    ServerUtility.getActiveServer(),
                    userName, password, activity as NetworkCallBack)
        } catch (e: ServerNotFound) {
            Toast.makeText(context, "Add at least one instance...", Toast.LENGTH_LONG).show()
            (activity as LoginActivity).progressBar.visibility = View.GONE
            (activity as LoginActivity).actionChip.visibility = View.VISIBLE
        }

    }

    companion object {

        private val LOG_TAG = LogIngConfigureUserFragment::class.java.simpleName
        private const val USER_NAME_KEY = "USER_NAME_KEY"
        private const val PASSWORD_KEY = "PASSWORD_KEY"

        fun newInstance(): LogIngConfigureUserFragment {
            return LogIngConfigureUserFragment()
        }
    }
}