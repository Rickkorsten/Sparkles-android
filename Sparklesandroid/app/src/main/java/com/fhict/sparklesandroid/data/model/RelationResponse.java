package com.fhict.sparklesandroid.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelationResponse {
    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("data")
    @Expose
    private Object data;

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public Object getdata() {
        return data;
    }

    public void setdata(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "response{" +
                "confirmation='" + confirmation + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
