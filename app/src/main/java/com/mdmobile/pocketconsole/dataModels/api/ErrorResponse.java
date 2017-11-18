package com.mdmobile.pocketconsole.dataModels.api;


import android.support.annotation.Nullable;

public class ErrorResponse {

    private int errorCode;
    private String Message, data, helpLink;


    public ErrorResponse(int errorCode, String Message, @Nullable String data, @Nullable String helpLink) {
        this.errorCode = errorCode;
        this.Message = Message;
        this.data = data;
        this.helpLink = helpLink;

    }

    public ErrorResponse(String Message) {
        this.Message = Message;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return Message;
    }

    public String getData() {
        return data;
    }

    public String getHelpLink() {
        return helpLink;
    }
}
