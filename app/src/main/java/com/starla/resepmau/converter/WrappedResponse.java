package com.starla.resepmau.converter;

import com.google.gson.annotations.SerializedName;

public class WrappedResponse<T> {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private T data;

    public WrappedResponse() {
    }

    public WrappedResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
