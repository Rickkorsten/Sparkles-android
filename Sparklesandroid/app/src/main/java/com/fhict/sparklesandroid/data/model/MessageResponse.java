package com.fhict.sparklesandroid.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("createdMessage")
    @Expose
    private Object createdMessage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getCreatedMessage() {
        return createdMessage;
    }

    public void setCreatedMessage(Object createdMessage) {
        this.createdMessage = createdMessage;
    }

    @Override
    public String toString() {
        return "response{" +
                "confirmation='" + message + '\'' +
                ", data='" + createdMessage + '\'' +
                '}';
    }
}
