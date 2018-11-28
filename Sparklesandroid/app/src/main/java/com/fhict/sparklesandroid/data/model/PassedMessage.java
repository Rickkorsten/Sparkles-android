package com.fhict.sparklesandroid.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

public class PassedMessage {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;
    @SerializedName("relation_id")
    @Expose
    private String relationId;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user")
    @Expose
    private UserInfo user;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String user) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) { this.text = text; }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
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

