package com.mdmobile.cyclops.interfaces;

import android.os.Bundle;

import com.android.volley.VolleyError;
import com.mdmobile.cyclops.dataModels.api.Token;

public interface NetworkCallBack {

    void tokenReceived(Bundle userInfo, Token JsonToken);

    void errorReceivingToken(VolleyError response);

}


