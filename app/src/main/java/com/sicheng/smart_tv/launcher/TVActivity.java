package com.sicheng.smart_tv.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.fragments.SearchTVFragment;
import com.sicheng.smart_tv.fragments.TVListFragment;
import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.models.TV;
import com.sicheng.smart_tv.services.TVService;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVActivity extends Activity implements SearchTVFragment.OnFragmentInteractionListener, TVListFragment.OnFragmentInteractionListener {
    private SearchTVFragment searchTVFragment;
    private TVListFragment recommendedTVListFragment;
    private TVService.TVServiceInterface tvService = TVService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        initImageLoader(getApplicationContext());
        this.searchTVFragment = (SearchTVFragment) getFragmentManager().findFragmentById(R.id.search_tv_fragment);
        this.recommendedTVListFragment = (TVListFragment) getFragmentManager().findFragmentById(R.id.recommended_tv_list_fragment);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Call<ListResponse<TV>> call = this.tvService.recommend(20);
        call.enqueue(new Callback<ListResponse<TV>>() {
            @Override
            public void onResponse(Call<ListResponse<TV>> call, Response<ListResponse<TV>> response) {
                ListResponse<TV> resp = response.body();
                ArrayList<TV> tvs = resp.getResult();
                recommendedTVListFragment.setTVs(tvs);
            }

            @Override
            public void onFailure(Call<ListResponse<TV>> call, Throwable t) {
                Log.e("RECOMMEND_TV", call.request().url() + ": failed: " + t);
            }
        });
    }

    private void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }


    @Override
    public void onFragmentSearch(String keywords, ArrayList<TV> tvs, int totalRows) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("type", "tv");
        intent.putExtra("keywords", keywords);
        intent.putExtra("totalRows", totalRows);
        intent.putParcelableArrayListExtra("data", tvs);
        startActivity(intent);
    }
}
