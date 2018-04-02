package com.mdmobile.cyclops;


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

public class ApplicationLoader extends Application {
    public static volatile Context applicationContext;
    private String LOG_TAG = ApplicationLoader.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();

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