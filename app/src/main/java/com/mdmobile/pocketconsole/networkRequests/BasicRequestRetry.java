package com.mdmobile.pocketconsole.networkRequests;

import com.android.volley.DefaultRetryPolicy;

/**
 * Retry policy for API requests
 */

public class BasicRequestRetry extends DefaultRetryPolicy {

    BasicRequest request;

    public BasicRequestRetry( BasicRequest request ) {
        super();
        this.request = request;
    }
}
