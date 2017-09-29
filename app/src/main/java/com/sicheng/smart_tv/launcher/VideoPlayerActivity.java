package com.sicheng.smart_tv.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.models.Resource;
import com.sicheng.smart_tv.models.TV;

import java.util.ArrayList;

public class VideoPlayerActivity extends Activity {
    private WebView videoPlayer;
    private ArrayList<TV> playlist = new ArrayList<TV>();
    private int current;
    private ArrayList<Resource> resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        this.videoPlayer = findViewById(R.id.video_player);
        this.videoPlayer.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        WebSettings webSettings = this.videoPlayer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMixedContentMode(webSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
        Intent intent = getIntent();
        ArrayList<TV> added_playlist = intent.getParcelableArrayListExtra("playlist");
        current = playlist.size();
        playlist.addAll(added_playlist);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.play(this.current);
    }

    public void play(int i) {
        TV tv = playlist.get(i);
        if (tv != null) {
            this.playVideo(tv);
        }
    }

    public void playVideo(TV tv) {
        String url = null;
        Resource resource = tv.getResource();
        switch (resource.getSource()) {
            case 1:
                url = "http://v.youku.com/v_show/id_" + resource.getId() + ".html";
                break;
        }
        if (url != null) {
            this.videoPlayer.loadUrl(url);
        }
    }
}
