package com.mdmobile.pocketconsole.interfaces;

import android.os.Bundle;

import com.android.volley.VolleyError;
import com.mdmobile.pocketconsole.dataModels.api.Token;
import com.mdmobile.pocketconsole.dataModels.api.User;

public interface NetworkCallBack {

    void tokenReceived(Bundle userInfo, Token JsonToken);

    void errorReceivingToken(VolleyError response);

}


