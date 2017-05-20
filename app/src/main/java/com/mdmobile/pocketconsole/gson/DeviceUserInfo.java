package com.mdmobile.pocketconsole.gson;

/**
 * DeviceUserInfo class -> present in all devices Kind beside Zebra printers
 */

public class DeviceUserInfo {

        private String UserName, FirstName, MiddleName, LastName, DomainName, UPN, PhoneNumber,
                Email, CustomProperty1, CustomProperty2, CustomProperty3, Identifier;

        public String getUserName() {
            return UserName;
        }

        public String getFirstName() {
            return FirstName;
        }

        public String getMiddleName() {
            return MiddleName;
        }

        public String getLastName() {
            return LastName;
        }

        public String getDomainName() {
            return DomainName;
        }

        public String getUPN() {
            return UPN;
        }

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public String getEmail() {
            return Email;
        }

        public String getCustomProperty1() {
            return CustomProperty1;
        }

        public String getCustomProperty2() {
            return CustomProperty2;
        }

        public String getCustomProperty3() {
            return CustomProperty3;
        }

        public String getIdentifier() {
            return Identifier;
        }
}
