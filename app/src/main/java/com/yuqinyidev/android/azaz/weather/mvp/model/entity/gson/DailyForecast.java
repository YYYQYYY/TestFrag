package com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RDX64 on 2017/6/18.
 * <p>
 * daily_forecast	天气预报
 * <p>
 * astro	天文指数
 * mr	月升时间
 * sr	日出时间
 * ss	日落时间
 * ms	月落时间
 * cond	天气状况
 * code_n	夜间天气状况代码
 * code_d	白天天气状况代码
 * txt_n	夜间天气状况描述
 * txt_d	白天天气状况描述
 * date	日期
 * hum	相对湿度
 * pcpn	降水量
 * pop	降水概率
 * pres	气压
 * tmp	温度
 * max	最高温度
 * min	最低温度
 * uv	紫外线指数
 * vis	能见度
 * wind	风力情况
 */
public class DailyForecast {
    @SerializedName("astro")
    public Astronomy astronomy;
    @SerializedName("cond")
    public WeatherCondition condition;
    public String date;
    @SerializedName("hum")
    public String relativeHumidity;
    @SerializedName("pcpn")
    public String precipitation;
    @SerializedName("pop")
    public String precipitationProbability;
    @SerializedName("pres")
    public String pressure;
    @SerializedName("tmp")
    public Temperature temperature;
    public String uv;
    @SerializedName("vis")
    public String visibility;
    @SerializedName("wind")
    public WindCondition windCondition;

    public class Astronomy {
        @SerializedName("mr")
        public String moonrise;
        @SerializedName("ms")
        public String moonset;
        @SerializedName("sr")
        public String sunrise;
        @SerializedName("ss")
        public String sunset;
    }

    public class WeatherCondition {
        @SerializedName("code_d")
        public String dayCode;
        @SerializedName("code_n")
        public String nightCode;
        @SerializedName("txt_d")
        public String dayInfo;
        @SerializedName("txt_n")
        public String nightInfo;
    }

    public class Temperature {
        public String max;
        public String min;
    }
}
