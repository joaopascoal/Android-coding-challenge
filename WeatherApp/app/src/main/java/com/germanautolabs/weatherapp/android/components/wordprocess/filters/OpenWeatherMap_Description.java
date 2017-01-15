package com.germanautolabs.weatherapp.android.components.wordprocess.filters;

import com.germanautolabs.weatherapp.android.components.wordprocess.IWeatherDataFilter;
import com.germanautolabs.weatherapp.android.utils.JSONUtils;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Joao on 1/15/2017.
 */

public class OpenWeatherMap_Description implements IWeatherDataFilter
{

    public OpenWeatherMap_Description() {}

    @Override
    public List<String> processData(String pRawData)
    {
        LinkedTreeMap map = JSONUtils.getLinkedTreeMap(pRawData);
        ArrayList<String> dataList = new ArrayList<>();

        ArrayList weatherMap = (ArrayList) map.get("weather");
        if (weatherMap != null && weatherMap.size() == 1)
        {
            LinkedTreeMap linkedTree = (LinkedTreeMap) weatherMap.get(0);
            String value = (String) linkedTree.get("description");
            dataList.add(value);
        }

        return dataList;
    }
}
