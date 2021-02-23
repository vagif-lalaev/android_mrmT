package ru.lalaev.vagif.mrmTeplokomInJava.model.POJO;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestClass implements Serializable, Parcelable {
    @SerializedName("ID")
    @Expose
    private long iD;

    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("Client")
    @Expose
    private String client;

    @SerializedName("Address")
    @Expose
    private String address;

    @SerializedName("Date")
    @Expose
    private String date;

    @SerializedName("Duration")
    @Expose
    private long duration;

    @SerializedName("Comment")
    @Expose
    private String comment;

    @SerializedName("Status")
    @Expose
    private long status;

    public final static Parcelable.Creator<RequestClass> CREATOR = new Creator<RequestClass>() {

        @SuppressWarnings({"unchecked"})
        public RequestClass createFromParcel(Parcel in) {
            return new RequestClass(in);
        }

        public RequestClass[] newArray(int size) {
            return (new RequestClass[size]);
        }
    };
    private final static long serialVersionUID = 9105350302630718778L;

    protected RequestClass(Parcel in) {
        this.iD = ((long) in.readValue((long.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.client = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.duration = ((long) in.readValue((long.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((long) in.readValue((long.class.getClassLoader())));
    }

    public RequestClass(String date, String title, String address, String client) {
        this.date = date;
        this.title = title;
        this.address = address;
        this.client = client;
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestClass() {
    }

    /**
     *
     * @param date
     * @param duration
     * @param address
     * @param client
     * @param comment
     * @param iD
     * @param title
     * @param status
     */
    public RequestClass(long iD, String title, String client, String address, String date, long duration, String comment, long status) {
        super();
        this.iD = iD;
        this.title = title;
        this.client = client;
        this.address = address;
        this.date = date;
        this.duration = duration;
        this.comment = comment;
        this.status = status;
    }

    public long getID() {
        return iD;
    }

    public void setID(long iD) {
        this.iD = iD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(iD);
        dest.writeValue(title);
        dest.writeValue(client);
        dest.writeValue(address);
        dest.writeValue(date);
        dest.writeValue(duration);
        dest.writeValue(comment);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "RequestClass{" +
                "iD=" + iD +
                ", title='" + title + '\'' +
                ", client='" + client + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", duration=" + duration +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }
}
