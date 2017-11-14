package com.sicheng.smart_tv.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.models.Video;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class VideoPlayerActivity extends Activity {
    private WebView videoPlayer;
    private ArrayList<Video> playlist = new ArrayList<>();
    private int current;

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

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String host = request.getUrl().getHost();
                Log.i("url", request.getUrl().toString());
                if (host != null) {
                    switch (host) {
                        case "k.youku.com":
                        case "valp.atm.youku.com":
                        case "valb.atm.youku.com":
                        case "valf.atm.youku.com":
                        case "valc.atm.youku.com":
                        case "valo.atm.youku.com":
                        case "val.atm.youku.com":
                        case "atm.youku.com":
                            String result = "";
                            WebResourceResponse webResourceResponse = new WebResourceResponse("text/html", "UTF-8", new ByteArrayInputStream(result.getBytes()));
                            return webResourceResponse;
                    }
                }
                return super.shouldInterceptRequest(view, request);
            }

        });
        this.videoPlayer.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                String url = view.getUrl();
                Log.i("finish", url);
                Video video = playlist.get(current);
                switch (video.getSource()) {
                    case 1:
                        String css = "#module_basic_title, #qheader {display: none !important;} #playBox {z-index: 99999 !important; margin: 0 !important;} #playerBox {z-index: 100000 !important;} .youku-film-player,.preplay-layer {background-color: #fff !important;} body, #playBox, #ykPlayer {position: fixed !important; top: 0; left: 0; right: 0; bottom: 0; overflow: hidden;}";
                        view.loadUrl("javascript:(function() {" +
                                "var parent = document.getElementsByTagName('head').item(0);" +
                                "var style = document.createElement('style');" +
                                "style.type = 'text/css';" +
                                "style.innerHTML = '" + css + "';" +
                                "parent.appendChild(style);" +
                                "})()");
                        break;
                }
            }
        });
        WebSettings webSettings = this.videoPlayer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMixedContentMode(webSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
        Intent intent = getIntent();
        ArrayList<Video> added_playlist = intent.getParcelableArrayListExtra("playlist");
        current = playlist.size();
        playlist.addAll(added_playlist);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.play(this.current);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.videoPlayer.loadData("", "text/html; charset=UTF-8", null);
        this.videoPlayer.destroy();
    }

    public void play(int i) {
        if (i < playlist.size()) {
            Video video = playlist.get(i);
            this.playVideo(video);
        }
    }

    public void playVideo(Video video) {
        String url = null;
        WebSettings webSettings = this.videoPlayer.getSettings();
        switch (video.getSource()) {
            case 1:
                webSettings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
                url = "http://v.youku.com/v_show/id_" + video.getVideo_id() + ".html";
                break;
            case 2:
                webSettings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
                url = "https://v.qq.com/x/cover/" + video.getId() + "/" + video.getVideo_id() + ".html";
                break;
            case 3:
                webSettings.setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/6");
                url = "http://m.iqiyi.com/" + video.getVideo_id() + ".html";
                break;
        }
        if (url != null) {
            this.videoPlayer.loadUrl(url);
        }
    }
}
