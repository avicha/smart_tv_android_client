package com.sicheng.smart_tv.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.adapters.TVPartAdapter;
import com.sicheng.smart_tv.models.Video;

import java.util.ArrayList;

public class TVPartsActivity extends Activity {
    private GridView tvPartsGridView;
    private ArrayList<Video> videos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_parts);
        this.tvPartsGridView = findViewById(R.id.tv_parts);
        Intent intent = getIntent();
        this.videos = intent.getParcelableArrayListExtra("playlist");
        TVPartAdapter tvPartAdapter = new TVPartAdapter(getApplicationContext(), this.videos);
        this.tvPartsGridView.setAdapter(tvPartAdapter);
        this.tvPartsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<Video> playlist = new ArrayList<>();
                for (int index = i, len = videos.size(); index < len; index++) {
                    playlist.add(videos.get(index));
                }
                Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
                intent.putParcelableArrayListExtra("playlist", playlist);
                startActivity(intent);
            }
        });
    }
}
