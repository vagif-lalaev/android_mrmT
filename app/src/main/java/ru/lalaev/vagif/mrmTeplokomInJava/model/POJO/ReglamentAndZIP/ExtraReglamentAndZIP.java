package ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.ReglamentAndZIP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ExtraReglamentAndZIP {
    @SerializedName("ID")
    @Expose
    private int idReglament;
    @SerializedName("Title")
    @Expose
    private String titleReglament = "-";
    @SerializedName("Status")
    @Expose
    private byte statusReglament;

    @SerializedName("ID")
    @Expose
    private int idZIP;
    @SerializedName("Title")
    @Expose
    private String titleZIP = "-";
    @SerializedName("Cost")
    @Expose
    private int costZIP = 0;

    public ExtraReglamentAndZIP(int idReglament, String titleReglament, byte statusReglament) {
        this.idReglament = idReglament;
        this.titleReglament = titleReglament;
        this.statusReglament = statusReglament;
    }

    public ExtraReglamentAndZIP(int idZIP, String titleZIP) {
        this.idZIP = idZIP;
        this.titleZIP = titleZIP;
    }

    public ExtraReglamentAndZIP() {
    }

    public void setStatusReglament(byte statusReglament) {
        this.statusReglament = statusReglament;
    }

    public void setCostZIP(int costZIP) {
        this.costZIP = costZIP;
    }

    public int getIdReglament() {
        return idReglament;
    }

    public String getTitleReglament() {
        return titleReglament;
    }

    public byte getStatusReglament() {
        return statusReglament;
    }

    public int getIdZIP() {
        return idZIP;
    }

    public String getTitleZIP() {
        return titleZIP;
    }

    public int getCostZIP() {
        return costZIP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraReglamentAndZIP that = (ExtraReglamentAndZIP) o;
        return idReglament == that.idReglament &&
                statusReglament == that.statusReglament &&
                idZIP == that.idZIP &&
                costZIP == that.costZIP &&
                Objects.equals(titleReglament, that.titleReglament) &&
                Objects.equals(titleZIP, that.titleZIP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReglament, titleReglament, statusReglament, idZIP, titleZIP, costZIP);
    }

    @Override
    public String toString() {
        return "ExtraReglamentAndZIP{" +
                "idReglament=" + idReglament +
                ", titleReglament='" + titleReglament + '\'' +
                ", statusReglament=" + statusReglament +
                ", idZIP=" + idZIP +
                ", titleZIP='" + titleZIP + '\'' +
                ", costZIP=" + costZIP +
                '}';
    }
}
