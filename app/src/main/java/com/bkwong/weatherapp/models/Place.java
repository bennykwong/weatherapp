package com.bkwong.weatherapp.models;

public class Place {
    private String lat;
    private String lon;

    public Place(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

}
