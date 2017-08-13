package com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RDX64 on 2017/6/18.
 * <p>
 * wind	风力情况
 * <p>
 * deg	风向（360度）
 * sc	风力等级
 * spd	风速
 * dir	风向
 */
public class WindCondition {
    @SerializedName("deg")
    public String direction360;
    @SerializedName("dir")
    public String direction;
    @SerializedName("sc")
    public String scale;
    @SerializedName("spd")
    public String speed;
}
