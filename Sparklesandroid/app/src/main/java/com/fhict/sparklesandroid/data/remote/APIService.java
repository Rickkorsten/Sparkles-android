package com.fhict.sparklesandroid.data.remote;
import com.fhict.sparklesandroid.data.model.User;

import java.io.Serializable;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("user/signup")
    @FormUrlEncoded
    Call<User> saveUser(@Field("firstName") String firstName,
                        @Field("gender") String gender,
                        @Field("device_id") String deviceId,
                        @Field("preference") String preference,
                        @Field("date_of_birth") Date date);


    @POST("user/login")
    @FormUrlEncoded
    Call<User> loginUser(@Field("firstName") String firstName,
                         @Field("device_id") String deviceId);
}
