package com.example.interviewrgpt.Service;

import static com.example.interviewrgpt.getApiKey.apiKey;

import com.example.interviewrgpt.Model.ApiRequest;
import com.example.interviewrgpt.Model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface getEndpointService {

    @POST("v1/chat/completions")
    Call<ApiResponse> generateText(@Body ApiRequest request);
}
