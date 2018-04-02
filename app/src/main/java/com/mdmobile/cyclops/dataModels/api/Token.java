package com.mdmobile.cyclops.dataModels.api;


public class Token {

    private String access_token, token_type, refresh_token;
    private int expires_in;


    public Token(String access_token, String token_type, int expires_in, String refresh_token) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public int getTokenExpiration() {
        return expires_in;
    }

    public String getRefreshToken() {
        return refresh_token;
    }
}
