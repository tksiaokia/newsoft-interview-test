package com.sean.newsoft.model.response;

public class GeneralResponse {
    private int code;
    private String message;

    protected GeneralResponse() {
    }

    public GeneralResponse(String message) {
        this.message = message;
        this.code = -1;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
