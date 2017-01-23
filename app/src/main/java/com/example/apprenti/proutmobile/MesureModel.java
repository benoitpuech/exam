package com.example.apprenti.proutmobile;


/**
 * Created by apprenti on 23/01/17.
 */

public class MesureModel {

    private float latitude;
    private float longitude;
    private Integer odeur;
    private String date;

    public MesureModel(float latitude, float longitude, Integer odeur, String date){
        this.latitude = latitude;
        this.longitude = longitude;
        this.odeur = odeur;
        this.date = date;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getOdeur() {
        return odeur;
    }

    public void setOdeur(Integer odeur) {
        this.odeur = odeur;
    }
}
