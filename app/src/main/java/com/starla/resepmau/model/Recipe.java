package com.starla.resepmau.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("published")
    @Expose
    private String published;

    public Recipe() { }

    public Recipe(int id, int user_id, String title, String content, String published) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.published = published;
    }

    public Recipe(int user_id, String title, String content) {
        this.user_id = user_id;
        this.title = title;
        this.content = content;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getUser_id() { return user_id; }

    public void setUser_id(int user_id) { this.user_id = user_id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

}
