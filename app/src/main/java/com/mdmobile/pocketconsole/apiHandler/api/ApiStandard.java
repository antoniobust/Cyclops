package com.mdmobile.pocketconsole.apiHandler.api;


import android.net.Uri;
import android.util.Pair;

import java.util.HashSet;

/**
 * ApiStandard is an abstract class which provides the standard methods to append standard parameters
 * (skip, take,filter,order) to the https request.
 * Since not all the requests support these parameters ApiStandard class is only extended in api classes
 * which support them
 */

public abstract class ApiStandard {

    Uri.Builder currentApi;

    ApiStandard(String request) {
        currentApi = Uri.parse(request).buildUpon();
    }

    public ApiStandard skip(int value) {
        //Append skip parameter and value to the url
        String skipParameterKey = "skip";
        currentApi.appendQueryParameter(skipParameterKey, String.valueOf(value));
        return this;
    }

    public ApiStandard take(int size) {
        //Append take parameter and value to the url
        String takeParameterKey = "take";
        currentApi.appendQueryParameter(takeParameterKey, String.valueOf(size));
        return this;
    }

    public ApiStandard filter(HashSet<Pair<String, String>> filters) {
        String s = "";
        //Navigate through the list of parameters and create the filter string
        for (Pair<String, String> pair : filters) {
            s = s.concat(pair.first).concat(":").concat(pair.second).concat(",");
        }
        //Remove the last comma from the string
        s = s.substring(0, s.length() - 1);

        //Append the filter to the url
        String filterParameterKey = "filter";
        currentApi.appendQueryParameter(filterParameterKey, s);
        return this;
    }

    public ApiStandard order(HashSet<Pair<String, String>> order) {
        String s = "";
        //Navigate through the list of parameters and create the order string
        for (Pair<String, String> pair : order) {
            s = s.concat(pair.first).concat(pair.second).concat(",");
        }
        //Remove the last comma from the string
        s = s.substring(0, s.length() - 1);

        //Append the filter to the url
        String orderParameterKey = "order";
        currentApi.appendQueryParameter(orderParameterKey, s);
        return this;
    }

    public String build() {
        return Uri.decode(currentApi.build().toString());
    }


}
