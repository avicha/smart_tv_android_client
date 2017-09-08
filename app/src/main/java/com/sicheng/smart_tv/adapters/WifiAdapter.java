package com.sicheng.smart_tv.adapters;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sicheng.smart_tv.R;

import java.util.List;

/**
 * Created by av on 2017/9/6.
 */

public class WifiAdapter extends BaseAdapter {
    private Context ctx;
    private List<ScanResult> scanResults;
    private LayoutInflater inflater;

    public WifiAdapter(Context context, List<ScanResult> scanResults) {
        this.ctx = context;
        this.scanResults = scanResults;
        this.inflater = LayoutInflater.from(this.ctx);
    }

    @Override
    public int getCount() {
        return this.scanResults.size();
    }

    @Override
    public Object getItem(int i) {
        return this.scanResults.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = this.inflater.inflate(R.layout.component_wifi_single, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.wifi_info);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ScanResult scanResult = (ScanResult) this.getItem(i);
        viewHolder.textView.setText(scanResult.SSID);
        return view;
    }

    private final class ViewHolder {
        TextView textView;
    }
}
