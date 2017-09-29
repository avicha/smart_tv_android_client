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
        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        // Load image, decode it to Bitmap and return Bitmap to callback
        imageLoader.displayImage(imageUri, viewHolder.folderView);
        viewHolder.nameView.setText(tv.getName());
        StringBuffer buffer = new StringBuffer("主演：");
        ArrayList<String> actors = tv.getResource().getActors();
        buffer.append(actors.get(0) != null ? actors.get(0) : "");
        for (int index = 1, len = actors.size(); i < len; i++) {
            buffer.append(',');
            buffer.append(actors.get(index));
        }
        viewHolder.actorsView.setText(buffer);
        return view;
    }

    private final class ViewHolder {
        ImageView folderView;
        TextView nameView;
        TextView actorsView;
    }
}
