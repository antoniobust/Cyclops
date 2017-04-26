package com.mdmobile.pocketconsole;

import com.mdmobile.pocketconsole.gson.Token;

public interface NetworkCallBack {

    void tokenReceived(Token JsonToken);
    void errorReceivingToken(String response);

}


