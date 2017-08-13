package com.yuqinyidev.android.azaz.weather.mvp.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yuqinyidev.android.azaz.R;
import com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson.Forecast;
import com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson.Weather;
import com.yuqinyidev.android.azaz.weather.mvp.util.HttpUtil;
import com.yuqinyidev.android.azaz.weather.mvp.util.Utility;
import com.yuqinyidev.android.framework.utils.FileUtils;
import com.yuqinyidev.android.framework.utils.StringUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherDetailFragment extends Fragment {
    public static final String ARG_CONTENT = "content";
    public static final String ARG_PAGE = "page_num";

    private static final String SPLASH_BG_NAME = "splash_bg.jpg";
    private static final String SP_KEY_CITY_ID = "sp_key_city_id";

    private String mCityId = null;

    public DrawerLayout drawerLayout;
    public SwipeRefreshLayout swipeRefresh;

    private Button navButton;
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private ImageView mBingPicImg;

    private TextView tv;

//    private int currentPageNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mCityId = bundle.getString(ARG_CONTENT);
//            currentPageNum = bundle.getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_weather_detail, container, false);
        tv = view.findViewById(R.id.tv);

        initController(view);

        initData();

        return view;
    }

    public void initData() {
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String weatherString = prefs.getString(SP_KEY_CITY_ID.concat(mCityId), null);
        if (StringUtils.isEmpty(weatherString)) {
            requestWeather(mCityId);
        } else {
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
//            tv.setText(weatherString);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mCityId);
            }
        });
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        displayBackground();
    }

    public static WeatherDetailFragment newInstance(String cityId) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_CONTENT, cityId);
//        bundle.putInt(ARG_PAGE, pagerNum);
        WeatherDetailFragment weatherDetailFragment = new WeatherDetailFragment();
        weatherDetailFragment.setArguments(bundle);
        return weatherDetailFragment;
    }

    public void requestWeather(final String cityId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" +
                cityId + "&key=f69c2ab4c3604c94b436bb0ae672560a";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                            editor.putString(SP_KEY_CITY_ID.concat(cityId), responseText);
                            editor.apply();
                            tv.setText(responseText);
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(getActivity(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void showWeatherInfo(Weather weather) {
        mCityId = weather.basic.weatherId;
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_weather_forecast_item, forecastLayout, false);
            TextView dateText = view.findViewById(R.id.date_text);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView maxText = view.findViewById(R.id.max_text);
            TextView minText = view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度： " + weather.suggestion.comfort.info;
        String carWash = "洗车指数： " + weather.suggestion.carWash.info;
        String sport = "运动建议： " + weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
//TODO:启动服务处理需要修正
//        Intent intent = new Intent(getActivity(), AutoUpdateWeatherService.class);
//        startService(intent);
    }

    private void initController(View view) {
        drawerLayout = view.findViewById(R.id.drawer_layout);
        navButton = view.findViewById(R.id.nav_button);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        weatherLayout = view.findViewById(R.id.weather_layout);
        titleCity = view.findViewById(R.id.title_city);
        titleUpdateTime = view.findViewById(R.id.title_update_time);
        degreeText = view.findViewById(R.id.degree_text);
        weatherInfoText = view.findViewById(R.id.weather_info_text);
        forecastLayout = view.findViewById(R.id.forecast_layout);
        aqiText = view.findViewById(R.id.aqi_text);
        pm25Text = view.findViewById(R.id.pm25_text);
        comfortText = view.findViewById(R.id.comfort_text);
        carWashText = view.findViewById(R.id.car_wash_text);
        sportText = view.findViewById(R.id.sport_text);
        mBingPicImg = view.findViewById(R.id.bing_pic_img);
    }

    private void displayBackground() {
//        SPLASH_BG_NAME
        File f = new File(FileUtils.packageName2CachePath(getActivity().getPackageName()), SPLASH_BG_NAME);
        if (f.exists()) {
            Glide.with(getActivity())
                    .load(f)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(mBingPicImg);
        } else {
            Glide.with(getActivity())
                    .load(R.drawable.applegray)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(mBingPicImg);
//        } else {
//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//            String bingPic = prefs.getString("bing_pic", null);
//            if (bingPic != null) {
//                Glide.with(this).load(bingPic).into(mBingPicImg);
//            } else {
//                loadBingPic();
//            }
        }
    }

}
