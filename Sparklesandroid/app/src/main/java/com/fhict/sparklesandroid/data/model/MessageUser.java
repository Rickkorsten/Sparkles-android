package com.fhict.sparklesandroid.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageUser {
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("firstName")
    @Expose
    private String name;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;

    public String get_id() {
        return _id;
    }

    public void set_id(String id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Override
//    public String toString() {
//        return "response{" +
//                "id='" + _id + '\'' +
//                ", name='" + name + '\'' +
//                '}';
//    }
}
