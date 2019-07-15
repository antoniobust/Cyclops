package com.mdmobile.cyclops.ui.logIn

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.Chart.LOG_TAG
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.adapters.LogInViewPagerAdapter
import com.mdmobile.cyclops.dataModel.Instance
import com.mdmobile.cyclops.util.ConfigureServerAsyncTask
import com.mdmobile.cyclops.util.GeneralUtility
import com.mdmobile.cyclops.util.Logger
import com.mdmobile.cyclops.util.ServerXmlConfigParser
import kotlinx.android.synthetic.main.fragment_add_server.*
import java.io.File
import java.util.*

/**
 * Fragment displayed to add a new server details
 */

class AddInstanceFragment : Fragment(), ServerXmlConfigParser.ServerXmlParse {

    private var xmlParsedFlag: Boolean = false

    companion object {
        const val EXTERNAL_STORAGE_READ_PERMISSION = 100
        fun newInstance(): AddInstanceFragment {
            return AddInstanceFragment()
        }
    }

    //Interfaces
    override fun xmlParseComplete(allInstanceInfo: ArrayList<Instance>) {
//        rootView!!.findViewById<View>(R.id.server_conf_read_label).visibility = View.VISIBLE
//        (Objects.requireNonNull<FragmentActivity>(activity) as LoginActivity).instanceList.addAll(allInstanceInfo)
//        xmlParsedFlag = true
    }


    //Lifecycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_server, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPagerSetup()

        val hosting = Objects.requireNonNull<FragmentActivity>(activity) as LoginActivity
        hosting.actionChip.setText(R.string.add_instance_label)
        hosting.actionChip
                .setCompoundDrawablesWithIntrinsicBounds(Objects.requireNonNull<Context>(context).getDrawable(R.drawable.ic_add), null, null, null)
    }

    override fun onResume() {
        super.onResume()
        if (checkConfigurationFile()) {
            if (checkStoragePermission()) {
                parseServerConfigFile()
            }
        }
    }

    private fun setViewPagerSetup() {
        val viewPagerAdapter = LogInViewPagerAdapter(childFragmentManager)
        login_add_server_view_pager.adapter = viewPagerAdapter
        login_add_server_view_pager.pageMargin = 80
        login_view_pager_dots_indicator.setupWithViewPager(login_add_server_view_pager, true)

        var dot: View
        val dotsContainer = login_view_pager_dots_indicator.getChildAt(0) as ViewGroup
        var p: ViewGroup.MarginLayoutParams

        for (i in 0 until login_view_pager_dots_indicator.tabCount) {
            dot = dotsContainer.getChildAt(i)
            p = dot.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(5, 0, 5, 0)
            dot.requestLayout()
        }
    }

    private fun checkConfigurationFile(): Boolean {
        return if (!File(Environment.getExternalStorageDirectory().toString() + File.separator + getString(R.string.server_ini_file_name)).exists()) {
            Logger.log(LOG_TAG, "ServerInfo configuration xml file not found", Log.VERBOSE)
            false
        } else {
            Logger.log(LOG_TAG, "ServerInfo configuration xml file found", Log.INFO)
            true
        }
    }

    private fun checkStoragePermission(): Boolean {
        return if (!GeneralUtility.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull<FragmentActivity>(activity), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                false
            } else {
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), EXTERNAL_STORAGE_READ_PERMISSION)
                false
            }
        } else {
            true
        }
    }

    internal fun parseServerConfigFile() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY) {
            val serverSetupFile = File(Environment.getExternalStorageDirectory(), getString(R.string.server_ini_file_name))
            val configureServerAsyncTask = ConfigureServerAsyncTask(this)
            configureServerAsyncTask.execute(serverSetupFile)
        } else {
            Logger.log(LOG_TAG, "Storage not available at the moment", Log.INFO)
            Toast.makeText(context, "Storage not available", Toast.LENGTH_SHORT).show()
        }
    }
}
