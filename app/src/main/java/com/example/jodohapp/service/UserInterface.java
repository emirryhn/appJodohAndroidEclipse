package com.example.jodohapp.service;

import com.example.jodohapp.entity.UserModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserInterface {

    @POST("/login")
    Call<String> userLogin(@Body UserModel userModel);

    @Multipart
    @POST("/register")
    Call<String> userRegister(@Part MultipartBody.Part file, @Part ("data") RequestBody data);

    @GET("/user/{gender}")
    Call<ArrayList<UserModel>> getUserByGender(@Header("Authorization") String header, @Path("gender")String gender);

}
