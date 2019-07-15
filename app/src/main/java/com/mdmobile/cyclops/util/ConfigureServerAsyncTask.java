package com.mdmobile.cyclops.util;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mdmobile.cyclops.dataModel.Instance;
import com.mdmobile.cyclops.ui.logIn.AddInstanceFragment;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ConfigureServerAsyncTask extends AsyncTask<File, Void, ArrayList<Instance>> {
    private static final String LOG_TAG = ConfigureServerAsyncTask.class.getSimpleName();
    private final WeakReference<AddInstanceFragment> hostingFragment;
    private Throwable throwable;
    private ServerXmlConfigParser.ServerXmlParse parseCompleteCallback;

    public ConfigureServerAsyncTask(ServerXmlConfigParser.ServerXmlParse callback) {
        parseCompleteCallback = callback;
        hostingFragment = new WeakReference<>((AddInstanceFragment) callback);
    }

    @Override
    protected ArrayList<Instance> doInBackground(File... serverSetupFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(serverSetupFile[0]);
            ServerXmlConfigParser fileParser = new ServerXmlConfigParser();
            return fileParser.parseXml(fileInputStream);
        } catch (IOException | XmlPullParserException e) {
            throwable = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Instance> info) {
        if (info == null) {
            if (throwable instanceof FileNotFoundException) {
                Logger.log(LOG_TAG, "No ServerSetup.xml file found", Log.INFO);
            } else {
                Toast.makeText(hostingFragment.get().getContext(), "Error: server configuration file", Toast.LENGTH_SHORT).show();
                Logger.log(LOG_TAG, "ServerSetup.xml file error: " + throwable.getMessage(), Log.ERROR);
            }
            return;
        }

        Logger.log(LOG_TAG, "ServerSetup.xml file parsed, servers found: " + info.size(), Log.VERBOSE);
        parseCompleteCallback.xmlParseComplete(info);
    }
}
