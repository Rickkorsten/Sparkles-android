package com.fhict.sparklesandroid.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("createdMessage")
    @Expose
    private Message createdMessage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message getCreatedMessage() {
        return createdMessage;
    }

    public void setCreatedMessage(Message createdMessage) {
        this.createdMessage = createdMessage;
    }

//    @Override
//    public String toString() {
//        return "response{" +
//                "confirmation='" + message + '\'' +
//                ", data='" + createdMessage + '\'' +
//                '}';
//    }
}
