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
import android.view.KeyEvent;
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

import java.io.File;

/**
 * Created by av on 2017/9/7.
 */

public class BaseActivity extends FragmentActivity {
    protected StatusBarFragment statusBarFragment;
    protected Toast toast;
    protected WifiManager wifiManager;
    protected BroadcastReceiver supplicantStateChangedBroadcastReceiver;


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


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i("KeyUp", String.valueOf(keyCode));
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return onKeycodeLeftKeyUp(event);
            case KeyEvent.KEYCODE_DPAD_UP:
                return onKeycodeUpKeyUp(event);
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return onKeycodeRightKeyUp(event);
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return onKeycodeDownKeyUp(event);
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                return onKeycodeEnterKeyUp(event);
            case KeyEvent.KEYCODE_BACK:
                return onKeycodeBackKeyUp(event);
            case KeyEvent.KEYCODE_HOME:
                return onKeycodeHomeKeyUp(event);
            case KeyEvent.KEYCODE_MENU:
                return onKeycodeMenuKeyUp(event);
            default:
                return super.onKeyUp(keyCode, event);
        }
    }


    public boolean onKeycodeLeftKeyUp(KeyEvent event) {
        return super.onKeyUp(KeyEvent.KEYCODE_DPAD_LEFT, event);
    }

    public boolean onKeycodeUpKeyUp(KeyEvent event) {
        return super.onKeyUp(KeyEvent.KEYCODE_DPAD_UP, event);
    }

    public boolean onKeycodeRightKeyUp(KeyEvent event) {
        return super.onKeyUp(KeyEvent.KEYCODE_DPAD_RIGHT, event);
    }

    public boolean onKeycodeDownKeyUp(KeyEvent event) {
        return super.onKeyUp(KeyEvent.KEYCODE_DPAD_DOWN, event);
    }

    public boolean onKeycodeEnterKeyUp(KeyEvent event) {
        return super.onKeyUp(KeyEvent.KEYCODE_DPAD_CENTER, event);
    }

    public boolean onKeycodeBackKeyUp(KeyEvent event) {
        return super.onKeyUp(KeyEvent.KEYCODE_BACK, event);
    }

    public boolean onKeycodeHomeKeyUp(KeyEvent event) {
        return super.onKeyUp(KeyEvent.KEYCODE_HOME, event);
    }


    public boolean onKeycodeMenuKeyUp(KeyEvent event) {
        return super.onKeyUp(KeyEvent.KEYCODE_MENU, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Log.i("KeyLongPress", String.valueOf(keyCode));
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return onKeycodeLeftLongPress(event);
            case KeyEvent.KEYCODE_DPAD_UP:
                return onKeycodeUpLongPress(event);
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return onKeycodeRightLongPress(event);
            case KeyEvent.KEYCODE_DPAD_DOWN:
                return onKeycodeDownLongPress(event);
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                return onKeycodeEnterLongPress(event);
            case KeyEvent.KEYCODE_BACK:
                return onKeycodeBackLongPress(event);
            case KeyEvent.KEYCODE_HOME:
                return onKeycodeHomeLongPress(event);
            case KeyEvent.KEYCODE_MENU:
                return onKeycodeMenuLongPress(event);
            default:
                return super.onKeyLongPress(keyCode, event);
        }
    }

    public boolean onKeycodeLeftLongPress(KeyEvent event) {
        return super.onKeyLongPress(KeyEvent.KEYCODE_DPAD_LEFT, event);
    }

    public boolean onKeycodeUpLongPress(KeyEvent event) {
        return super.onKeyLongPress(KeyEvent.KEYCODE_DPAD_UP, event);
    }

    public boolean onKeycodeRightLongPress(KeyEvent event) {
        return super.onKeyLongPress(KeyEvent.KEYCODE_DPAD_RIGHT, event);
    }

    public boolean onKeycodeDownLongPress(KeyEvent event) {
        return super.onKeyLongPress(KeyEvent.KEYCODE_DPAD_DOWN, event);
    }

    public boolean onKeycodeEnterLongPress(KeyEvent event) {
        return super.onKeyLongPress(KeyEvent.KEYCODE_DPAD_CENTER, event);
    }

    public boolean onKeycodeBackLongPress(KeyEvent event) {
        return super.onKeyLongPress(KeyEvent.KEYCODE_BACK, event);
    }

    public boolean onKeycodeHomeLongPress(KeyEvent event) {
        return super.onKeyLongPress(KeyEvent.KEYCODE_HOME, event);
    }


    public boolean onKeycodeMenuLongPress(KeyEvent event) {
        return super.onKeyLongPress(KeyEvent.KEYCODE_MENU, event);
    }
}
