package com.mdmobile.cyclops.ui.settings

import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.content.ContentValues
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.*
import com.mdmobile.cyclops.R
import com.mdmobile.cyclops.dataModel.Server
import com.mdmobile.cyclops.provider.McContract
import com.mdmobile.cyclops.utils.ServerUtility
import java.lang.ref.WeakReference


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

    class InstancePrefFragment : PreferenceFragmentCompat(),Preference.OnPreferenceChangeListener {
        private lateinit var instanceNameEditText: EditTextPreference
        private lateinit var instanceAddressEditText: EditTextPreference
        private lateinit var clientIdEditText: EditTextPreference
        private lateinit var secretEditText: EditTextPreference
        private var serverName: String? = ""
        private var server: Server? = Server()
        private lateinit var contentResolver: ContentResolver

        override fun onPreferenceChange(pref: Preference?, value: Any?): Boolean {
            pref?.summary = value.toString()
            return true
        }

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

            instanceAddressEditText = root.findPreference(McContract.ServerInfo.SERVER_ADDRESS) as EditTextPreference
            instanceAddressEditText.summary = server?.serverAddress
            instanceAddressEditText.text = server?.serverAddress
            instanceAddressEditText.setDefaultValue(server?.serverAddress)


            clientIdEditText = root.findPreference(McContract.ServerInfo.CLIENT_ID) as EditTextPreference
            clientIdEditText.text = server?.clientId
            clientIdEditText.summary = server?.clientId
            clientIdEditText.setDefaultValue(server?.clientId)

            secretEditText = root.findPreference(McContract.ServerInfo.CLIENT_SECRET) as EditTextPreference
            secretEditText.summary = server?.apiSecret
            secretEditText.text = server?.apiSecret
            secretEditText.setDefaultValue(server?.apiSecret)
            secretEditText.preferenceDataStore

            instanceNameEditText.preferenceDataStore = customDataStore
            instanceAddressEditText.preferenceDataStore = customDataStore
            clientIdEditText.preferenceDataStore = customDataStore
            secretEditText.preferenceDataStore = customDataStore
            instanceNameEditText.onPreferenceChangeListener = this
            instanceAddressEditText.onPreferenceChangeListener = this
            clientIdEditText.onPreferenceChangeListener = this
            secretEditText.onPreferenceChangeListener = this

            root.addPreference(instanceNameEditText)
            root.addPreference(instanceAddressEditText)
            root.addPreference(clientIdEditText)
            root.addPreference(secretEditText)
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
