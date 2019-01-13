package com.starla.resepmau.util;

import com.starla.resepmau.network.RetrofitClient;
import com.starla.resepmau.service.PostService;
import com.starla.resepmau.service.UserService;

public class ApiUtil {
    private ApiUtil(){ }

    private static final String API_URL = "https://resep-mau.herokuapp.com/api/";

    public static UserService getUserService(){ return RetrofitClient.getClient(API_URL).create(UserService.class); }

    public static PostService getPostService(){
        return RetrofitClient.getClient(API_URL).create(PostService.class);
    }
}