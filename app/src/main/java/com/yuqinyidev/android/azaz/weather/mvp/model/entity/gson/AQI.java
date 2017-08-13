package com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RDX64 on 2017/6/18.
 * <p>
 * aqi	AQI
 * <p>
 * city	城市名
 * pm10	PM10
 * pm25	PM2.5
 * co	CO
 * no2	NO2
 * o3	O3
 * qlty	空气质量
 * so2	SO2
 */
public class AQI {
    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm10;
        public String pm25;
        public String co;
        public String no2;
        public String o3;
        @SerializedName("qlty")
        public String airQuality;
        public String so2;
    }
}
