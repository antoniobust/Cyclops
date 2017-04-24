package com.mdmobile.pocketconsole.gson;


import android.support.annotation.Nullable;

public class ErrorResponse {

    private int errorCode;
    private String errorMessage, data, helpLink;


    public ErrorResponse(int errorCode, String errorMessage, @Nullable String data, @Nullable String helpLink) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
        this.helpLink = helpLink;

    }

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getData() {
        return data;
    }

    public String getHelpLink() {
        return helpLink;
    }
}
