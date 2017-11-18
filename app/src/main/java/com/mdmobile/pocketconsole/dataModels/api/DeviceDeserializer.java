package com.mdmobile.pocketconsole.dataModels.api;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mdmobile.pocketconsole.dataModels.api.devices.AndroidForWorkDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.AndroidGenericDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.AndroidPlusDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.IosDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.SamsungElmDevice;
import com.mdmobile.pocketconsole.dataModels.api.devices.SamsungKnoxDevice;

import java.lang.reflect.Type;

import static com.mdmobile.pocketconsole.dataTypes.ComplexDataType.DeviceKind;

/**
 * Custom Gson deserializer for devices
 */

public class DeviceDeserializer implements JsonDeserializer {


    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.has("Kind")) {

            String kind = jsonObject.get("Kind").getAsString();

            switch (DeviceKind.valueOf(kind)) {
                case iOS:
                    return new Gson().fromJson(json.getAsJsonObject(), IosDevice.class);
                case AndroidPlus:
                    return new Gson().fromJson(json.getAsJsonObject(), AndroidPlusDevice.class);
                case AndroidForWork:
                    return new Gson().fromJson(json.getAsJsonArray(), AndroidForWorkDevice.class);
                case AndroidGeneric:
                    return new Gson().fromJson(json.getAsJsonArray(), AndroidGenericDevice.class);
                case AndroidElm:
                    return new Gson().fromJson(json.getAsJsonArray(), SamsungElmDevice.class);
                case AndroidKnox:
                    return new Gson().fromJson(json.getAsJsonArray(), SamsungKnoxDevice.class);
            }

        }

        return null;
    }
}
