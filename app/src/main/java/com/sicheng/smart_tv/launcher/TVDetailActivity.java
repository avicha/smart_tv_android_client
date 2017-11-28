package com.sicheng.smart_tv.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.models.ListResponse;
import com.sicheng.smart_tv.models.Response;
import com.sicheng.smart_tv.models.TV;
import com.sicheng.smart_tv.models.Video;
import com.sicheng.smart_tv.services.TVService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class TVDetailActivity extends BaseActivity {
    private ImageView folder;
    private TextView name;
    private TextView score;
    private TextView publishDate;
    private TextView status;
    private TextView actors;
    private TextView region;
    private TextView types;
    private TextView desc;
    private RelativeLayout play;
    private ImageView play_icon;
    private TV tv;
    private ArrayList<Video> videos;
    private TVService.TVServiceInterface tvService = TVService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);
        this.folder = findViewById(R.id.tv_folder);
        this.name = findViewById(R.id.tv_name);
        this.score = findViewById(R.id.tv_score);
        this.publishDate = findViewById(R.id.tv_publish_date);
        this.status = findViewById(R.id.tv_status);
        this.actors = findViewById(R.id.tv_actors);
        this.region = findViewById(R.id.tv_region);
        this.types = findViewById(R.id.tv_types);
        this.desc = findViewById(R.id.tv_desc);
        this.play = findViewById(R.id.tv_play);
        this.play_icon = findViewById(R.id.tv_play_icon);
        this.initEvents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        this.tv = intent.getParcelableExtra("tv");
        this.videos = null;
        this.render();
        this.fetchDetail();
        this.fetchVideos();
    }

    public void render() {
        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        // Load image, decode it to Bitmap and return Bitmap to callback
        imageLoader.displayImage(this.tv.getResource().getFolder(), this.folder);
        this.name.setText(this.tv.getName());
        this.score.setText(String.valueOf(this.tv.getResource().getScore()));
        String publishDate = this.tv.getResource().getPublish_date();
        this.publishDate.setText(publishDate.equals("") ? "暂无" : publishDate);
        this.status.setText(this.tv.getResource().getStatusText());
        this.actors.setText(this.tv.getResource().getActors());
        this.region.setText(this.tv.getResource().getRegion());
        this.types.setText(this.tv.getResource().getTypes());
        this.desc.setText(this.tv.getResource().getDesc());
        switch (this.tv.getResource().getSource()) {
            case 1:
                this.play_icon.setImageResource(R.mipmap.youku_logo);
                break;
            case 2:
                this.play_icon.setImageResource(R.mipmap.vqq_logo);
                break;
            case 3:
                this.play_icon.setImageResource(R.mipmap.iqiyi_logo);
                break;
        }
        this.play.requestFocus();
    }

    public void initEvents() {
        this.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
                intent.putParcelableArrayListExtra("playlist", videos);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeycodeMenuKeyUp(KeyEvent event) {
        Intent intent = new Intent(getApplicationContext(), TVVideosActivity.class);
        intent.putParcelableArrayListExtra("playlist", videos);
        startActivity(intent);
        return false;
    }

    public void fetchDetail() {
        Call<Response<TV>> call = tvService.get_detail(this.tv.getResource().getAlbum_id(), this.tv.getResource().getSource());
        call.enqueue(new Callback<Response<TV>>() {
            @Override
            public void onResponse(Call<Response<TV>> call, retrofit2.Response<Response<TV>> response) {
                Response<TV> resp = response.body();
                tv = resp.getResult();
                render();
            }

            @Override
            public void onFailure(Call<Response<TV>> call, Throwable t) {
                Log.e("API:TV_DETAIL", call.request().url() + ": failed: " + t);
            }
        });
    }

    public void fetchVideos() {
        Call<ListResponse<Video>> call = tvService.get_videos(this.tv.getResource().getAlbum_id(), this.tv.getResource().getSource());
        call.enqueue(new Callback<ListResponse<Video>>() {
            @Override
            public void onResponse(Call<ListResponse<Video>> call, retrofit2.Response<ListResponse<Video>> response) {
                ListResponse<Video> resp = response.body();
                videos = resp.getResult();
            }

            @Override
            public void onFailure(Call<ListResponse<Video>> call, Throwable t) {
                Log.e("API:TV_VIDEOS", call.request().url() + ": failed: " + t);
            }
        });
    }
}
