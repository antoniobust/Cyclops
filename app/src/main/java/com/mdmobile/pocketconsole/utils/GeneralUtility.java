package com.mdmobile.pocketconsole.utils;


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

}
