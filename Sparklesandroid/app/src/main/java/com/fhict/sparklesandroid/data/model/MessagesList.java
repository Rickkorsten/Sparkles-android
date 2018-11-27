package com.fhict.sparklesandroid.data.model;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.List;

public class MessagesList {

    private List<IMessage> message;

    public List<IMessage> getMessage() {
        return message;
    }

    public void setResult(List<IMessage> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Item [result=" + message + "]";
    }
}