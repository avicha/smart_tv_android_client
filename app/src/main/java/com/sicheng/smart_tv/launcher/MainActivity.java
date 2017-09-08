package com.sicheng.smart_tv.launcher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.adapters.MenuAdapter;
import com.sicheng.smart_tv.models.Menu;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private GridView menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.menuList = findViewById(R.id.menu_list);
        //设置菜单列表
        final ArrayList<Menu> menuList = new ArrayList<Menu>();
        Menu menu1 = new Menu("电视剧", TVActivity.class);
        menu1.setBackgroundColor(Color.argb(127, 255, 255, 0));
        menuList.add(menu1);
        MenuAdapter menuAdapter = new MenuAdapter(getApplicationContext(), menuList);
        this.menuList.setAdapter(menuAdapter);
        //点击菜单时跳到相应到页面
        this.menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Menu menu = menuList.get(i);
                Intent intent = new Intent(getApplicationContext(), menu.getActivity());
                Log.d("MENU CLICK", "onItemClick: " + menu.getActivity().getName());
                startActivity(intent);
            }
        });
    }
}
