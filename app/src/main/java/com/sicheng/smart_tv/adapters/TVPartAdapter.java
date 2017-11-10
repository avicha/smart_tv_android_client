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
import com.sicheng.smart_tv.models.Video;

import java.util.ArrayList;

/**
 * Created by av on 2017/11/1.
 */

public class TVPartAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<Video> videos = new ArrayList<>();
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public TVPartAdapter(Context context, ArrayList<Video> videos) {
        this.ctx = context;
        this.videos = videos;
        this.inflater = LayoutInflater.from(this.ctx);
    }

    @Override
    public int getCount() {
        return this.videos.size();
    }

    @Override
    public Object getItem(int i) {
        return this.videos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        Video video = (Video) this.getItem(i);
        if (view == null) {
            switch (video.getViewType()) {
                case 1:
                    view = this.inflater.inflate(R.layout.component_tv_part_theme1, null);
                    viewHolder = new ViewHolder();
                    viewHolder.indexView = view.findViewById(R.id.tv_part_index);
                    viewHolder.vipView = view.findViewById(R.id.tv_part_vip);
                    viewHolder.previewView = view.findViewById(R.id.tv_part_preview);
                    break;
                case 2:
                    view = this.inflater.inflate(R.layout.component_tv_part_theme2, null);
                    viewHolder = new ViewHolder();
                    viewHolder.thumbView = view.findViewById(R.id.tv_part_thumb);
                    viewHolder.vipView = view.findViewById(R.id.tv_part_vip);
                    viewHolder.previewView = view.findViewById(R.id.tv_part_preview);
                    viewHolder.briefView = view.findViewById(R.id.tv_part_brief);
                    viewHolder.durationView = view.findViewById(R.id.tv_part_duration);
                    break;
            }
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        switch (video.getViewType()) {
            case 1:
                viewHolder.indexView.setText(String.valueOf(video.getIndex()));
                switch (video.getStatus()) {
                    case 2:
                        viewHolder.vipView.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        viewHolder.previewView.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case 2:
                String imageUri = video.getThumb();
                if (!imageUri.equals("")) {
                    imageLoader.displayImage(imageUri, viewHolder.thumbView);
                }
                switch (video.getStatus()) {
                    case 2:
                        viewHolder.vipView.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        viewHolder.previewView.setVisibility(View.VISIBLE);
                        break;
                }
                String brief = video.getBrief();
                if (!brief.equals("")) {
                    viewHolder.briefView.setText(brief);
                } else {
                    viewHolder.briefView.setVisibility(View.GONE);
                }
                viewHolder.durationView.setText("第" + video.getIndex() + "集  " + video.getDurationString());
                break;
        }
        return view;
    }

    private final class ViewHolder {
        ImageView thumbView;
        TextView indexView;
        TextView vipView;
        TextView previewView;
        TextView briefView;
        TextView durationView;
    }
}
