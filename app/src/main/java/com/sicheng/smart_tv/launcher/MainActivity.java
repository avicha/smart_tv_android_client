package com.sicheng.smart_tv.launcher;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.fragments.NavFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements NavFragment.OnFragmentInteractionListener {
    private LinearLayout navBar;
    private List<String> menuList = Arrays.asList("电视剧", "电影", "综艺", "频道", "直播", "游戏", "足迹");
    private List<String> pages = Arrays.asList("tv", "movie", "variety", "channel", "live", "game", "history");
    private List<NavFragment> navFragments;
    private int currentNav = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.navBar = findViewById(R.id.nav_bar);
        this.navFragments = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.renderNavBar();
    }

    @Override
    public boolean onKeycodeMenuKeyUp(KeyEvent event) {
        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onKeycodeRightLongPress(KeyEvent event) {
        this.currentNav = (this.currentNav + 1) % this.menuList.size();
        this.activeCurrentNav();
        return false;
    }

    @Override
    public boolean onKeycodeLeftLongPress(KeyEvent event) {
        this.currentNav = (this.currentNav + this.menuList.size() - 1) % this.menuList.size();
        this.activeCurrentNav();
        return false;
    }

    public void renderNavBar() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0, len = menuList.size(); i < len; i++) {
            String menu = menuList.get(i);
            String page = pages.get(i);
            NavFragment navFragment = NavFragment.newInstance(menu, page);

            this.navFragments.add(navFragment);
            fragmentTransaction.add(R.id.nav_bar, navFragment);
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
        this.activeCurrentNav();
    }

    public void activeCurrentNav() {
        for (int i = 0, len = this.navBar.getChildCount(); i < len; i++) {
            NavFragment navFragment = this.navFragments.get(i);
            if (i == currentNav) {
                navFragment.focus();
            } else {
                navFragment.blur();
            }
        }

    }

    @Override
    public void onNavClick(String page) {
        this.currentNav = pages.indexOf(page);
        this.activeCurrentNav();
    }
}
