package com.sicheng.smart_tv.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sicheng.smart_tv.R;
import com.sicheng.smart_tv.launcher.WifiActivity;
import com.sicheng.smart_tv.models.Response;
import com.sicheng.smart_tv.models.User;
import com.sicheng.smart_tv.models.Weather;
import com.sicheng.smart_tv.services.CommonService;
import com.sicheng.smart_tv.services.UserService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link StatusBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatusBarFragment extends Fragment {
    private ImageView avatarImageView;
    private TextView usernameTextView;
    private TextView timeTextView;
    private TextView dateTextView;
    private TextView dayOfWeekTextView;
    private TextView weatherTextView;
    private TextView temperatureTextView;
    private User user = null;
    private long currentTimeMillis = System.currentTimeMillis();
    private Weather weather = null;
    private Timer timer;
    private CommonService.CommonServiceInterface commonService = CommonService.getInstance();
    private UserService.UserServiceInterface userService = UserService.getInstance();


    public StatusBarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StatusBarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatusBarFragment newInstance() {
        StatusBarFragment fragment = new StatusBarFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_bar, container, false);
        this.avatarImageView = view.findViewById(R.id.avatar);
        this.usernameTextView = view.findViewById(R.id.username);
        this.timeTextView = view.findViewById(R.id.time);
        this.dateTextView = view.findViewById(R.id.date);
        this.dayOfWeekTextView = view.findViewById(R.id.day_of_week);
        this.weatherTextView = view.findViewById(R.id.weather);
        this.temperatureTextView = view.findViewById(R.id.temperature);
        return view;
    }

    public void render() {
        if (this.user != null) {
            this.usernameTextView.setText(this.user.getUsername());
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(this.user.getAvatar(), this.avatarImageView);
        }
        this.renderDatetime();
        if (this.weather != null) {
            this.weatherTextView.setText(this.weather.getWeather());
            this.temperatureTextView.setText(this.weather.getTemperature() + "℃");
        }
    }

    public void renderDatetime() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.currentTimeMillis);
        String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
        this.timeTextView.setText(new SimpleDateFormat("HH:mm").format(cal.getTime()));
        this.dateTextView.setText(new SimpleDateFormat("MM-dd").format(cal.getTime()));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        this.dayOfWeekTextView.setText(weeks[week_index]);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.setIntervalUpdateTime();
        this.getWeather();
        this.getUser();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.timer.cancel();
    }

    private void setIntervalUpdateTime() {
        this.timer = new Timer();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                currentTimeMillis = System.currentTimeMillis();
                renderDatetime();
            }
        };
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 0, 1000);
    }

    protected void getUser() {
        Call<Response<User>> call = this.userService.status();
        call.enqueue(new Callback<Response<User>>() {
            @Override
            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                Response<User> resp = response.body();
                User user = resp.getResult();
                handleUserLoaded(user);
            }

            @Override
            public void onFailure(Call<Response<User>> call, Throwable t) {
                Log.e("API:USER_STATUS", call.request().url() + ": failed: " + t);
            }
        });
    }

    protected void handleUserLoaded(User user) {
        if (user != null) {
            this.user = user;
            this.render();
        } else {
            this.redirectToLoginActivity();
        }
    }

    /**
     * 重定向到login页面进行用户登录
     */
    private void redirectToLoginActivity() {
        if (!this.getActivity().getClass().equals(WifiActivity.class)) {
            Intent intent = new Intent(getContext(), WifiActivity.class);
            intent.putExtra("redirect_to", this.getActivity().getClass().getName());
            startActivity(intent);
        }
    }

    public void getWeather() {
        Call<Response<Weather>> call = this.commonService.weather();
        call.enqueue(new Callback<Response<Weather>>() {
            @Override
            public void onResponse(Call<Response<Weather>> call, retrofit2.Response<Response<Weather>> response) {
                Response<Weather> resp = response.body();
                weather = resp.getResult();
                render();
            }

            @Override
            public void onFailure(Call<Response<Weather>> call, Throwable t) {
                Log.e("API:COMMON_WEATHER", call.request().url() + ": failed: " + t);
            }
        });
    }

    public void syncServerDatetime() {
        Call<Response<Long>> call = this.commonService.now();
        call.enqueue(new Callback<Response<Long>>() {
            @Override
            public void onResponse(Call<Response<Long>> call, retrofit2.Response<Response<Long>> response) {
                Response<Long> resp = response.body();
                currentTimeMillis = resp.getResult();
                render();
            }

            @Override
            public void onFailure(Call<Response<Long>> call, Throwable t) {
                Log.e("API:COMMON_NOW", call.request().url() + ": failed: " + t);
            }
        });
    }
}
