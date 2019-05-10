package com.sean.newsoft.model;

import com.google.gson.annotations.SerializedName;

public class Listing {
    private String id;
    @SerializedName("list_name")
    private String name;
    private double distance;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    public String getDistanceString(){
        return String.format("%.2f",distance);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
