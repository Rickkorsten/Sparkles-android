package com.fhict.sparklesandroid.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

public class Message {
    @SerializedName("user")
    @Expose
    private Object user;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("relation_id")
    @Expose
    private String relationId;
    @SerializedName("_id")
    @Expose
    private String id;



    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}

