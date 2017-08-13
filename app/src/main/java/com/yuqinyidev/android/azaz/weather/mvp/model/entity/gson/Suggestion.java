package com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RDX64 on 2017/6/18.
 * <p>
 * suggestion	生活指数
 * <p>
 * air	空气指数
 * brf	简介
 * txt	数据详情
 * comf	舒适度指数
 * cw	洗车指数
 * drsg	穿衣指数
 * flu	感冒指数
 * sport	运动指数
 * trav	旅游指数
 * uv	紫外线指数
 */
public class Suggestion {
    public SuggestionData air;
    @SerializedName("comf")
    public SuggestionData comfort;
    @SerializedName("cw")
    public SuggestionData carWash;
    @SerializedName("drsg")
    public SuggestionData dressing;
    @SerializedName("flu")
    public SuggestionData influenza;
    public SuggestionData sport;
    @SerializedName("trav")
    public SuggestionData travel;
    public SuggestionData uv;

    public class SuggestionData {
        @SerializedName("brf")
        public String briefIntroduction;
        @SerializedName("txt")
        public String detailInfo;
    }
}
