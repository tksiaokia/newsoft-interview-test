package com.sean.newsoft.services;

import android.util.Log;

import com.sean.newsoft.model.UserToken;
import com.sean.newsoft.model.response.ApiResponse;
import com.sean.newsoft.model.response.GeneralResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseService {
    //General function to enqueue call which accept generic type
    protected <T extends ApiResponse> void enqueueCall(Call<T> call, final ResponseSuccessInterface<T> successCallback, final GeneralErrorInterface callback){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                T apiResponse = response.body();
                if(apiResponse != null){
                    if(apiResponse.isSuccess()){
                        successCallback.onSuccess(apiResponse);
                    }else{
                        callback.onError(apiResponse.getStatus());
                    }
                }else{
                    handleResponseError(response,callback);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                handleGeneralError(t,callback);
            }
        });
    }


    private void handleResponseError(Response response, GeneralErrorInterface callback) {
        if (response != null && response.message() != null) {
            callback.onError(new GeneralResponse(response.message()));
        } else {
            callback.onError(new GeneralResponse("Something Wrong"));
        }
    }

    private void handleGeneralError(Throwable t, GeneralErrorInterface callback) {
        callback.onError(new GeneralResponse(t.getMessage()));
    }
}
