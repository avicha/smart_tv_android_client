package com.sicheng.smart_tv.services;

import com.sicheng.smart_tv.models.Response;
import com.sicheng.smart_tv.models.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by av on 2017/9/12.
 */

public final class UserService {
    public static UserServiceInterface getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserServiceInterface service = retrofit.create(UserServiceInterface.class);
        return service;
    }

    public interface UserServiceInterface {
        @GET("/api/user/login")
        Call<Response> login();

        @GET("/api/user/status")
        Call<Response<User>> status();
    }
}
