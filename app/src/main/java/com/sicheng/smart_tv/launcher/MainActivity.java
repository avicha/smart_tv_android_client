package com.sicheng.smart_tv.launcher;

import android.app.Activity;
import android.os.Bundle;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.fragments.StatusBarFragment;

public class MainActivity extends Activity implements StatusBarFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
