package com.fhict.sparklesandroid.data.remote;
import com.fhict.sparklesandroid.data.model.LoginResponse;
import com.fhict.sparklesandroid.data.model.RelationResponse;
import com.fhict.sparklesandroid.data.model.User;

import java.io.Serializable;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    Call<LoginResponse> loginUser(@Field("firstName") String firstName,
                                  @Field("device_id") String deviceId);


    @GET("user/{id}")
    Call<User> getUser(@Path("id") String userId);

    @GET(" search_match/{id}/{preference}/{language}")
    Call<RelationResponse> searchAndSetRelation(@Path("id") String userId,
                                                @Path("preference") String preference,
                                                @Path("language") String language);


}
