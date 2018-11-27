package com.fhict.sparklesandroid.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PassedRelationsResponse {
    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("data")
    @Expose
    private List<Relation> passedRelationsList;

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public List<Relation> getPassedRelationsList() {
        return passedRelationsList;
    }

    public void setPassedRelationsList(List<Relation> passedRelationsList) {
        this.passedRelationsList = passedRelationsList;
    }

//    @Override
//    public String toString() {
//        return "response{" +
//                "confirmation='" + confirmation + '\'' +
//                ", data='" + messagesList + '\'' +
//                '}';
//    }
}