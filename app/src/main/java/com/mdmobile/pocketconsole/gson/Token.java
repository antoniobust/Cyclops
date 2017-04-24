package com.mdmobile.pocketconsole.gson;


public class Token {
    private String accessToken,tokenType;
    private Integer expiresIn;


    public Token(String accessToken, String tokenType, Integer expiresIn){
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }
}
