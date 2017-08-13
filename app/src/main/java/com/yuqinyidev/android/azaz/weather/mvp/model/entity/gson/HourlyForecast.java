package com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RDX64 on 2017/6/18.
 * <p>
 * hourly_forecast	每小时预报
 * <p>
 * cond	天气状况
 * date	日期
 * hum	相对湿度
 * pop	降水概率
 * pres	气压
 * tmp	温度
 * wind	风力情况
 */
public class HourlyForecast {
    @SerializedName("cond")
    public WeatherCondition condition;
    public String date;
    @SerializedName("hum")
    public String relativeHumidity;
    @SerializedName("pop")
    public String precipitationProbability;
    @SerializedName("pres")
    public String pressure;
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("wind")
    public WindCondition windCondition;

}
