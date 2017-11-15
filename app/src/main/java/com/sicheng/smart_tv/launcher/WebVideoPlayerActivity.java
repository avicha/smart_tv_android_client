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

public class WebVideoPlayerActivity extends Activity {
    private WebView videoPlayer;
    private ArrayList<Video> playlist = new ArrayList<>();
    private int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_video_player);
        this.videoPlayer = findViewById(R.id.web_video_player);
        this.videoPlayer.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //使用webview来显示
                return false;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String host = request.getUrl().getHost();
                Log.i("url", request.getUrl().toString());
                if (host != null) {
                    //去广告的关键！
                    switch (host) {
                        case "k.youku.com":
                        case "valp.atm.youku.com":
                        case "valb.atm.youku.com":
                        case "valf.atm.youku.com":
                        case "valc.atm.youku.com":
                        case "valo.atm.youku.com":
                        case "val.atm.youku.com":
                        case "atm.youku.com":
                        case "s19.cnzz.com":
                        case "tl.zcsfs.cn":
                        case "js.kfkfl.cn":
                            return makeStringResponse("");
                    }
                }
                return super.shouldInterceptRequest(view, request);
            }

        });
        this.videoPlayer.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //往当前网页注入脚本，改变默认行为
                super.onReceivedTitle(view, title);
                String url = view.getUrl();
                Log.i("page", url);
                Video video = playlist.get(current);
                switch (video.getSource()) {
                    case 3:
                        String js = "var tick = window.setInterval(function(){var video = document.querySelector(\"video\");if(video){window.clearInterval(tick);video.poster = \"\"; video.play();}},10);";
                        view.loadUrl("javascript:" + js);
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
        //获取播放列表
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
    }

    protected WebResourceResponse makeStringResponse(String result) {
        WebResourceResponse webResourceResponse = new WebResourceResponse("text/html", "UTF-8", new ByteArrayInputStream(result.getBytes()));
        return webResourceResponse;
    }

    public void play(int i) {
        if (i < playlist.size()) {
            Video video = playlist.get(i);
            this.playVideo(video);
        }
    }

    public void next() {
        if (current + 1 < playlist.size()) {
            current++;
            this.play(this.current);
        }
    }

    public void record() {

    }

    public void playVideo(Video video) {
        String url = null;
        WebSettings webSettings = this.videoPlayer.getSettings();
        switch (video.getSource()) {
            case 1:
                webSettings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
                url = "http://player.youku.com/embed/" + video.getVideo_id();
                break;
            case 2:
                webSettings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
                url = "http://jx.maoyun.tv/index.php?id=https://v.qq.com/x/cover/" + video.getId() + "/" + video.getVideo_id() + ".html";
                break;
            case 3:
                webSettings.setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/6");
                url = "http://jx.maoyun.tv/index.php?id=http://m.iqiyi.com/" + video.getVideo_id() + ".html";
                break;
        }
        if (url != null) {
            this.videoPlayer.loadUrl(url);
        }
    }
}
