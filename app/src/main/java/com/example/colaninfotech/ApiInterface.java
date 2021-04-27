package com.example.colaninfotech;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("repos")
    Call<List<Data>> getData();
}