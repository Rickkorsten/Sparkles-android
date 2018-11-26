package com.fhict.sparklesandroid.data.model;

import java.util.List;

public class MessagesList {

    private List<Message> message;

    public List<Message> getMessage() {
        return message;
    }

    public void setResult(List<Message> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Item [result=" + message + "]";
    }
}