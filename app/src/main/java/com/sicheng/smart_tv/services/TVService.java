package com.sicheng.smart_tv.services;

import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.models.TV;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by av on 2017/8/13.
 */

public final class TVService {

    public static TVServiceInterface getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TVServiceInterface service = retrofit.create(TVServiceInterface.class);
        return service;
    }

    public interface TVServiceInterface {
        @GET("/api/tv/search")
        Call<ListResponse<TV>> search(@Query("keywords") String keywords);
        @GET("/api/tv/recommend")
        Call<ListResponse<TV>> recommend(@Query("rows") int rows);
    }
}
