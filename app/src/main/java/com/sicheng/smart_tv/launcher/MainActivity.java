package com.sicheng.smart_tv.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.fragments.NavFragment;
import com.sicheng.smart_tv.fragments.NavbarFragment;

public class MainActivity extends BaseActivity implements NavFragment.OnFragmentInteractionListener {
    private NavbarFragment navbarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.navbarFragment = (NavbarFragment) getFragmentManager().findFragmentById(R.id.navbar_fragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.navbarFragment.setCurrentNav(0);
    }

    @Override
    public boolean onKeycodeMenuKeyUp(KeyEvent event) {
        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onKeycodeRightLongPress(KeyEvent event) {
        this.navbarFragment.next();
        return false;
    }

    @Override
    public boolean onKeycodeLeftLongPress(KeyEvent event) {
        this.navbarFragment.prev();
        return false;
    }

    @Override
    public void onNavClick(String page) {
        this.navbarFragment.setCurrentNav(page);
        switch (page) {

        }
    }
}
