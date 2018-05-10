package com.mdmobile.cyclops;


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.cyclops.dataModel.chart.Chart;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.ui.main.dashboard.ChartFactory;
import com.mdmobile.cyclops.utils.GeneralUtility;
import com.mdmobile.cyclops.utils.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ApplicationLoader extends Application {
    public static volatile Context applicationContext;
    private String LOG_TAG = ApplicationLoader.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        boolean firstLoad = applicationContext.getSharedPreferences(getString(R.string.general_shared_preference), MODE_PRIVATE)
                .getBoolean(getString(R.string.first_boot_preference), true);

        if (firstLoad) {
            ArrayList<Chart> defaultChart = new ArrayList<>(3);
            defaultChart.add(0, new Chart(ChartFactory.PIE_CHART, McContract.Device.COLUMN_AGENT_ONLINE, "N/A"));
            defaultChart.add(1, new Chart(ChartFactory.PIE_CHART, McContract.Device.COLUMN_MANUFACTURER, "N/A"));
            defaultChart.add(2, new Chart(ChartFactory.PIE_CHART, McContract.Device.COLUMN_COMPLIANCE_STATUS, "N/A"));

            Type deviceCollectionType = new TypeToken<ArrayList<Chart>>() {
            }.getType();
            Gson gson = new Gson();
            String json = gson.toJson(defaultChart, deviceCollectionType);
            GeneralUtility.setSharedPreference(applicationContext, getString(R.string.charts_preference), json);

            Logger.log(LOG_TAG,
                    "First boot... Added following charts:\n" + json.replace("},{", "}\n{") + "}\n in saved charts",
                    Log.VERBOSE);

        }


        if (Build.VERSION.SDK_INT >= 26) {
            final NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            final String id = getPackageName();
            final String channelName = getString(R.string.notification_channel_name);
            final String description = getString(R.string.notification_channel_description);
            final int priority = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(id, channelName, priority);
            notificationChannel.setDescription(description);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}