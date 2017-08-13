package com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by RDX64 on 2017/6/18.
 * <p>
 * 字段	说明
 * HeWeather5	和风标识
 * <p>
 * basic	基本信息
 * aqi	AQI
 * daily_forecast	天气预报
 * hourly_forecast	每小时预报
 * now	实况天气
 * status	状态码
 * suggestion	生活指数
 */
public class Weather {
    public Basic basic;
    public AQI aqi;
    @SerializedName("daily_forecast")
    public List<DailyForecast> dailyForecastList;
    @SerializedName("hourly_forecast")
    public List<HourlyForecast> hourlyForecastList;
    public Now now;
    public String status;
    public Suggestion suggestion;
}
