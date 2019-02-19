package com.mdmobile.cyclops.sync

import android.content.Context
import android.util.Log
import com.mdmobile.cyclops.api.ApiRequestManager
import com.mdmobile.cyclops.dataModel.Instance
import com.mdmobile.cyclops.sync.SyncService.*
import com.mdmobile.cyclops.util.Logger

class SyncServiceHelper {
    companion object {
        private val LOG_TAG = SyncServiceHelper::class.java.simpleName
        fun sync(context: Context, instanceList: List<Instance>, actions: ArrayList<String>) {

            Logger.log(LOG_TAG, "Syncing servers...\nAction to perform:" + actions.toString(), Log.VERBOSE)
//            val intent = Intent(MainActivity.UPDATE_LOADING_BAR_ACTION)
//            intent.putExtra(MainActivity.UPDATE_LOADING_BAR_ACTION_COUNT, actions.size)
//            intent.setPackage(getContext().getPackageName())
            for (server in instanceList) {
                Logger.log(LOG_TAG, "Syncing ${server.serverName}...", Log.VERBOSE)
                for (action in actions) {
                    when (action) {
                        SYNC_DEVICES ->
                            //context.sendBroadcast(intent)
                            ApiRequestManager.getInstance().getDeviceInfo(server)
                        SYNC_SERVER ->
                            //context.sendBroadcast(intent)
                            ApiRequestManager.getInstance().getServicesInfo(server)
                        SYNC_USERS ->
                            //context.sendBroadcast(intent)
                            ApiRequestManager.getInstance().getUsers(server)
                    }
                }
            }
        }
    }
}