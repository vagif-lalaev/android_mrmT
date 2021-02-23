package ru.lalaev.vagif.mrmTeplokomInJava.model.POJO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdditionalWorkClass implements Serializable {

    @SerializedName("Req")
    @Expose
    private String req;

    @SerializedName("Work_id")
    @Expose
    private String workId;

    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("Date")
    @Expose
    private String date;

    private String pathImage;


    @SerializedName("extra_work_id")
    @Expose
    private String idResponseAddWork;

    @SerializedName("recommendation_id")
    @Expose
    private String idResponseRecommendationWork;

    @SerializedName("ID")
    @Expose
    private String idEvent;


    public String getIdResponseAddWork() {
        return idResponseAddWork;
    }

    public String getIdResponseRecommendationWork() {
        return idResponseRecommendationWork;
    }

    public AdditionalWorkClass(String idEvent, String title, String pathImage) {
        this.title = title;
        this.pathImage = pathImage;
        this.idEvent = idEvent;
    }

    public AdditionalWorkClass(String idEvent, String title, String pathImage, String date) {
        this.title = title;
        this.date = date;
        this.pathImage = pathImage;
        this.idEvent = idEvent;
    }

    public AdditionalWorkClass(String title) {
        this.title = title;
    }

    public AdditionalWorkClass() {
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getReq() {
        return req;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getPathImage() {
        return pathImage;
    }

    @Override
    public String toString() {
        return idEvent;
    }

}
