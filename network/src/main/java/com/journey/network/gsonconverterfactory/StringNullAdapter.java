package com.journey.network.gsonconverterfactory;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.journey.base.utils.Logger;

import java.io.IOException;

public class StringNullAdapter extends TypeAdapter<String> {
    @Override
    public String read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return "";//原先是返回Null，这里改为返回空字符串
        }

        String jsonStr = reader.nextString();

        Logger.i("jsonStr","速通卡   "+jsonStr);
        if(jsonStr.equals("null")) {
            return "";
        }else {
            return jsonStr;
        }
    }

    @Override
    public void write(JsonWriter writer, String value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }
}