package com.sicheng.smart_tv.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
import com.sicheng.smart_tv.models.TV;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchResultActivity extends Activity implements SearchTVFragment.OnFragmentInteractionListener, TVListFragment.OnFragmentInteractionListener {
    private SearchTVFragment searchTVFragment;
    private TextView fragmentTvListText;
    private TVListFragment TVListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initImageLoader(getApplicationContext());
        this.searchTVFragment = (SearchTVFragment) getFragmentManager().findFragmentById(R.id.search_tv_fragment);
        this.fragmentTvListText = findViewById(R.id.fragment_tv_list_text);
        this.TVListFragment = (TVListFragment) getFragmentManager().findFragmentById(R.id.tv_list_fragment);
        Intent intent = getIntent();
        String keywords = intent.getStringExtra("keywords");
        ArrayList<TV> tvs = intent.getParcelableArrayListExtra("data");
        int totalRows = intent.getIntExtra("totalRows", 0);
        this.fragmentTvListText.setText(getString(R.string.fragment_tv_list_text) + "(" + totalRows + "个搜索结果)");
        for (int i = 0, l = tvs.size(); i < l; i++) {
            TV tv = tvs.get(i);
            Log.d("TV LIST", "tv name:" + tv.getName());
        }
        this.searchTVFragment.setQuery(keywords, false);
        this.TVListFragment.setTVs(tvs);
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
        this.searchTVFragment.setQuery(keywords, false);
        this.fragmentTvListText.setText(getString(R.string.fragment_tv_list_text) + "(" + totalRows + "个搜索结果)");
        this.TVListFragment.setTVs(tvs);
    }

    private final class CustomImageDownaloder extends BaseImageDownloader {

        public CustomImageDownaloder(Context context) {
            super(context);
        }

        public CustomImageDownaloder(Context context, int connectTimeout, int readTimeout) {
            super(context, connectTimeout, readTimeout);
        }

        @Override
        protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
            HttpURLConnection conn = super.createConnection(url, extra);
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
            for (Map.Entry<String, String> header : headers.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
            return conn;
        }
    }
}


