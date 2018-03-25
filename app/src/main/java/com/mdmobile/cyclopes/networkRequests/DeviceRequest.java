package com.mdmobile.cyclopes.networkRequests;

import android.content.ContentValues;
import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.cyclopes.dataModels.api.RuntimeTypeAdapterFactory;
import com.mdmobile.cyclopes.dataModels.api.devices.AndroidForWork;
import com.mdmobile.cyclopes.dataModels.api.devices.AndroidGeneric;
import com.mdmobile.cyclopes.dataModels.api.devices.AndroidPlus;
import com.mdmobile.cyclopes.dataModels.api.devices.BasicDevice;
import com.mdmobile.cyclopes.dataModels.api.devices.IosDevice;
import com.mdmobile.cyclopes.dataModels.api.devices.SamsungElm;
import com.mdmobile.cyclopes.dataModels.api.devices.WindowsCE;
import com.mdmobile.cyclopes.dataModels.api.devices.WindowsDesktop;
import com.mdmobile.cyclopes.dataModels.api.devices.WindowsDesktopLegacy;
import com.mdmobile.cyclopes.dataModels.api.devices.WindowsPhone;
import com.mdmobile.cyclopes.dataModels.api.devices.WindowsRuntime;
import com.mdmobile.cyclopes.dataTypes.DeviceKind;
import com.mdmobile.cyclopes.provider.McContract;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

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

    public DeviceRequest(Context context, int method, String url, Response.Listener<T> listener,
                         Response.ErrorListener errorListener, int insertDataMethod) {
        super(method, url, errorListener);

        this.mContext = context.getApplicationContext();
        this.listener = listener;
        insertInfoMethod = insertDataMethod;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String jsonResponseString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            final RuntimeTypeAdapterFactory<BasicDevice> typeFactory = RuntimeTypeAdapterFactory
                    .of(BasicDevice.class, "Kind")
                    .registerSubtype(IosDevice.class, DeviceKind.IOS)
                    .registerSubtype(AndroidGeneric.class, DeviceKind.ANDROID_GENERIC)
                    .registerSubtype(AndroidForWork.class, DeviceKind.ANDROID_FOR_WORK)
                    .registerSubtype(AndroidPlus.class, DeviceKind.ANDROID_PLUS)
                    .registerSubtype(SamsungElm.class, DeviceKind.ANDROID_ELM)
                    .registerSubtype(WindowsDesktop.class, DeviceKind.WINDOWS_DESKTOP)
                    .registerSubtype(WindowsDesktopLegacy.class, DeviceKind.WINDOWS_DESKTOP_LEGACY)
                    .registerSubtype(WindowsPhone.class, DeviceKind.WINDOWS_PHONE)
                    .registerSubtype(WindowsRuntime.class, DeviceKind.WINDOWS_RUNTIME)
                    .registerSubtype(WindowsCE.class, DeviceKind.WINDOWS_CE);
//            .registerSubtype(SamsungKnoxDevice.class, DeviceKind.ANDROID_KNOX)


            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(typeFactory)
                    .setLenient()
                    .create();

            //TODO: this will not work updating a single device
            Type deviceCollectionType = new TypeToken<ArrayList<? extends BasicDevice>>() {
            }.getType();
            ArrayList<? extends BasicDevice> devices = gson.fromJson(jsonResponseString, deviceCollectionType);
            mContext.getContentResolver().delete(McContract.Device.CONTENT_URI, null, null);

            //Parse devices to extract common properties and put other as extra string
            if (devices.size() == 1) {
                ContentValues device = devices.get(0).toContentValues();
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
                        devicesValues.add(((BasicDevice) device).toContentValues());
                    }
                }
                ContentValues[] array = new ContentValues[]{};
                mContext.getContentResolver().bulkInsert(McContract.Device.CONTENT_URI, devicesValues.toArray(array));
            }

            return Response.success(null,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
