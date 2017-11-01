package com.sicheng.smart_tv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.models.TV;

import java.util.ArrayList;

/**
 * Created by av on 2017/8/15.
 */

public class TVAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<TV> tvs;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public TVAdapter(Context context, ArrayList<TV> tvs) {
        this.ctx = context;
        this.tvs = tvs;
        this.inflater = LayoutInflater.from(this.ctx);
    }

    @Override
    public int getCount() {
        return this.tvs.size();
    }

    @Override
    public Object getItem(int i) {
        return this.tvs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = this.inflater.inflate(R.layout.component_tv_single, null);
            viewHolder = new ViewHolder();
            viewHolder.folderView = view.findViewById(R.id.tv_folder);
            viewHolder.nameView = view.findViewById(R.id.tv_name);
            viewHolder.actorsView = view.findViewById(R.id.tv_actors);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        TV tv = (TV) this.getItem(i);
        String imageUri = tv.getResource().getFolder();

        imageLoader.displayImage(imageUri, viewHolder.folderView);
        viewHolder.nameView.setText(tv.getName());
        viewHolder.actorsView.setText("主演：" + tv.getResource().getActors());
        return view;
    }

    private final class ViewHolder {
        ImageView folderView;
        TextView nameView;
        TextView actorsView;
    }
}
