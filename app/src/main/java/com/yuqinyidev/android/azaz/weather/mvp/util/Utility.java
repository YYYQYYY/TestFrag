package com.yuqinyidev.android.azaz.weather.mvp.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yuqinyidev.android.azaz.weather.mvp.model.entity.City;
import com.yuqinyidev.android.azaz.weather.mvp.model.entity.County;
import com.yuqinyidev.android.azaz.weather.mvp.model.entity.Province;
import com.yuqinyidev.android.azaz.weather.mvp.model.entity.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by RDX64 on 2017/6/18.
 */

public class Utility {

    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Nullable
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * csvPath : csv路径，例如 G:\SenimarInfo.csv
     */
//    private void getCSV(String csvPath) {
//        ArrayList<SeminarInfo> dataLists = new ArrayList<SeminarInfo>();
//        ArrayList<String> lists = new ArrayList<String>();
//        SeminarInfo mSeminarInfo = new SeminarInfo();
//        if (dataLists != null) {
//            dataLists.clear();
//        }
//        //_______________________导入到数据库，未成功______________________________
//        mSeminarInfoDB = new SeminarInfoDBHelper(mContext);
//        db = mSeminarInfoDB.getWritableDatabase();
//
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(csvFile));
//            String line = "";
//            int i = 0;
//            while ((line = br.readLine()) != null) {//一次一行，lists.size()=14,28,42...
//                // 把一行数据分割成多个字段
//                StringTokenizer st = new StringTokenizer(line, "|");
//                while (st.hasMoreTokens()) {//一次一个 lists.size()=1
//                    String str = st.nextToken();
//                    lists.add(str);
//                    //    System.out.println("_______________tokens__________________"+str);
//                }
//                System.out.println("_______________size__________________" + lists.size());
//                if (lists.size() > 14) {
//                    ///**ID|EventID|CompanyID|Booth|Date|Time|Hall|RoomNo|PresentCompany|Topic|Speaker|langID|OrderFull|OrderMob
//                    mSeminarInfo.setId(lists.get(14 + i));
//                    mSeminarInfo.setEventID(lists.get(15 + i));
//                    mSeminarInfo.setCompanyID(lists.get(16 + i));
//                    mSeminarInfo.setBooth(lists.get(17 + i));
//                    mSeminarInfo.setDate(lists.get(18 + i));
//                    mSeminarInfo.setTime(lists.get(19 + i));
//                    mSeminarInfo.setHall(lists.get(20 + i));
//                    mSeminarInfo.setRoomNo(lists.get(21 + i));
//                    mSeminarInfo.setPresentCompany(lists.get(22 + i));
//                    mSeminarInfo.setTopic(lists.get(23 + i));
//                    mSeminarInfo.setSpeaker(lists.get(24 + i));
//                    mSeminarInfo.setLangID(lists.get(25 + i));
//                    dataLists.add(mSeminarInfo);
//                    i = i + 14;
//                    System.out.println("dataLists.toString()——————>" + dataLists.toString());
//                    //    System.out.println("_______________i__________________"+i);
//                    //    System.out.println("_______________mSeminarInfo__________________"+mSeminarInfo.getId()+"***"+mSeminarInfo.getEventID()+"***"+mSeminarInfo.getLangID());
//                }
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
