package com.quynhlm.dev.demoonthi1.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequest {
    private ApiServer apiServer;

    public ApiRequest () {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiServer = retrofit.create(ApiServer.class);
    }
    public ApiServer getApiService() {
        return apiServer;
    }
}
