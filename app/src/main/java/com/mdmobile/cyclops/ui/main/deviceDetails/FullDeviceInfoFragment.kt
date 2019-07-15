package com.mdmobile.cyclops.ui.main.deviceDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.dataModel.api.devices.BasicDevice
import com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity.DEVICE_EXTRA_KEY
import com.mdmobile.cyclops.ui.main.deviceDetails.DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY
import com.mdmobile.cyclops.util.LabelHelper
import java.util.*


class FullDeviceInfoFragment : Fragment() {
    private val infoLabels: Array<String> = LabelHelper.getAllUILabels()
    private lateinit var deviceID: String
    private lateinit var infoRecycler: RecyclerView
    private lateinit var device: BasicDevice

    companion object {
        fun newInstance(deviceID: String, device: BasicDevice): FullDeviceInfoFragment {
            val args = Bundle(1)
            args.putString(DEVICE_ID_EXTRA_KEY, deviceID)
            args.putParcelable(DEVICE_EXTRA_KEY, device)
            val fragment = FullDeviceInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    // -- Lifecycle's methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deviceID = arguments!!.getString(DEVICE_ID_EXTRA_KEY)!!
        device = arguments!!.getParcelable(DEVICE_EXTRA_KEY)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_full_device_information, container, false)

        val infoList = ArrayList<Array<String>>()
        val contentValues = device.toContentValues()
        val keys = contentValues.keySet().toTypedArray()
        var label: String
        //TODO: need to get EXTRA INFO Properties
        for (key in keys) {
            label = LabelHelper.getUiLabelFor(key)
            if (label == "") {
                continue
            }
            infoList.add(arrayOf(label, contentValues.getAsString(key)))
        }

        infoRecycler = rootView.findViewById(R.id.device_details_recycler)
        infoRecycler.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        infoRecycler.adapter = InfoAdapter(infoList, false)

        return rootView
    }

}
