package com.mdmobile.cyclops.ui.main.deviceDetails

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdmobile.cyclops.R

/**
 * Device details header
 */

class DeviceDetailsHeaderFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_device_detail_header, container, true)

}
