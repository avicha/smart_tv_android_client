package com.sicheng.smart_tv.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

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
import com.sicheng.smart_tv.fragments.StatusBarFragment;
import com.sicheng.smart_tv.models.Response;
import com.sicheng.smart_tv.models.User;
import com.sicheng.smart_tv.services.UserService;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by av on 2017/9/7.
 */

public class BaseActivity extends FragmentActivity {
    protected StatusBarFragment statusBarFragment;
    protected Toast toast;
    protected WifiManager wifiManager;
    protected BroadcastReceiver supplicantStateChangedBroadcastReceiver;
    protected UserService.UserServiceInterface userService = UserService.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);
        this.wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        this.supplicantStateChangedBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handleSupplicantStateChanged(context, intent);
            }
        };
        registerReceiver(this.supplicantStateChangedBroadcastReceiver, new IntentFilter(wifiManager.SUPPLICANT_STATE_CHANGED_ACTION));
        this.initImageLoader();
        //如果网络没连接，不是wifi连接页面的其他页面都先跳转到wifi连接页面
        if (this.isWifiFailed()) {
//            this.redirectToWifiActivity();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.statusBarFragment = (StatusBarFragment) getFragmentManager().findFragmentById(R.id.status_bar_fragment);
        if (this.statusBarFragment != null) {
            this.getUserStatus();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.supplicantStateChangedBroadcastReceiver);
    }

    protected void initImageLoader() {
        Context context = getApplicationContext();
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

    /**
     * 处理网络状态变化
     *
     * @param context
     * @param intent
     */
    protected void handleSupplicantStateChanged(Context context, Intent intent) {
        if (this.isWifiFailed()) {
//            this.redirectToWifiActivity();
        }
    }

    protected void getUserStatus() {
        Call<Response<User>> call = this.userService.status();
        call.enqueue(new Callback<Response<User>>() {
            @Override
            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                Response<User> resp = response.body();
                User user = resp.getResult();
                handleUserLoaded(user);
            }

            @Override
            public void onFailure(Call<Response<User>> call, Throwable t) {
                Log.e("API:USER_STATUS", call.request().url() + ": failed: " + t);
            }
        });
    }

    protected void handleUserLoaded(User user) {
        if (user != null) {
            this.statusBarFragment.setUserInfo(user);
        } else {
            this.redirectToLoginActivity();
        }
    }

    /**
     * 是否wifi连接失败
     *
     * @return
     */
    private boolean isWifiFailed() {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo == null || wifiInfo.getSupplicantState() != SupplicantState.COMPLETED;
    }

    /**
     * 重定向到wifi页面进行wifi连接
     */
    private void redirectToWifiActivity() {
        if (!this.getClass().equals(WifiActivity.class)) {
            Intent intent = new Intent(getApplicationContext(), WifiActivity.class);
            intent.putExtra("redirect_to", this.getClass().getName());
            startActivity(intent);
        }
    }

    /**
     * 重定向到login页面进行用户登录
     */
    private void redirectToLoginActivity() {
        if (!this.getClass().equals(WifiActivity.class)) {
            Intent intent = new Intent(getApplicationContext(), WifiActivity.class);
            intent.putExtra("redirect_to", this.getClass().getName());
            startActivity(intent);
        }
    }
}
