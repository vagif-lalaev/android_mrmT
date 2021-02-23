package ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.ReglamentAndZIP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ZIP {
    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Cost")
    @Expose
    private int cost;
    private final static long serialVersionUID = 7723538802947288937L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ZIP() {
    }

    public ZIP(int iD, String title) {
        this.iD = iD;
        this.title = title;
    }

    /**
     *
     * @param cost
     * @param iD
     * @param title
     */
    public ZIP(int iD, String title, int cost) {
        super();
        this.iD = iD;
        this.title = title;
        this.cost = cost;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZIP zip = (ZIP) o;
        return iD == zip.iD &&
                cost == zip.cost &&
                Objects.equals(title, zip.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iD, title, cost);
    }

    @Override
    public String toString() {
        return "ZIP{" +
                "iD=" + iD +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                '}';
    }
}
