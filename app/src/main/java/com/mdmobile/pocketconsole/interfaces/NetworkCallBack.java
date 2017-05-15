package com.mdmobile.pocketconsole.interfaces;

import com.android.volley.VolleyError;
import com.mdmobile.pocketconsole.gson.Token;

public interface NetworkCallBack {

    void tokenReceived(Token JsonToken);
    void errorReceivingToken(VolleyError response);

}


