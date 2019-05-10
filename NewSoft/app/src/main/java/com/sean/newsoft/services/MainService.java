package com.sean.newsoft.services;

import com.sean.newsoft.Constants;
import com.sean.newsoft.model.Listing;
import com.sean.newsoft.model.UserToken;
import com.sean.newsoft.model.response.ApiResponse;
import com.sean.newsoft.model.response.ListingResponse;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainService extends BaseService{
    public interface MainServiceEndPoint {
        @FormUrlEncoded
        @POST("login")
        Call<UserToken> login(@Field("email")  String username,@Field("password") String password);

        @GET("listing")
        Call<ListingResponse> listing(@Query("id")  String id, @Query("token") String token);

        @FormUrlEncoded
        @POST("listing/update")
        Call<ApiResponse> updateListing(@Field("id")  String id,
                                        @Field("token") String token,
                                        @Field("listing_id") String listingId,
                                        @Field("listing_name") String listingName,
                                        @Field("distance") double distance);
    }

    public interface LoginResponseCallback extends GeneralErrorInterface{
        void onLoginSuccess(UserToken userToken);
    }

    public interface ListingResponseCallback extends GeneralErrorInterface{
        void onGetListings(ArrayList<Listing> listings);
    }

    public interface UpdateListingResponseCallback extends GeneralErrorInterface{
        void onSuccessUpdateListing(String message);
    }

    Retrofit retrofit;
    public MainService() {
        //Enable Logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //Login
    public void login(String email, String password, final LoginResponseCallback callback){
        Call<UserToken> call = retrofit.create(MainServiceEndPoint.class).login(email,password);
        enqueueCall(call, new ResponseSuccessInterface<UserToken>() {
            @Override
            public void onSuccess(UserToken response) {
                callback.onLoginSuccess(response);
            }
        }, callback);
    }

    //Listing
    public void getListing(UserToken token, final ListingResponseCallback callback){
        Call<ListingResponse> call = retrofit.create(MainServiceEndPoint.class).listing(token.getId(),token.getToken());
        enqueueCall(call, new ResponseSuccessInterface<ListingResponse>() {
            @Override
            public void onSuccess(ListingResponse response) {
                callback.onGetListings(response.getListings());
            }
        }, callback);
    }

    //Update Listing
    public void updateListing(UserToken token,String id,String name,double distance,final UpdateListingResponseCallback callback){
        Call<ApiResponse> call = retrofit.create(MainServiceEndPoint.class).updateListing(token.getId(),token.getToken(),id,name,distance);
        enqueueCall(call, new ResponseSuccessInterface<ApiResponse>() {
            @Override
            public void onSuccess(ApiResponse response) {
                callback.onSuccessUpdateListing(response.getStatus().getMessage());
            }
        }, callback);
    }

}
