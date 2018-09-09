package com.mdmobile.cyclops.ui.settings

import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.content.ContentValues
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.*
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.dataModel.Server
import com.mdmobile.cyclops.provider.McContract
import com.mdmobile.cyclops.utils.ServerUtility


class SettingsActivity : AppCompatActivity(), Preference.OnPreferenceClickListener {
    private val serverExtraKey = "serverExtraKey"

    override fun onPreferenceClick(preference: Preference?): Boolean {
        return if (preference?.fragment != null) {
            //TODO: support multi pane view
            val f = Fragment.instantiate(this, preference.fragment)
            val args = Bundle()
            args.putString(serverExtraKey, preference.key)
            f.arguments = args
            supportFragmentManager.beginTransaction()
                    .addToBackStack(SettingsActivity::class.java.name)
                    .replace(R.id.pref_container, f)
                    .commit()
            true
        } else {
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportFragmentManager.beginTransaction().add(R.id.pref_container, GeneralPreferenceFragment()).commit()
    }

    class GeneralPreferenceFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(args: Bundle?, rootKey: String?) {
            val preferenceScreen = preferenceManager.createPreferenceScreen(context)
            setPreferenceScreen(preferenceScreen)
            val instancesCategory = PreferenceCategory(context)
            instancesCategory.title = getString(R.string.pref_instance_category_title)
            preferenceScreen.addPreference(instancesCategory)

            val instances = ServerUtility.getAllInstances()
            var pref: Preference
            for (s: Server in instances) {
                pref = Preference(context)
                pref.title = s.serverName
                pref.key = s.serverName
                pref.onPreferenceClickListener = (activity as Preference.OnPreferenceClickListener)
                pref.fragment = InstancePrefFragment::class.java.name
                instancesCategory.addPreference(pref)
            }
        }
    }

    class InstancePrefFragment : PreferenceFragmentCompat() {
        private lateinit var instanceNameEditText: EditTextPreference
        private lateinit var instanceAddressEditText: EditTextPreference
        private lateinit var clientIdEditText: EditTextPreference
        private lateinit var secretEditText: EditTextPreference
        private var serverName: String? = ""
        private var server: Server? = Server()
        lateinit var contentResolver: ContentResolver
        private val updateHandler: Handler = Handler(Looper.getMainLooper()) {
            if (it.what == 101) {
                true
            } else {
                false
            }
        }
        private val contentObserver = UriContentObserver(updateHandler)

        override fun onCreate(savedInstanceState: Bundle?) {
            (activity as SettingsActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as SettingsActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
            if (arguments != null) {
                serverName = arguments?.getString("serverExtraKey")
                server = ServerUtility.getServer(serverName)
            }
            if (context != null) {
                contentResolver = context!!.contentResolver
            }

            contentResolver.registerContentObserver(
                    McContract.ServerInfo.buildServerInfoUriWithName(serverName),
                    false, contentObserver)
            super.onCreate(savedInstanceState)
        }

        override fun onCreatePreferences(bundle: Bundle?, key: String?) {
            addPreferencesFromResource(R.xml.instance_preference)
            val root = preferenceManager.preferenceScreen
            val customDataStore = CustomDataStore(contentResolver, server)

            instanceNameEditText = root.findPreference(McContract.ServerInfo.NAME) as EditTextPreference
            instanceNameEditText.summary = serverName
            instanceNameEditText.setDefaultValue(serverName)
            instanceNameEditText.text = serverName
            instanceNameEditText.preferenceDataStore = customDataStore

            instanceAddressEditText = root.findPreference(McContract.ServerInfo.SERVER_ADDRESS) as EditTextPreference
            instanceAddressEditText.summary = server?.serverAddress
            instanceAddressEditText.text = server?.serverAddress
            instanceAddressEditText.setDefaultValue(server?.serverAddress)

            instanceAddressEditText.preferenceDataStore = customDataStore

            clientIdEditText = root.findPreference(McContract.ServerInfo.CLIENT_ID) as EditTextPreference
            clientIdEditText.text = server?.clientId
            clientIdEditText.summary = server?.clientId
            clientIdEditText.setDefaultValue(server?.clientId)
            clientIdEditText.preferenceDataStore = customDataStore

            secretEditText = root.findPreference(McContract.ServerInfo.CLIENT_SECRET) as EditTextPreference
            secretEditText.summary = server?.apiSecret
            secretEditText.text = server?.apiSecret
            secretEditText.setDefaultValue(server?.apiSecret)
            secretEditText.preferenceDataStore
            secretEditText.preferenceDataStore = customDataStore

            root.addPreference(instanceNameEditText)
            root.addPreference(instanceAddressEditText)
            root.addPreference(clientIdEditText)
            root.addPreference(secretEditText)
        }

        override fun onPause() {
            super.onPause()
            contentResolver.unregisterContentObserver(contentObserver)
        }

        class UriContentObserver(private val handler: Handler) : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean, uri: Uri) {
                val msg = Message()
                msg.what = 101
                handler.sendMessage(msg)
            }
        }
    }


    class CustomDataStore(private val contentResolver: ContentResolver?, val s: Server?) : PreferenceDataStore() {
        override fun putString(key: String?, value: String?) {
            val dbOp = AsyncDbOp(contentResolver)
            val values = ContentValues()
            values.put(key, value)
            dbOp.startUpdate(1, Any(), McContract.ServerInfo.buildServerInfoUriWithName(s?.serverName),
                    values, null, null)
        }
    }

    class AsyncDbOp(cr: ContentResolver?) : AsyncQueryHandler(cr)
}
