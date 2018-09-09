package com.mdmobile.cyclops.ui.settings

import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.preference.ListPreference
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.*
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

    companion object {

        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->
            val stringValue = value.toString()

            if (preference is ListPreference) {
                val index = preference.findIndexOfValue(stringValue)
                preference.setSummary(
                        if (index >= 0)
                            preference.entries[index]
                        else
                            null)
            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.summary = stringValue
            }
            true
        }

        private fun bindPreferenceSummaryToValue(preference: Preference) {
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.context)
                            .getString(preference.key, ""))
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

        override fun onCreate(savedInstanceState: Bundle?) {
            (activity as SettingsActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as SettingsActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
            if (arguments != null) {
                serverName = arguments?.getString("serverExtraKey")
                server = ServerUtility.getServer(serverName)
            }
            super.onCreate(savedInstanceState)
        }

        override fun onCreatePreferences(bundle: Bundle?, key: String?) {
            addPreferencesFromResource(R.xml.instance_preference)
            val root = preferenceManager.preferenceScreen
            val customDataStore = CustomDataStore(context, server)

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


//            val instanceNamePref = EditTextPreference(activity)
//            instanceNamePref.key = server.serverName
//            instanceNamePref.title = getString(R.string.server_name_hint)
//            instanceNamePref.summary = server.serverName
//            instanceNamePref.dialogTitle = getString(R.string.server_name_hint)
//            instanceNamePref.dialogMessage = getString(R.string.server_name_hint)
//
//            preferenceScreen.addPreference(EditTextPreference(activity))

//            val instanceAddressPref = EditTextPreference(activity)
//            instanceNamePref.key = server.serverAddress
//            instanceAddressPref.title = getString(R.string.server_address_hint)
//            instanceAddressPref.summary = server.serverAddress
//            instanceAddressPref.dialogTitle = getString(R.string.server_address_hint)
//            preferenceScreen.addPreference(instanceAddressPref)
//
//
//            val clientIdPref = EditTextPreference(activity)
//            instanceNamePref.key = server.clientId
//            clientIdPref.title = getString(R.string.client_id_hint)
//            clientIdPref.summary = server.clientId
//            clientIdPref.dialogTitle = getString(R.string.client_id_hint)
//            preferenceScreen.addPreference(clientIdPref)
//
//            val apiSecretPref = EditTextPreference(activity)
//            instanceNamePref.key = server.apiSecret
//            apiSecretPref.title = getString(R.string.api_secret_hint)
//            apiSecretPref.summary = server.apiSecret
//            apiSecretPref.dialogTitle = getString(R.string.api_secret_hint)
//            preferenceScreen.addPreference(apiSecretPref)
        }

//        override fun onDestroyView() {
//            super.onDestroyView()
//            val s = Server(instanceNameEditText.title.toString(), instanceAddressEditText.title.toString(),
//                    clientIdEditText.title.toString(), instanceAddressEditText.title.toString(), server!!.serverMajorVersion,
//                    server!!.buildNumber)
//            val dbOp = AsyncDbOp(context?.contentResolver)
//            dbOp.startUpdate(s.describeContents(), Any(), McContract.ServerInfo.buildServerInfoUriWithName(s.serverName),
//                    s.toContentValues(), null, null)
//        }
    }

    class CustomDataStore(val c: Context?, val s: Server?) : PreferenceDataStore() {
        override fun putString(key: String?, value: String?) {
            val dbOp = AsyncDbOp(c?.contentResolver)
            val values = ContentValues()
            values.put(key, value)
            dbOp.startUpdate(1, Any(), McContract.ServerInfo.buildServerInfoUriWithName(s?.serverName),
                    values, null, null)
        }
    }

    class AsyncDbOp(cr: ContentResolver?) : AsyncQueryHandler(cr)
}
