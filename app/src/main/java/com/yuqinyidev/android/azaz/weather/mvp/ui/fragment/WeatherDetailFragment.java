package com.yuqinyidev.android.azaz.weather.mvp.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yuqinyidev.android.azaz.R;
import com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson.Weather;
import com.yuqinyidev.android.azaz.weather.mvp.ui.activity.MainTActivity;
import com.yuqinyidev.android.azaz.weather.mvp.util.HttpUtil;
import com.yuqinyidev.android.azaz.weather.mvp.util.Utility;
import com.yuqinyidev.android.framework.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherDetailFragment extends Fragment {
    public static final String ARG_CONTENT = "content";
    public static final String ARG_PAGE = "page_num";

    private static final String SP_KEY_CITY_ID = "sp_key_city_id";

    private String mCityId = null;


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
        initView(view);
        return view;
    }

    public void initView(View view) {
        tv = view.findViewById(R.id.tv);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String weatherString = prefs.getString(SP_KEY_CITY_ID.concat(mCityId), null);
        if (StringUtils.isEmpty(weatherString)) {
            requestWeather(mCityId);
        } else {
            tv.setText(weatherString);
        }
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
                        } else {
                            Toast.makeText(getActivity(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
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
                    }
                });
            }
        });
    }

}
