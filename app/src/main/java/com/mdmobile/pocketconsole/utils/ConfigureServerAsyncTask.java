package com.mdmobile.pocketconsole.utils;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mdmobile.pocketconsole.ui.logIn.AddServerFragment;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ConfigureServerAsyncTask extends AsyncTask<File, Void, ServerXmlConfigParser.ServerInfo> {
    private static final String LOG_TAG = ConfigureServerAsyncTask.class.getSimpleName();
    private final WeakReference<AddServerFragment> hostingFragment;
    private Throwable throwable;
    private ServerXmlConfigParser.ServerXmlParse parseCompleteCallback;

    public ConfigureServerAsyncTask(ServerXmlConfigParser.ServerXmlParse callback) {
        parseCompleteCallback = callback;
        hostingFragment = new WeakReference<>((AddServerFragment) callback);
    }

    @Override
    protected ServerXmlConfigParser.ServerInfo doInBackground(File... serverSetupFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(serverSetupFile[0]);
            ServerXmlConfigParser fileParser = new ServerXmlConfigParser();
            ArrayList<ServerXmlConfigParser.ServerInfo> serverInfo = fileParser.parseXml(fileInputStream);
            //TODO:support multiple servers
            ServerXmlConfigParser.ServerInfo info = serverInfo.get(0);
            ServerUtility.saveServerInfo(info.getServerName(), info.getApiSecret(), info.getClientId(), info.getServerAddress());
            return info;
        } catch (IOException | XmlPullParserException e) {
            throwable = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(ServerXmlConfigParser.ServerInfo info) {
        if (info == null) {
            if (throwable instanceof FileNotFoundException) {
                Logger.log(LOG_TAG, "No ServerSetup.xml file found", Log.INFO);
            } else {
                Toast.makeText(hostingFragment.get().getContext(), "Error: server configuration file", Toast.LENGTH_SHORT).show();
                Logger.log(LOG_TAG, "ServerSetup.xml file error: " + throwable.getMessage(), Log.ERROR);
            }
            return;
        }

        Logger.log(LOG_TAG, "ServerSetup.xml file parsed: name = " + info.getServerName() + "\naddress = "
                + info.getServerAddress() + "\nAPI Secret = " + info.getApiSecret() + "\nclient ID = "
                + info.getClientId(), Log.VERBOSE);
        parseCompleteCallback.xmlParseComplete();

    }
}
