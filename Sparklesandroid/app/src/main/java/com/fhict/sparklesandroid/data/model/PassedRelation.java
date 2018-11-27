package com.fhict.sparklesandroid.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PassedRelation {
    @SerializedName("progress")
    @Expose
    private Integer progress;

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("first_user_id")
    @Expose
    private PassedRelationUser firstUserId;

    @SerializedName("second_user_id")
    @Expose
    private PassedRelationUser secondUserId;

    @SerializedName("start_date")
    @Expose
    private Date startDate;

    @SerializedName("status")
    @Expose
    private String status;

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PassedRelationUser getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(PassedRelationUser firstUserId) {
        this.firstUserId = firstUserId;
    }

    public PassedRelationUser getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(PassedRelationUser secondUserId) {
        this.secondUserId = secondUserId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date secondUserId) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "progress='" + progress + '\'' +
                ", status='" + status + '\'' +
                ", firstUserId=" + firstUserId +
                ", secondUserId='" + secondUserId + '\'' +
                ", id='" + id + '\'' +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
