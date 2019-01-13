package com.starla.resepmau.converter;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

//Kelas ini gak dipake
public class UserDeserializer<T> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement content = json.getAsJsonObject().get("content");
        return new Gson().fromJson(content, typeOfT);
    }
}
