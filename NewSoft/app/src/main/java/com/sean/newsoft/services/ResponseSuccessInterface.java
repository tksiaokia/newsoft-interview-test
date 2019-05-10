package com.sean.newsoft.services;

import com.sean.newsoft.model.response.ApiResponse;
import com.sean.newsoft.model.response.GeneralResponse;

public interface ResponseSuccessInterface <T extends ApiResponse> {
    void onSuccess(T response);
}
