package com.mdmobile.pocketconsole.gson;


/**
 * Class used for serialization of action request payload
 */

public class Action {
    private String Action, Message, PhoneNumber;

    public Action(String action, String message, String phoneNumber) {
        Action = action;
        if (message != null) {
            Message = message;
        }
        if (phoneNumber != null) {
            PhoneNumber = phoneNumber;
        }
    }
}
