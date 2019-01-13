package com.starla.resepmau.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("api_token")
    @Expose
    private String api_token;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    public User() { }


    public User(int id, String name, String api_token, String email) {
        this.id = id;
        this.name = name;
        this.api_token = api_token;
        this.email = email;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(int id, String name, String api_token, String email, String password) {
        this.id = id;
        this.name = name;
        this.api_token = api_token;
        this.email = email;
        this.password = password;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
