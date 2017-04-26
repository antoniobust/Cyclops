package com.mdmobile.pocketconsole;

public interface NetworkCallBack {

    void tokenReceived(String response);
    void errorReceivingToken(String response);

}


