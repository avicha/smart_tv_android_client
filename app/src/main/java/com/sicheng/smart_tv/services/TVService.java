package com.sicheng.smart_tv.services;

import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.models.SearchOption;
import com.sicheng.smart_tv.models.TV;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by av on 2017/8/13.
 */

public final class TVService {

    public static TVServiceInterface getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TVServiceInterface service = retrofit.create(TVServiceInterface.class);
        return service;
    }

    public interface TVServiceInterface {
        @GET("/api/tv/search")
        Call<ListResponse<TV>> search(@QueryMap Map<String, String> filters);

        @GET("/api/tv/get_search_options")
        Call<ListResponse<SearchOption>> getSearchOptions();
    }
}
