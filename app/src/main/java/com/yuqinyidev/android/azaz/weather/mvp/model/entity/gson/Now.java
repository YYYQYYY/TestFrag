package com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RDX64 on 2017/6/18.
 * <p>
 * now	实况天气
 * <p>
 * cond	天气状况
 * fl	体感温度
 * hum	相对湿度
 * pcpn	降水量
 * pres	气压
 * tmp	温度
 * vis	能见度
 * wind	风力情况
 */
public class Now {
    @SerializedName("cond")
    public WeatherCondition condition;
    @SerializedName("fl")
    public String faceTemperature;
    @SerializedName("hum")
    public String relativeHumidity;
    @SerializedName("pcpn")
    public String precipitation;
    @SerializedName("pres")
    public String pressure;
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("vis")
    public String visibility;
    @SerializedName("wind")
    public WindCondition windCondition;
}
