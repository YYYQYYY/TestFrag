package com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RDX64 on 2017/6/18.
 * <p>
 * cond	天气状况
 * <p>
 * code	天气状况代码
 * txt	数据详情
 */
public class WeatherCondition {
    public String code;
    @SerializedName("txt")
    public String detailInfo;
}
