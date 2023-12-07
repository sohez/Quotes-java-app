package com.smartvichar.smartvichar.smartsuvichar.network;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public interface NetCallback {
    void onFailure(Request request, IOException e, String tag);
    void onResponse(Response response, String tag);
    void customError(String str,String tag);
}
