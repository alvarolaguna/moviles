package com.sam.elderwatch;

import android.media.Image;
import android.provider.MediaStore;

/**
 * Created by SAM on 31/3/2016.
 */
public class MyVideo {

    private String cause, date, thumbnail, url;

    public MyVideo(String cause, String date, String thumbnail, String url) {
        this.cause = cause;
        this.date = date;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    public MyVideo() {
        this.cause = "";
        this.date = "";
        this.thumbnail = "";
        this.url = "";
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void printVideo(){
        System.out.println("Causa "+this.cause + " Fecha "+this.date + " Thumbnail " + this.thumbnail + " URL " + this.url);
    }
}