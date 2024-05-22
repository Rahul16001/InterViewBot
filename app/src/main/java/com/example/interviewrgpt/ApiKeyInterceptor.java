package com.example.interviewrgpt;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {
    private final String apiKey;

    public ApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Add the API key to the request headers
        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type","application/json")
                .build();

        return chain.proceed(newRequest);
    }
}

