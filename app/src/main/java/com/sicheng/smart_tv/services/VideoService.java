package com.sicheng.smart_tv.services;

import com.sicheng.smart_tv.models.Response;
import com.sicheng.smart_tv.models.VideoPlayInfo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by av on 2017/8/13.
 */

public final class VideoService {

    public static VideoServiceInterface getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VideoServiceInterface service = retrofit.create(VideoServiceInterface.class);
        return service;
    }

    public interface VideoServiceInterface {

        @GET("/api/video/get_play_info")
        Call<Response<VideoPlayInfo>> get_play_info(@Query("video_id") String video_id, @Query("source") int source);
    }
}
