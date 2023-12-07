package com.smartvichar.smartvichar.smartsuvichar.network;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpGet {
    public NetCallback listener;

    public void setOnNetWorkListener(NetCallback listener) {
        this.listener = listener;
    }

    // Asynchronous Mode
    public void request_get(String strings, String tag) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(strings)
                    .get()
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    listener.onFailure(request, e, tag);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    listener.onResponse(response, tag);
                }

            });

        } catch (Exception e) {
            //error occuare when wrong url format
            listener.customError(e.toString(),tag);
        }
    }
}
