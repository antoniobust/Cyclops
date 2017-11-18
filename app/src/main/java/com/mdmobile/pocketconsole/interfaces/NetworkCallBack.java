package com.mdmobile.pocketconsole.interfaces;

import com.android.volley.VolleyError;
import com.mdmobile.pocketconsole.dataModels.api.Token;

public interface NetworkCallBack {

    void tokenReceived(Token JsonToken);

    void errorReceivingToken(VolleyError response);

}


