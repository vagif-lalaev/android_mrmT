package ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.ReglamentAndZIP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubWorkClass implements Serializable {
    /**
     * Класс описывающий отображение регламентных работ на плане работника
     */

    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Status")
    @Expose
    private byte status;
    private final static long serialVersionUID = -796490899174014347L;

    /**
     * No args constructor for use in serialization
     *
     */
    public SubWorkClass() {
    }

    /**
     *
     * @param iD
     * @param title
     * @param status
     */
    public SubWorkClass(int iD, String title, byte status) {
        super();
        this.iD = iD;
        this.title = title;
        this.status = status;
    }
    /**
    * @param title
     * @param status
     * */

    public SubWorkClass(String title, byte status) {
        this.title = title;
        this.status = status;
    }

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
