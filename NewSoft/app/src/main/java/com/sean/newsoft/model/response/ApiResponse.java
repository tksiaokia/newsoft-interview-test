package com.sean.newsoft.model.response;

public class ApiResponse {
    private GeneralResponse status;

    public GeneralResponse getStatus() {
        return status;
    }

    public boolean isSuccess(){
        return status.getCode() == 200;
    }
}
