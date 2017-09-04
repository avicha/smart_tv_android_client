package com.sicheng.smart_tv.services;

import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.models.TV;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by av on 2017/8/13.
 */

public interface TVService {
    @GET("/api/tv/search")
    Call<ListResponse<TV>> search(@Query("keywords") String keywords);
}
