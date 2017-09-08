package com.sicheng.smart_tv.launcher;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.adapters.WifiAdapter;

import java.util.List;

public class WifiActivity extends BaseActivity {
    private TextView wifiInfoView;
    private TextView scanWifiStatusView;
    private ListView wifiListView;

    private String redirectTo;

    private BroadcastReceiver scanResultsAvailableBroadcastReceiver;
    final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        this.wifiInfoView = findViewById(R.id.wifi_info);
        this.scanWifiStatusView = findViewById(R.id.scan_wifi_status);
        this.wifiListView = findViewById(R.id.wifi_list);
        this.redirectTo = getIntent().getStringExtra("redirect_to");
        // wifi扫描完毕之后列出wifi
        this.scanResultsAvailableBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                List<ScanResult> scanResults = wifiManager.getScanResults();
                scanWifiStatusView.setText("已经搜索到附近的" + scanResults.size() + "个WIFI^_^");
                WifiAdapter wifiAdapter = new WifiAdapter(context, scanResults);
                wifiListView.setAdapter(wifiAdapter);
            }
        };
        //注册wifi扫描完毕广播接收器
        registerReceiver(this.scanResultsAvailableBroadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    @Override
    protected void onStart() {
        super.onStart();
        //如果wifi未打开，先打开wifi功能
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        //显示当前wifi状态
        this.setWifiInfo();
        //请求权限，扫描wifi列表
        this.checkAndGetWifiScanResults();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.scanResultsAvailableBroadcastReceiver);
    }

    private void setWifiInfo() {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null || wifiInfo.getSupplicantState() != SupplicantState.COMPLETED) {
            this.wifiInfoView.setText("你还未连接上任何WIFI网络");
        } else {
            //如果已经连接上网络，则跳转到其他页面
            this.wifiInfoView.setText("你当前已经连接上WIFI:" + wifiInfo.getSSID());
//            this.redirect();
        }
    }

    public int checkAndGetWifiScanResults() {
        //检查权限
        String[] permissions = new String[2];
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions[1] = Manifest.permission.ACCESS_COARSE_LOCATION;
        }
        Log.d("wifi", "need permissions:" + permissions[0] + "," + permissions[1]);
        //两个权限都没有授权，则请求用户授权
        if (permissions[0] != null || permissions[1] != null) {
            requestPermissions(permissions, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return PackageManager.PERMISSION_DENIED;
        } else {
            //否则直接扫描wifi
            this.startScan();
            return PackageManager.PERMISSION_GRANTED;
        }
    }

    /**
     * 扫描wifi
     */
    private void startScan() {
        wifiManager.startScan();
        this.scanWifiStatusView.setText("正在扫描附近的WIFI，请稍后^_^");
    }

    /**
     * 网络连接成功后重定向到上一个界面
     */
    private void redirect() {
        Intent intent;
        Class activityClass = null;
        try {
            activityClass = this.redirectTo == null ? MainActivity.class : Class.forName(this.redirectTo);
        } catch (ClassNotFoundException e) {
            activityClass = MainActivity.class;
        } finally {
            intent = new Intent(getApplicationContext(), activityClass);
            startActivity(intent);
        }
    }

    /**
     * 用户权限请求回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("wifi", "onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                //如果都同意授权
                if (permissions.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED || (permissions.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    this.startScan();
                } else {
                    toast.setText("用户拒绝授权");
                    toast.show();
                }
                break;
        }
    }


    /**
     * 处理网络状态变化
     * @param context
     * @param intent
     */
    @Override
    protected void handleSupplicantStateChanged(Context context, Intent intent) {
        this.setWifiInfo();
    }
}
