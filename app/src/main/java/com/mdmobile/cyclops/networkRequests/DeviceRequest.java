package com.mdmobile.cyclops.networkRequests;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.cyclops.dataModel.Server;
import com.mdmobile.cyclops.dataModel.api.RuntimeTypeAdapterFactory;
import com.mdmobile.cyclops.dataModel.api.devices.AndroidForWork;
import com.mdmobile.cyclops.dataModel.api.devices.AndroidGeneric;
import com.mdmobile.cyclops.dataModel.api.devices.AndroidPlus;
import com.mdmobile.cyclops.dataModel.api.devices.BasicDevice;
import com.mdmobile.cyclops.dataModel.api.devices.IosDevice;
import com.mdmobile.cyclops.dataModel.api.devices.IosDeviceV14;
import com.mdmobile.cyclops.dataModel.api.devices.NotYetSupportedDevice;
import com.mdmobile.cyclops.dataModel.api.devices.SamsungElm;
import com.mdmobile.cyclops.dataModel.api.devices.WindowsCE;
import com.mdmobile.cyclops.dataModel.api.devices.WindowsDesktop;
import com.mdmobile.cyclops.dataModel.api.devices.WindowsDesktopLegacy;
import com.mdmobile.cyclops.dataModel.api.devices.WindowsPhone;
import com.mdmobile.cyclops.dataModel.api.devices.WindowsRuntime;
import com.mdmobile.cyclops.dataTypes.DeviceKind;
import com.mdmobile.cyclops.provider.McContract;
import com.mdmobile.cyclops.sec.ServerNotFound;
import com.mdmobile.cyclops.ui.main.MainActivity;
import com.mdmobile.cyclops.utils.Logger;
import com.mdmobile.cyclops.utils.ServerUtility;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.mdmobile.cyclops.ApplicationLoader.applicationContext;

/**
 * Request devices
 * Pass API request in the constructor
 */

public class DeviceRequest<T> extends BasicRequest<T> {

    public final static int ERASE_OLD_DEVICE_INFO = 1;
    public final static int UPDATE_EXISTING_DEVICE_INFO = 2;
    private Response.Listener<T> listener;
    private Context mContext;
    private int insertInfoMethod;
    private Server server;


    public DeviceRequest(Context context, int method, String url, Server server, Response.Listener<T> listener,
                         Response.ErrorListener errorListener, int insertDataMethod) {
        super(method, url, errorListener);

        this.mContext = context.getApplicationContext();
        this.listener = listener;
        insertInfoMethod = insertDataMethod;
        this.server = server;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        Intent intent = new Intent(MainActivity.UPDATE_LOADING_BAR_ACTION);
        intent.setPackage(applicationContext.getPackageName());
        try {

            String jsonResponseString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            final RuntimeTypeAdapterFactory<BasicDevice> typeFactory = RuntimeTypeAdapterFactory
                    .of(BasicDevice.class, "Kind")
                    .registerSubtype(IosDevice.class, DeviceKind.IOS)
                    .registerSubtype(IosDeviceV14.class, DeviceKind.IOS_V14)
                    .registerSubtype(AndroidGeneric.class, DeviceKind.ANDROID_GENERIC)
                    .registerSubtype(AndroidForWork.class, DeviceKind.ANDROID_FOR_WORK)
                    .registerSubtype(AndroidPlus.class, DeviceKind.ANDROID_PLUS)
                    .registerSubtype(SamsungElm.class, DeviceKind.ANDROID_ELM)
                    .registerSubtype(WindowsDesktop.class, DeviceKind.WINDOWS_DESKTOP)
                    .registerSubtype(WindowsDesktopLegacy.class, DeviceKind.WINDOWS_DESKTOP_LEGACY)
                    .registerSubtype(WindowsPhone.class, DeviceKind.WINDOWS_PHONE)
                    .registerSubtype(WindowsRuntime.class, DeviceKind.WINDOWS_RUNTIME)
                    .registerSubtype(WindowsCE.class, DeviceKind.WINDOWS_CE)
                    .registerSubtype(NotYetSupportedDevice.class, DeviceKind.LINUX);
//            .registerSubtype(SamsungKnoxDevice.class, DeviceKind.ANDROID_KNOX)


            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(typeFactory)
                    .setLenient()
                    .create();

            //TODO: this will not work updating a single device
            Type deviceCollectionType = new TypeToken<ArrayList<? extends BasicDevice>>() {
            }.getType();
            ArrayList<? extends BasicDevice> devices = gson.fromJson(jsonResponseString, deviceCollectionType);

            applicationContext.sendBroadcast(intent);
            Uri uri = McContract.buildUriWithServerName(McContract.Device.CONTENT_URI, ServerUtility.getActiveServer().getServerName());
            mContext.getContentResolver().delete(uri, null, null);

            Logger.log("TEST", "Extrainfo: " + devices.get(0).getExtraInfo() + "\n", Log.VERBOSE);
            Logger.log("TEST", "ExtraAttributes: " + devices.get(0).getExtraAttributesList().toString(), Log.VERBOSE);

            saveDevicesToDB(devices);

            applicationContext.sendBroadcast(intent);

            return Response.success(null,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | ServerNotFound e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    private void saveDevicesToDB(ArrayList<? extends BasicDevice> devices) {

        Cursor c = mContext.getContentResolver().query(McContract.ServerInfo.CONTENT_URI,
                new String[]{McContract.ServerInfo._ID},
                McContract.ServerInfo.NAME + "=?", new String[]{server.getServerName()}, null);

        if (c == null || !c.moveToFirst()) {
            return;
        }

        Integer serverId = c.getInt(0);
        c.close();

        //Parse devices to extract common properties and put other as extra string
        if (devices.size() == 1) {
            ContentValues device = devices.get(0).toContentValues(serverId);
            if (insertInfoMethod == ERASE_OLD_DEVICE_INFO) {
                mContext.getContentResolver().insert(McContract.Device.CONTENT_URI, device);
            } else if (insertInfoMethod == UPDATE_EXISTING_DEVICE_INFO) {
                String devId = devices.get(0).getDeviceId();
                mContext.getContentResolver()
                        .update(McContract.Device.buildUriWithDeviceID(devId), device, null, null);
            }
        } else if (devices.size() > 1 && insertInfoMethod == ERASE_OLD_DEVICE_INFO) {
            ArrayList<ContentValues> devicesValues = new ArrayList<>(devices.size());
            for (Object device : devices) {
                if (device instanceof BasicDevice) {
                    devicesValues.add(((BasicDevice) device).toContentValues(serverId));
                }
            }
            ContentValues[] array = new ContentValues[]{};
            mContext.getContentResolver().bulkInsert(McContract.Device.CONTENT_URI, devicesValues.toArray(array));
        }
    }
}
