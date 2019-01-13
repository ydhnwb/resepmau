package com.starla.resepmau.converter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WrappedListResponse <T> {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<T> data = new ArrayList<>();

    public WrappedListResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() { return data; }

    public void setData(List<T> data) { this.data = data; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
