package com.example.yixu.fullcircle;

import android.location.Location;

public class Attractions {

    private int attractionId;
    private double attractionLong;
    private double attractionLat;
    private String attractionName;
    private String visitDate;

    public Attractions() {
    }

    public Attractions(String attractionName, double attractionLong, double attractionLat, String visitDate) {
        this.attractionLong = attractionLong;
        this.attractionLat = attractionLat;
        this.attractionName = attractionName;
        this.visitDate = visitDate;
    }

    public Attractions(int attractionId, String attractionName, double attractionLong, double attractionLat, String visitDate){

        this.attractionId = attractionId;
        this.attractionLat = attractionLat;
        this.attractionLong = attractionLong;
        this.attractionName = attractionName;
        this.visitDate = visitDate;

    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public double getAttractionLong() {
        return attractionLong;
    }

    public void setAttractionLong(double attractionLong) {
        this.attractionLong = attractionLong;
    }

    public double getAttractionLat() {
        return attractionLat;
    }

    public void setAttractionLat(double attractionLat) {
        this.attractionLat = attractionLat;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }
}
