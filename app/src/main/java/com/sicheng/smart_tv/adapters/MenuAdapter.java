package com.sicheng.smart_tv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.models.Menu;

import java.util.ArrayList;

/**
 * Created by av on 2017/9/4.
 */

public class MenuAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<Menu> menuList;

    public MenuAdapter(Context context, ArrayList<Menu> menuList) {
        this.ctx = context;
        this.menuList = menuList;
        this.inflater = LayoutInflater.from(this.ctx);
    }

    @Override
    public int getCount() {
        return this.menuList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.menuList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = this.inflater.inflate(R.layout.component_menu_single, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.menu_text);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Menu menu = (Menu) this.getItem(i);
        viewHolder.textView.setText(menu.getText());
        viewHolder.textView.setBackgroundColor(menu.getBackgroundColor());
        return view;
    }

    private final class ViewHolder {
        TextView textView;
    }
}
