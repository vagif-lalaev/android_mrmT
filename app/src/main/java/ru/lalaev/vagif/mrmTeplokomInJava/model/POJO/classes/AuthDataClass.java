package ru.lalaev.vagif.mrmTeplokomInJava.model.POJO.classes;

import android.support.annotation.Nullable;

import com.squareup.moshi.Json;

import java.util.Objects;


public class AuthDataClass {
    @Nullable
    @Json(name = "token")
    private String token;

    @Nullable
    public final String getToken() {
        return this.token;
    }

    public final void setToken(@Nullable String var1) {
        this.token = var1;
    }

    public AuthDataClass(@Json(name = "token") @Nullable String token) {
        this.token = token;
    }

    public final AuthDataClass copy(@Json(name = "token") @Nullable String token) {
        return new AuthDataClass(token);
    }

    @Override
    public String toString() {
        return "AuthDataClass(token=" + this.token + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthDataClass that = (AuthDataClass) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
