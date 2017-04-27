package com.mdmobile.pocketconsole.gson;


public class Token {

    private String access_token, token_type;


    public Token(String access_token, String token_type) {
        this.access_token = access_token;
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

}
