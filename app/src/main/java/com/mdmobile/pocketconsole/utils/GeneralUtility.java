package com.mdmobile.pocketconsole.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class GeneralUtility {


    public static String validateUrl(String url) {
        StringBuilder serverUrl = new StringBuilder(url);

        //Get the first 7 chars  of the url
        String subStr = url.substring(0, 8);

        //Check if begins with "https://"
        if (!subStr.equals("https://")) {
            serverUrl = new StringBuilder("https://").append(url);
        }

        //Check if url ends with "/", if yes remove it
        subStr = url.substring(url.length() - 1, url.length());
        if (subStr.equals("/")) {
            serverUrl = serverUrl.delete(serverUrl.length() - 1, serverUrl.length());
        }

        return serverUrl.toString();
    }

    public static HashMap<String, String> formatDeviceExtraInfo(String extraInfo){

        String[] extraValues = extraInfo.split(";|=");
        HashMap<String,String> map = new HashMap<>();

        for(int i = 0; i < extraValues.length/2; i=i+2){
            if(extraValues[i+1].equals("")){
                map.put(extraValues[i], "N/A");
            }else {
                map.put(extraValues[i], extraValues[i + 1]);
            }
        }
        return map;
    }

//    public static String formatDate(String date){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        simpleDateFormat.setTimeZone(TimeZone.getDefault());
//        simpleDateFormat.format(new Date(date));
//        return "a";

//    }

}
