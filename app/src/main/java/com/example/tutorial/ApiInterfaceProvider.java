package com.example.tutorial;

import com.example.tutorial.model.network.ApiInterface;
import com.example.tutorial.model.network.ApiService;

public class ApiInterfaceProvider {
    private static ApiInterface apiInterface;

    public static ApiInterface getInstance() {
        if (apiInterface == null){
            apiInterface = ApiService.getRetrofit().create(ApiInterface.class);
        }
        return apiInterface;
    }
}
