package ru.lalaev.vagif.mrmTeplokomInJava.model.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FileClass implements Serializable {
    @SerializedName("Req")
    @Expose
    private String req = "";
    @SerializedName("Work_id")
    @Expose
    private String workId = "";
    @SerializedName("Image")
    @Expose
    private String image = "fileName";

    public FileClass(String req, String workId) {
        this.req = req;
        this.workId = workId;
    }

    public FileClass() {
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getImage() {
        return image;
    }

}
