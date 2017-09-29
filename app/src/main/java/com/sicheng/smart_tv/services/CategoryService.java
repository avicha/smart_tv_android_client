package com.sicheng.smart_tv.services;

import com.sicheng.smart_tv.models.Category;
import com.sicheng.smart_tv.models.ListResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by av on 2017/9/12.
 */

public final class CategoryService {
    public static CategoryServiceInterface getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CategoryServiceInterface service = retrofit.create(CategoryServiceInterface.class);
        return service;
    }

    public interface CategoryServiceInterface {
        @GET("/api/category/list")
        Call<ListResponse<Category>> list();
    }
}
