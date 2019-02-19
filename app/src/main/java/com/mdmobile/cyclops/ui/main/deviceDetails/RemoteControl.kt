package com.mdmobile.cyclops.ui.main.deviceDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.api.ApiRequestManager
import com.mdmobile.cyclops.util.Logger
import com.mdmobile.cyclops.util.ServerUtility
import kotlinx.android.synthetic.main.fragment_remote_control.*


class RemoteControl : Fragment() {
    private val deviceIdKey = "RC_ADDRESS_ARG_KEY"
    private lateinit var deviceId: String

    companion object {
        @JvmStatic
        fun newInstance(deviceId: String) =
                RemoteControl().apply {
                    arguments = Bundle().apply {
                        putString(deviceIdKey, deviceId)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deviceId = arguments?.get(deviceIdKey) as String
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_remote_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rcAddress = ApiRequestManager.getInstance().remoteControlDevice(ServerUtility.getActiveServer(), deviceId)
        Logger.log(RemoteControl::class.java.simpleName, "Loading RC url: $rcAddress", Log.VERBOSE)

        val mwe = remote_control_web_view
        mwe.settings.javaScriptEnabled = true
        mwe.settings.javaScriptCanOpenWindowsAutomatically = true
        mwe.settings.domStorageEnabled = true
        mwe.webChromeClient = WebChromeClient()
        mwe.webViewClient = WebViewClient()
        mwe.loadUrl(rcAddress)


    }
}
