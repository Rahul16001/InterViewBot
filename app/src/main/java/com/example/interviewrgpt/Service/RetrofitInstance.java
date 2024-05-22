package com.example.interviewrgpt.Service;

import static com.example.interviewrgpt.getApiKey.apiKey;

import com.example.interviewrgpt.ApiKeyInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInstance {

    static final String BASE_URL = "https://api.openai.com/";

    static Retrofit retrofit  = null;

    static public getEndpointService getService()
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new ApiKeyInterceptor(apiKey));

        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                .build();
        }

        return retrofit.create(getEndpointService.class);
    }
}
