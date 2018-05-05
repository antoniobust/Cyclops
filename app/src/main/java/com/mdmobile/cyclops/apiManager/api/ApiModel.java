package com.mdmobile.cyclops.apiManager.api;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.mdmobile.cyclops.dataTypes.ComplexDataType;
import com.mdmobile.cyclops.dataTypes.ParameterKeys;

import java.util.Arrays;

/**
 * ApiModel contains the difference kind of API available represented as classes
 */

public class ApiModel {

    private static final String MobiControl = "MobiControl";
    private static final String SotiAssist = "sotiassist";

    public static class DevicesApi {

        public static SelectDevice Builder(@NonNull String authority, @NonNull String deviceID) {
            return new SelectDevice(authority.concat("/" + MobiControl), deviceID);
        }

        public static ListDevices Builder(@NonNull String authority, int mcVersion) {
            return new ListDevices(authority.concat("/" + MobiControl), mcVersion);
        }

        public static RemoteControl BuildRC(@NonNull String authority, @NonNull String deviceID) {
            return new RemoteControl(authority.concat("/" + SotiAssist), deviceID);
        }

        public static class ListDevices extends ApiStandard {

            public ListDevices(@NonNull String authority, int mcMajorVersion) {
                super(authority);
                if (mcMajorVersion >= 140) {
                    super.currentApi = Uri.parse(authority).buildUpon().appendEncodedPath(ApiTypes.deviceSearch);
                } else {
                    super.currentApi = Uri.parse(authority).buildUpon().appendPath(ApiTypes.devicesApi);
                }

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

            private SelectDevice(String authority, String deviceID) {
                currentApi = Uri.parse(authority).buildUpon().appendPath(ApiTypes.devicesApi).appendPath(deviceID);
            }

            public static SelectDevice Builder(String authority, String devId) {
                return new SelectDevice(authority.concat("/" + MobiControl), devId);
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
                String path = "installedApplications";
                currentApi.appendPath(path);
                return SelectDevice.this;
            }

            public SelectDevice getProfiles() {
                String path = "profiles";
                currentApi.appendPath(path);
                return SelectDevice.this;
            }

            public SelectDevice installDeviceProfile(@NonNull String profileID) {
                String path = "actions";
                getProfiles();
                currentApi.appendPath(profileID).appendPath(path);
                return SelectDevice.this;
            }

            public SelectDevice getSupportContactInfo() {
                String path = "support";
                currentApi.appendPath(path);
                return SelectDevice.this;
            }

            public SelectDevice actionRequest() {
                String path = "actions";
                currentApi.appendPath(path);
                return SelectDevice.this;
            }

            public SelectDevice relocate() {
                String path = "parentPath";
                currentApi.appendPath(path);
                return SelectDevice.this;
            }

            public SelectDevice setPassCode() {
                String path = "passCode";
                currentApi.appendPath(path);
                return SelectDevice.this;
            }

            public SelectDevice setCustomAttirbute(@NonNull String customAttributeId) {
                String path = "customAttributes";
                currentApi.appendPath(path).appendQueryParameter(ParameterKeys.customAttributeId.toString(), customAttributeId);
                return SelectDevice.this;
            }

            public String build() {
                return Uri.decode(currentApi.build().toString());
            }
        }

        public static class RemoteControl {
            private static Uri.Builder currentAPI;

            RemoteControl(String apiAuthority, String deviceId) {
                currentAPI = Uri.parse(apiAuthority).buildUpon().appendPath(ApiTypes.remoteControl).appendPath(deviceId);
            }

            @Override
            public String toString() {
                return Uri.decode(currentAPI.build().toString());
            }
        }
    }

    public static class DirectoriesApi {

        private static Uri.Builder currentApi;

        private DirectoriesApi(Uri.Builder currentApi) {
            DirectoriesApi.currentApi = currentApi;
        }

        public static DirectoriesApi Builder(@NonNull String authority) {
            Uri.parse(authority).buildUpon().appendPath(MobiControl).appendPath(ApiTypes.directoriesResourceApi);
            return new DirectoriesApi(currentApi);
        }

        public DirectoriesApi getLdapUserOrGroups(@NonNull String directoryConnectionName, @NonNull String search,
                                                  @Nullable String type, @Nullable String[] memberOf) {

            String searchParameterKey = "searchString";
            String path = "directoryConnectionName";
            String typeParameterKey = "type";

            currentApi.appendPath(path).appendQueryParameter(searchParameterKey, search);

            if (type == null) {
                currentApi.appendQueryParameter(typeParameterKey, ComplexDataType.DirectoryResourceType.Both.toString
                        ());
            } else {
                currentApi.appendQueryParameter(searchParameterKey, search);
            }

            if (memberOf != null) {
                String memberOfQueryKey = "memberOf";
                currentApi.appendQueryParameter(memberOfQueryKey, Uri.encode(Arrays.toString(memberOf)));
            }

            return DirectoriesApi.this;
        }

        public String build() {
            return Uri.decode(currentApi.build().toString());
        }


    }

    public static class UserSecurityApi {
        private static Uri.Builder currentApi;


        public static UserSecurityApi Builder(@NonNull String authority) {
            currentApi = Uri.parse(authority).buildUpon().appendPath(MobiControl).appendPath(ApiTypes.usersResource);
            return new UserSecurityApi();
        }

        public UserSecurityApi getAllUsers(@NonNull Boolean includeHiddenUsers, @Nullable String searchString, @Nullable String[] memberOf) {
            String includeHiddenUsersParameterKey = "includeHiddenUsers";
            if (includeHiddenUsers) {
                currentApi.appendQueryParameter(includeHiddenUsersParameterKey, "true");
            } else {
                currentApi.appendQueryParameter(includeHiddenUsersParameterKey, "false");
            }

            if (searchString != null) {
                String searchStringParamKey = "searchString";
                currentApi.appendQueryParameter(searchStringParamKey, searchString);
            }

            if (memberOf != null) {
                String memberOfParamKey = "memberOf";
                currentApi.appendQueryParameter(memberOfParamKey, Uri.encode(Arrays.toString(memberOf)));
            }

            return UserSecurityApi.this;
        }

        public UserSecurityApi getSingleUser(@NonNull String userName) {
            currentApi.appendPath(userName);
            return UserSecurityApi.this;
        }

        public UserSecurityApi getUserGroups(@NonNull String userName, @NonNull Boolean showGroupInheritance) {
            String groupsPath = "groups";
            String showGroupInheritanceParamKey = "showGroupInheritance";
            getSingleUser(userName);
            if (showGroupInheritance) {
                currentApi.appendPath(groupsPath).appendQueryParameter(showGroupInheritanceParamKey, "true");
            } else {
                currentApi.appendPath(groupsPath).appendQueryParameter(showGroupInheritanceParamKey, "false");
            }
            return UserSecurityApi.this;
        }

        public String build() {
            return Uri.decode(currentApi.build().toString());
        }
    }

    public static class ServerApi {

        private static Uri.Builder currentApi;

        public static ServerApi Builder(@NonNull String authority) {
            currentApi = Uri.parse(authority).buildUpon().appendPath(MobiControl).appendPath(ApiTypes.serverResource);
            return new ServerApi();
        }

        public ServerApi getServerInfo() {
            return ServerApi.this;
        }

        public String build() {
            return Uri.decode(currentApi.build().toString());
        }

    }

}
