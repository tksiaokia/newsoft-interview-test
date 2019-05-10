package com.sean.newsoft.model;

import com.sean.newsoft.model.response.ApiResponse;

public class UserToken extends ApiResponse {
    private String id;
    private String token;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
