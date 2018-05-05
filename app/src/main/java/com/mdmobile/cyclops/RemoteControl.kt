package com.mdmobile.cyclops

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdmobile.cyclops.apiManager.ApiRequestManager
import com.mdmobile.cyclops.utils.Logger
import com.mdmobile.cyclops.utils.ServerUtility
import kotlinx.android.synthetic.main.fragment_remote_control.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient


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
