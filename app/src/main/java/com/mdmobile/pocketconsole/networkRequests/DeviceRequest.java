package com.mdmobile.pocketconsole.networkRequests;

import android.content.ContentValues;
import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mdmobile.pocketconsole.dataModels.api.RuntimeTypeAdapterFactory;
import com.mdmobile.pocketconsole.dataModels.api.devices.AndroidForWorkDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.AndroidGenericDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.AndroidPlusDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.BasicDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.IosDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.SamsungElmDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.SamsungKnoxDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.WindowsCE;
import com.mdmobile.pocketconsole.dataModels.api.devices.WindowsDesktop;
import com.mdmobile.pocketconsole.dataModels.api.devices.WindowsDesktopLegacy;
import com.mdmobile.pocketconsole.dataModels.api.devices.WindowsPhone;
import com.mdmobile.pocketconsole.dataModels.api.devices.WindowsRuntime;
import com.mdmobile.pocketconsole.dataTypes.ComplexDataType;
import com.mdmobile.pocketconsole.provider.McContract;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.mdmobile.pocketconsole.utils.DbData.prepareDeviceValues;

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
                    .registerSubtype(IosDevice.class, ComplexDataType.DeviceKind.iOS.toString())
                    .registerSubtype(AndroidGenericDevice.class, ComplexDataType.DeviceKind.AndroidGeneric.toString())
                    .registerSubtype(AndroidForWorkDevice.class, ComplexDataType.DeviceKind.AndroidForWork.toString())
                    .registerSubtype(AndroidPlusDevice.class, ComplexDataType.DeviceKind.AndroidPlus.toString())
                    .registerSubtype(SamsungKnoxDevice.class, ComplexDataType.DeviceKind.AndroidKnox.toString())
                    .registerSubtype(SamsungElmDevice.class, ComplexDataType.DeviceKind.AndroidElm.toString())
                    .registerSubtype(WindowsDesktop.class, ComplexDataType.DeviceKind.WindowsDesktop.toString())
                    .registerSubtype(WindowsDesktopLegacy.class, ComplexDataType.DeviceKind.WindowsDesktopLegacy.toString())
                    .registerSubtype(WindowsPhone.class, ComplexDataType.DeviceKind.WindowsPhone.toString())
                    .registerSubtype(WindowsRuntime.class, ComplexDataType.DeviceKind.WindowsRuntime.toString())
                    .registerSubtype(WindowsCE.class, ComplexDataType.DeviceKind.WindowsCE.toString());


            Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();


            //If we are refreshing all device data delete the old info first
            if (insertInfoMethod == ERASE_OLD_DEVICE_INFO) {
                Type deviceCollectionType = new TypeToken<ArrayList<BasicDevice>>() {
                }.getType();
                ArrayList<BasicDevice> devices = gson.fromJson(jsonResponseString, deviceCollectionType);
                mContext.getContentResolver().delete(McContract.Device.CONTENT_URI, null, null);

                //Parse devices to extract common properties and put other as extra string
                if (devices.size() == 1) {
                    ContentValues device = prepareDeviceValues(devices.get(0));
                    mContext.getContentResolver().insert(McContract.Device.CONTENT_URI, device);
                } else if (devices.size() > 1) {
                    ContentValues[] devicesValues = prepareDeviceValues(devices);
                    mContext.getContentResolver().bulkInsert(McContract.Device.CONTENT_URI, devicesValues);
                }
            } else if (insertInfoMethod == UPDATE_EXISTING_DEVICE_INFO) {
                Type deviceCollectionType = new TypeToken<BasicDevice>() {
                }.getType();
                BasicDevice deviceInfo = gson.fromJson(jsonResponseString, deviceCollectionType);
                ContentValues device = prepareDeviceValues(deviceInfo);
                String devId = device.getAsString(McContract.Device.COLUMN_DEVICE_ID);
                mContext.getContentResolver()
                        .update(McContract.Device.buildUriWithDeviceID(devId), device, null, null);
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
