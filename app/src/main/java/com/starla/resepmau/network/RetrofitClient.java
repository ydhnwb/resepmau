package com.starla.resepmau.network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starla.resepmau.LoginActivity;
import com.starla.resepmau.MainActivity;
import com.starla.resepmau.converter.UserDeserializer;
import com.starla.resepmau.model.User;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    private static OkHttpClient h = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build();


    public static Retrofit getClient(String url){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .client(h)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
