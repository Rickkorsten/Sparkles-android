package com.fhict.sparklesandroid.data.model;

import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        import java.util.List;

public class RelationMessagesResponse {
    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("data")
    @Expose
    private List<Message> messagesList;

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public List<Message> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<Message> messagesList) {
        this.messagesList = messagesList;
    }

//    @Override
//    public String toString() {
//        return "response{" +
//                "confirmation='" + confirmation + '\'' +
//                ", data='" + messagesList + '\'' +
//                '}';
//    }
}