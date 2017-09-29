package com.sicheng.smart_tv.services;

import com.sicheng.smart_tv.models.Category;
import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.models.Response;
import com.sicheng.smart_tv.models.Weather;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by av on 2017/9/12.
 */

public class CommonService {
    public static CommonServiceInterface getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommonServiceInterface service = retrofit.create(CommonServiceInterface.class);
        return service;
    }

    public interface CommonServiceInterface {
        @GET("/api/common/now")
        Call<Response<Long>> now();

        @GET("/api/common/weather")
        Call<Response<Weather>> weather();

        @GET("/api/common/category_list")
        Call<ListResponse<Category>> category_list();
    }
}
