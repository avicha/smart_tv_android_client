package com.sicheng.smart_tv.services;

import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.models.Response;
import com.sicheng.smart_tv.models.SearchOption;
import com.sicheng.smart_tv.models.TV;
import com.sicheng.smart_tv.models.Video;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
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

        @GET("/api/tv/get_detail")
        Call<Response<TV>> get_detail(@Query("album_id") String album_id, @Query("source") int source);

        @GET("/api/tv/get_videos")
        Call<ListResponse<Video>> get_videos(@Query("album_id") String album_id, @Query("source") int source);
    }
}
