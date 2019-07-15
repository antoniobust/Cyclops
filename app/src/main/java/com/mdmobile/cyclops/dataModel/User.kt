package com.mdmobile.cyclops.dataModel

data class User(val userName: String = "N/A", val password: String = "N/A") {
        companion object {
            fun userNotDefault(user: User): Boolean {
                return !(user.userName.isBlank() || user.userName == "N/A"
                        || user.password.isBlank() || user.password == "N/A")
            }
        }
    }