package com.mdmobile.mobiconsole.Api;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.mdmobile.mobiconsole.DataTypes.ComplexDataType;
import com.mdmobile.mobiconsole.DataTypes.ParameterKeys;

/**
 * ApiModel contains the difference kind of API available represented as classes
 */

public class ApiModels {

    public static final String MobiControl = "MobiControl";

    public static class DevicesApi {

        public static SelectDevice Builder(@NonNull String authority, @NonNull String deviceID) {
            return new SelectDevice(authority.concat("/" + MobiControl), deviceID);
        }

        public static ListDevices Builder(@NonNull String authority) {
            return new ListDevices(authority.concat("/" + MobiControl));
        }

        public static class ListDevices extends ApiStandard {

            public ListDevices(@NonNull String authority) {
                super(authority);
                super.currentApi = Uri.parse(authority).buildUpon().appendPath(ApiTypes.devicesApi);

            }

            public ApiStandard addUserFilter(Pair<String, String> filter) {
                //Build the filter according to the API DOC
                String parameter = filter.first.concat(":").concat(filter.second);
                //Append the filter to the request in super
                super.currentApi.appendQueryParameter(ParameterKeys.userFilter.toString(), parameter);
                return ListDevices.this;
            }

            //TODO: this api has path parameter to limit the result to a specific folder needs to be implemented
            public ApiStandard getCollectedData(@NonNull String startDate, @NonNull String endDate,
                                                @Nullable ComplexDataType.BuiltInDataType dataType, @Nullable String customDataType) {
                String path = "collectedData";
                super.currentApi.appendEncodedPath(path).appendQueryParameter(ParameterKeys.startDate.toString(), Uri.encode(startDate))
                        .appendQueryParameter(ParameterKeys.endDate.toString(), Uri.encode(endDate));

                if (dataType != null && customDataType == null) {
                    super.currentApi.appendQueryParameter(ParameterKeys.builtInDataType.toString(), dataType.toString());
                } else if (dataType == null && customDataType != null) {
                    super.currentApi.appendQueryParameter(ParameterKeys.customDataType.toString(), customDataType);
                }
                return ListDevices.this;
            }
        }

        public static class SelectDevice {
            private static Uri.Builder currentApi;

            public SelectDevice(String authority, String deviceID) {
                currentApi = Uri.parse(authority).buildUpon().appendPath(ApiTypes.devicesApi).appendPath(deviceID);
            }

            public SelectDevice getCollectedData(@NonNull String startDate, @NonNull String endDate,
                                                 @Nullable ComplexDataType.BuiltInDataType dataType, @Nullable String customDataType) {

                String path = "collectedData";
                currentApi.appendEncodedPath(path).appendQueryParameter(ParameterKeys.startDate.toString(), Uri.encode(startDate))
                        .appendQueryParameter(ParameterKeys.endDate.toString(), Uri.encode(endDate));


                if (dataType != null && customDataType == null) {
                    currentApi.appendQueryParameter(ParameterKeys.builtInDataType.toString(), dataType.toString());
                } else if (dataType == null && customDataType != null) {
                    currentApi.appendQueryParameter(ParameterKeys.customDataType.toString(), customDataType);
                }

                return SelectDevice.this;
            }

            public SelectDevice getInstalledApplications() {
                String installedApp = "installedApplications";
                currentApi.appendPath(installedApp);
                return SelectDevice.this;
            }

            public SelectDevice getInstalledProfiles() {
                String installedApp = "profiles";
                currentApi.appendPath(installedApp);
                return SelectDevice.this;
            }

            public SelectDevice getSupportContactInfo() {
                String installedApp = "support";
                currentApi.appendPath(installedApp);
                return SelectDevice.this;
            }

            public String build() {
                return Uri.decode(currentApi.build().toString());
            }
        }
    }

}
