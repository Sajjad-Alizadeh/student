package com.example.tutorial.model.network;

import com.example.tutorial.model.Student;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("experts/student")
    Single<List<Student>> getStudents();

    @POST("experts/student")
  //  @FormUrlEncoded
    Single<Student> addStudent(@Body JsonObject jsonObject);

}
