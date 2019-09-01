package com.journey.network.gsonconverterfactory;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.journey.base.utils.Logger;
import com.journey.network.beans.BaseResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

public final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        String response = value.string();//把responsebody转为string
        Logger.i("response:  ",response);
        // 这里只是为了检测code是否==1,所以只解析BaseResult中的字段,因为只要code和message就可以了
        BaseResult httpStatus = gson.fromJson(response, BaseResult.class);
//        if (httpStatus.isCodeInvalid()) {
//            value.close();
        //抛出一个RuntimeException, 这里抛出的异常会到Subscriber的onError()方法中统一处理
//            throw new RuntimeException(httpStatus.getCode(), httpStatus.getMessage());
//        }

        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}