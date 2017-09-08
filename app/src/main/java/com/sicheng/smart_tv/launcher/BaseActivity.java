package com.sicheng.smart_tv.launcher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by av on 2017/9/7.
 */

public class BaseActivity extends Activity {
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        //如果网络没连接，不是wifi连接页面的其他页面都先跳转到wifi连接页面
        if (this.isWifiFailed()) {
            this.redirectToWifiActivity();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.supplicantStateChangedBroadcastReceiver);
    }

    /**
     * 处理网络状态变化
     * @param context
     * @param intent
     */
    protected void handleSupplicantStateChanged(Context context, Intent intent) {
        if (this.isWifiFailed()) {
            this.redirectToWifiActivity();
        }
    }

    /**
     * 是否wifi连接失败
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
}
