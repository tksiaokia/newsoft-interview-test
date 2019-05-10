package com.sean.newsoft.model.response;

import com.google.gson.annotations.SerializedName;
import com.sean.newsoft.model.Listing;

import java.util.ArrayList;

public class ListingResponse extends ApiResponse{
    @SerializedName("listing")
    private ArrayList<Listing> listings;

    public ArrayList<Listing> getListings() {
        return listings;
    }
}
