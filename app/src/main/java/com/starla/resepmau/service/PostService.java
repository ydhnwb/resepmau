package com.starla.resepmau.service;

import com.starla.resepmau.converter.WrappedListResponse;
import com.starla.resepmau.converter.WrappedResponse;
import com.starla.resepmau.model.Recipe;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostService {

    @GET("post")
    Call<WrappedListResponse<Recipe>> getPosts(@Header("Authorization")String token);

    @FormUrlEncoded
    @POST("post")
    Call<WrappedResponse<Recipe>> addNew(@Header("Authorization")String token, @Field("title") String title, @Field("content") String content);


    @GET("post/{id}")
    Call<WrappedResponse<Recipe>> find(@Header("Authorization")String token, @Path("id") String id);

    @DELETE("post/{id}")
    Call<WrappedResponse<Recipe>> delete(@Header("Authorization")String token, @Path("id") String id);

    @FormUrlEncoded
    @PUT("post/{id}")
    Call<WrappedResponse<Recipe>> update(@Header("Authorization")String token, @Path("id") String id, @Field("title") String title,
                                         @Field("content") String content);

}
