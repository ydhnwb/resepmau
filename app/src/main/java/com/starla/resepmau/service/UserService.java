package com.starla.resepmau.service;

import com.starla.resepmau.converter.WrappedResponse;
import com.starla.resepmau.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

//    @FormUrlEncoded
//    @POST("login")
//    Call<User> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<WrappedResponse<User>> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<WrappedResponse<User>> register(@Field("name") String name,@Field("email") String email, @Field("password") String password);

}
