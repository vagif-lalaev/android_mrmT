package ru.lalaev.vagif.mrmTeplokomInJava.model.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class WorkClass implements Serializable, Parcelable {

    @SerializedName("ID")
    @Expose
    private long iD;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Client")
    @Expose
    private String client;
    @SerializedName("Contact")
    @Expose
    private String contact;
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
    @SerializedName("Kind")
    @Expose
    private long kind;
    @SerializedName("Recommendation")
    @Expose
    private String recommendation;

    public final static Parcelable.Creator<WorkClass> CREATOR = new Creator<WorkClass>() {


        @SuppressWarnings({
                "unchecked"
        })
        public WorkClass createFromParcel(Parcel in) {
            return new WorkClass(in);
        }

        public WorkClass[] newArray(int size) {
            return (new WorkClass[size]);
        }

    };
    private final static long serialVersionUID = 376130287224707845L;

    protected WorkClass(Parcel in) {
        this.iD = ((long) in.readValue((long.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.client = ((String) in.readValue((String.class.getClassLoader())));
        this.contact = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.duration = ((long) in.readValue((long.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((long) in.readValue((long.class.getClassLoader())));
        this.kind = ((long) in.readValue((long.class.getClassLoader())));
        this.recommendation = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * @param id
     * @param title
     * @param client
     * @param contact
     * @param address
     * @param date
     * @param duration
     * @param comment
     * @param status
     * @param kind
     * @param recommendation
     */
    public WorkClass(long id, String title, String client, String contact, String address, @Nullable String date, long duration, String comment, long status, long kind, String recommendation) {
        this.iD = id;
        this.title = title;
        this.client = client;
        this.contact = contact;
        this.address = address;
        this.date = date;
        this.duration = duration;
        this.comment = comment;
        this.status = status;
        this.kind = kind;
        this.recommendation = recommendation;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public long getKind() {
        return kind;
    }

    public void setKind(long kind) {
        this.kind = kind;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(iD);
        dest.writeValue(title);
        dest.writeValue(client);
        dest.writeValue(contact);
        dest.writeValue(address);
        dest.writeValue(date);
        dest.writeValue(duration);
        dest.writeValue(comment);
        dest.writeValue(status);
        dest.writeValue(kind);
        dest.writeValue(recommendation);
    }

    public int describeContents() {
        return 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkClass workClass = (WorkClass) o;
        return iD == workClass.iD &&
                duration == workClass.duration &&
                status == workClass.status &&
                kind == workClass.kind &&
                Objects.equals(title, workClass.title) &&
                Objects.equals(client, workClass.client) &&
                Objects.equals(contact, workClass.contact) &&
                Objects.equals(address, workClass.address) &&
                Objects.equals(date, workClass.date) &&
                Objects.equals(comment, workClass.comment) &&
                Objects.equals(recommendation, workClass.recommendation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iD, title, client, contact, address, date, duration, comment, status, kind, recommendation);
    }

    @Override
    public String toString() {
        return "WorkClass{" +
                "iD=" + iD +
                ", title='" + title + '\'' +
                ", client='" + client + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", duration=" + duration +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", kind=" + kind +
                ", recommendation='" + recommendation + '\'' +
                '}';
    }
}
