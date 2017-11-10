package com.mdmobile.pocketconsole.utils;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static com.mdmobile.pocketconsole.ApplicationLoader.applicationContext;

public class ConfigureServerAsyncTask extends AsyncTask<File, Void, ServerXmlConfigParser.ServerInfo> {
    private static final String LOG_TAG = ConfigureServerAsyncTask.class.getSimpleName();
    private Throwable throwable;
    private ServerXmlConfigParser.ServerXmlParse parseCompleteCallback;

    public ConfigureServerAsyncTask(ServerXmlConfigParser.ServerXmlParse callback) {
        parseCompleteCallback = callback;
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
                Logger.log(LOG_TAG, "ServerSetup.xml file not found", Log.ERROR);
            } else {
                Toast.makeText(applicationContext, "Error: server configuration file", Toast.LENGTH_SHORT).show();
                Logger.log(LOG_TAG, "ServerSetup.xml file error: " + throwable.getMessage(), Log.ERROR);
            }
            return;
        }

        Logger.log(LOG_TAG, "ServerSetup.xml file parsed: name = " + info.getServerName() + "\naddress = " + info.getServerName()
                + "\nAPI Secret = " + info.getApiSecret() + "\nclient ID = " + info.getClientId(), Log.VERBOSE);
        parseCompleteCallback.xmlParseComplete();

    }
}
