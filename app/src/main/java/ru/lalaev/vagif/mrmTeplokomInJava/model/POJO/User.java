package ru.lalaev.vagif.mrmTeplokomInJava.model.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("First_name")
    @Expose
    private String firstName;
    @SerializedName("Last_name")
    @Expose
    private String lastName;
    @SerializedName("Patronymic")
    @Expose
    private String patronymic;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Organisation")
    @Expose
    private String organisation;
    @SerializedName("Photo")
    @Expose
    private String photo;


    public User(String email, String firstName, String lastName, String patronymic) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getPhoto() {
        return photo;
    }
}
