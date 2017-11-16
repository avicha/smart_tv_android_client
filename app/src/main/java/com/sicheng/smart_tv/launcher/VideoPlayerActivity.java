package com.sicheng.smart_tv.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.models.Response;
import com.sicheng.smart_tv.models.Video;
import com.sicheng.smart_tv.models.VideoPlayInfo;
import com.sicheng.smart_tv.services.VideoService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class VideoPlayerActivity extends BaseActivity {

    private SurfaceView surfaceView;

    private AliVcMediaPlayer player;

    private ArrayList<Video> playlist = new ArrayList<>();

    private int current;

    private VideoService.VideoServiceInterface videoService = VideoService.getInstance();

    private VideoPlayInfo videoPlayInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        String businessId = "smart_tv";
        AliVcMediaPlayer.init(getApplicationContext(), businessId);
        surfaceView = this.findViewById(R.id.video_player);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                holder.setKeepScreenOn(true);
                // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
                if (player != null) {
                    player.setVideoSurface(holder.getSurface());
                }

            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                if (player != null) {
                    player.setSurfaceChanged();
                }
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                if (player != null) {
                    player.releaseVideoSurface();
                }
            }
        });
        player = new AliVcMediaPlayer(this, surfaceView);
        player.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        player.setDefaultDecoder(0);
        player.enableNativeLog();

        player.setPreparedListener(new MediaPlayer.MediaPlayerPreparedListener() {
            @Override
            public void onPrepared() {
                toast.setText("准备就绪");
                toast.show();
            }
        });
        player.setErrorListener(new MediaPlayer.MediaPlayerErrorListener() {
            @Override
            public void onError(int i, String msg) {
                player.stop();
                toast.setText("播放出现错误：" + msg);
                toast.show();
            }
        });
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

    public void play(int i) {
        if (i < playlist.size()) {
            Video video = playlist.get(i);
            this.playVideo(video);
        }
    }

    public void playVideo(Video video) {
        Call<Response<VideoPlayInfo>> call = videoService.get_play_info(video.getVideo_id(), video.getSource());
        call.enqueue(new Callback<Response<VideoPlayInfo>>() {
            @Override
            public void onResponse(Call<Response<VideoPlayInfo>> call, retrofit2.Response<Response<VideoPlayInfo>> response) {
                Response<VideoPlayInfo> resp = response.body();
                videoPlayInfo = resp.getResult();
                VideoPlayInfo.VideoType videoType = videoPlayInfo.getDefaultPlayVideoType();
                if (videoType != null) {
                    player.prepareAndPlay(videoType.getUrl());
                }

            }

            @Override
            public void onFailure(Call<Response<VideoPlayInfo>> call, Throwable t) {
                Log.e("API:VIDEO_PLAY", call.request().url() + ": failed: " + t);
            }
        });
    }


    //播放
    private void play() {
        if (player != null) {
            player.prepareAndPlay("https://pl-ali.youku.com/playlist/m3u8?vid=XMjg4MTUwNjE0OA%3D%3D&type=mp4hd2v3&ups_client_netip=78ec94a9&ups_ts=1510802221&utid=HLwAEYVFXmECAXFcg5HlXZrB&ccode=0501&psid=08109470d7a808f64ef2b3f482b74a11&duration=2668&expire=18000&ups_key=58fc01d416261b88f97649196250dfec"
            );
        }
    }

    //暂停播放
    private void pause() {
        if (player != null) {
            player.pause();

        }
    }

    //继续播放
    private void resume() {
        if (player != null) {
            player.play();
        }
    }

    //停止播放
    private void stop() {
        if (player != null) {
            player.stop();
        }
    }

    //重头播放
    private void replay() {
        stop();
        play();
    }

    //保留播放状态
    private void savePlayerState() {
        if (player.isPlaying()) {
            // 不可见，暂停播放器
            pause();
        }
    }

    //销毁画面时释放面板，暂停并摧毁播放器
    private void destroy() {
        if (player != null) {
            player.releaseVideoSurface();
            player.stop();
            player.destroy();
        }
    }

    //退出界面时暂停播放
    @Override
    protected void onStop() {
        super.onStop();
        savePlayerState();
    }

    @Override
    protected void onDestroy() {
        destroy();
        super.onDestroy();
    }
}
