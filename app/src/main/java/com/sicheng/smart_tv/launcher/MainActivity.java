package com.sicheng.smart_tv.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.sicheng.smart_tv.R;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onKeycodeMenuKeyUp(KeyEvent event) {
        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
        startActivity(intent);
        return false;
    }
}
