package com.mdmobile.pocketconsole.dataModels.api;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mdmobile.pocketconsole.dataModels.api.devices.AndroidForWork;
import com.mdmobile.pocketconsole.dataModels.api.devices.AndroidGeneric;
import com.mdmobile.pocketconsole.dataModels.api.devices.AndroidPlus;
import com.mdmobile.pocketconsole.dataModels.api.devices.IosDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.SamsungElm;
import com.mdmobile.pocketconsole.dataTypes.DeviceKind;

import java.lang.reflect.Type;

/**
 * Custom Gson deserializer for devices
 */

public class DeviceDeserializer implements JsonDeserializer {


    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.has("Kind")) {

            String kind = jsonObject.get("Kind").getAsString();

            switch (kind) {
                case DeviceKind.IOS:
                    return new Gson().fromJson(json.getAsJsonObject(), IosDevice.class);
                case DeviceKind.ANDROID_PLUS:
                    return new Gson().fromJson(json.getAsJsonObject(), AndroidPlus.class);
                case DeviceKind.ANDROID_FOR_WORK:
                    return new Gson().fromJson(json.getAsJsonArray(), AndroidForWork.class);
                case DeviceKind.ANDROID_GENERIC:
                    return new Gson().fromJson(json.getAsJsonArray(), AndroidGeneric.class);
                case DeviceKind.ANDROID_ELM:
                    return new Gson().fromJson(json.getAsJsonArray(), SamsungElm.class);
//                case DeviceKind.ANDROID_KNOX:
//                    return new Gson().fromJson(json.getAsJsonArray(), SamsungKnoxDevice.class);
            }

        }

        return null;
    }
}
