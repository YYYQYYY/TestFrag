package com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RDX64 on 2017/6/18.
 * <p>
 * basic	基本信息
 * <p>
 * id	城市编号
 * city	城市名
 * utc	UTC时间
 * loc	当地时间
 * lon	经度
 * lat	纬度
 * cnty	国家
 */
public class Basic {
    @SerializedName("cnty")
    public String countryName;
    public String lat;
    public String lon;

    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Update update;

    public class Update {
        @SerializedName("utc")
        public String utcUpdateTime;
        @SerializedName("loc")
        public String updateTime;
    }
}
