package com.sicheng.smart_tv.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sicheng.smart_tv.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavbarFragment extends Fragment {

    private LinearLayout navBar;
    private List<String> menuList = Arrays.asList("电视剧", "电影", "综艺", "频道", "直播", "游戏", "足迹");
    private List<String> pages = Arrays.asList("tv", "movie", "variety", "channel", "live", "game", "history");
    private ArrayList<NavFragment> navFragments = new ArrayList<>();
    private int currentNav = 0;

    public NavbarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navbar, container, false);
        this.navBar = view.findViewById(R.id.navbar);
        this.render();
        return view;
    }

    public void setCurrentNav(int currentNav) {
        this.currentNav = currentNav;
        this.activeCurrentNav();
    }

    public void setCurrentNav(String page) {
        int index = pages.indexOf(page);
        this.setCurrentNav(index);
    }

    public void next() {
        this.currentNav = (this.currentNav + 1) % this.menuList.size();
        this.activeCurrentNav();
    }

    public void prev() {
        this.currentNav = (this.currentNav + this.menuList.size() - 1) % this.menuList.size();
        this.activeCurrentNav();
    }

    public void render() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0, len = menuList.size(); i < len; i++) {
            String menu = menuList.get(i);
            String page = pages.get(i);
            NavFragment navFragment = NavFragment.newInstance(menu, page);
            this.navFragments.add(navFragment);
            fragmentTransaction.add(R.id.navbar, navFragment);
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
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
}
