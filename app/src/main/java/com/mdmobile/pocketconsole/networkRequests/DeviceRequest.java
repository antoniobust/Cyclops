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
import com.mdmobile.pocketconsole.dataTypes.ComplexDataType;
import com.mdmobile.pocketconsole.gson.RuntimeTypeAdapterFactory;
import com.mdmobile.pocketconsole.gson.devices.AndroidForWorkDevice;
import com.mdmobile.pocketconsole.gson.devices.AndroidGenericDevice;
import com.mdmobile.pocketconsole.gson.devices.AndroidPlusDevice;
import com.mdmobile.pocketconsole.gson.devices.BasicDevice;
import com.mdmobile.pocketconsole.gson.devices.IosDevice;
import com.mdmobile.pocketconsole.gson.devices.SamsungElmDevice;
import com.mdmobile.pocketconsole.gson.devices.SamsungKnoxDevice;
import com.mdmobile.pocketconsole.gson.devices.WindowsCE;
import com.mdmobile.pocketconsole.gson.devices.WindowsDesktop;
import com.mdmobile.pocketconsole.gson.devices.WindowsDesktopLegacy;
import com.mdmobile.pocketconsole.gson.devices.WindowsPhone;
import com.mdmobile.pocketconsole.gson.devices.WindowsRuntime;
import com.mdmobile.pocketconsole.provider.McContract;
import com.mdmobile.pocketconsole.utils.DbData;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.mdmobile.pocketconsole.utils.DbData.formatDeviceData;

/**
 * Request devices
 * Pass API request in the constructor
 */

public class DeviceRequest<T> extends BasicRequest<T> {

    public final static int ERASE_OLD_DEVICE_INFO = 1;
    public final static int UPDATE_EXISTING_DEVICE_INFO = 2;
    private Response.Listener<T> listener;
    private Context mContext;
    private int inserInfoMethod;

    public DeviceRequest(Context context, int method, String url, Response.Listener<T> listener,
                         Response.ErrorListener errorListener, int insertDataMEthod) {
        super(method, url, errorListener, context);

        this.mContext = context;
        this.listener = listener;
        inserInfoMethod = insertDataMEthod;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String jsonResponseString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));


            Type deviceCollectionType = new TypeToken<ArrayList<BasicDevice>>() {
            }.getType();

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
            ArrayList<BasicDevice> devices = gson.fromJson(jsonResponseString, deviceCollectionType);

            //If we are refreshing all device data delete the old info first
            if (inserInfoMethod == ERASE_OLD_DEVICE_INFO) {
                mContext.getContentResolver().delete(McContract.Device.CONTENT_URI, null, null);

                //Parse devices to extract common properties and put other as extra string
                if (devices.size() == 1) {
                    ContentValues device = formatDeviceData(devices.get(0));
                    mContext.getContentResolver().insert(McContract.Device.CONTENT_URI, device);
                } else if (devices.size() > 1) {
                    ContentValues[] devicesValues = DbData.bulkFormatDeviceData(devices);
                    mContext.getContentResolver().bulkInsert(McContract.Device.CONTENT_URI, devicesValues);
                }
            } else if (inserInfoMethod == UPDATE_EXISTING_DEVICE_INFO) {
//                TODO:implement old data update method
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
