package com.mdmobile.pocketconsole.ui.deviceDetails

import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdmobile.pocketconsole.R
import com.mdmobile.pocketconsole.adapters.LabelsCursorAdapter
import com.mdmobile.pocketconsole.provider.McContract
import com.mdmobile.pocketconsole.ui.deviceDetails.DeviceDetailsActivity.DEVICE_ID_EXTRA_KEY
import com.mdmobile.pocketconsole.utils.LabelHelper


class FullDeviceInfoFragment : Fragment(), android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {
    private val infoLabels: Array<String> = LabelHelper.getAllUILabels()
    private lateinit var deviceID: String
    private lateinit var infoRecycler: RecyclerView

    companion object {
        fun newInstance(deviceID: String): FullDeviceInfoFragment {
            val args = Bundle(1)
            args.putString(DEVICE_ID_EXTRA_KEY, deviceID)
            val fragment = FullDeviceInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    // -- Interface methods
    override fun onCreateLoader(id: Int, args: Bundle): android.support.v4.content.Loader<Cursor> {
        return android.support.v4.content.CursorLoader(context!!, McContract.Device.buildUriWithDeviceID(deviceID), null, null, null, null)
    }

    override fun onLoadFinished(loader: android.support.v4.content.Loader<Cursor>, data: Cursor) {
        infoRecycler.swapAdapter(LabelsCursorAdapter(data, infoLabels), true)
    }

    override fun onLoaderReset(loader: android.support.v4.content.Loader<Cursor>) {
        infoRecycler.swapAdapter(null, false)
    }

    // -- Lifecycle's methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deviceID = arguments!!.getString(DEVICE_ID_EXTRA_KEY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_full_device_information, container, false)

        infoRecycler = rootView.findViewById(R.id.device_details_recycler)
        infoRecycler.layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)

        infoRecycler.adapter = LabelsCursorAdapter(null, infoLabels)
        loaderManager.initLoader(200, Bundle(), this)
        return rootView
    }
    
}
