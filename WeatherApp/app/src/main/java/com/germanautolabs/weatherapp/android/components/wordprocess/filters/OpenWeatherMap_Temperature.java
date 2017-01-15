package com.germanautolabs.weatherapp.android.components.wordprocess.filters;

import com.germanautolabs.weatherapp.android.components.wordprocess.IWeatherDataFilter;
import com.germanautolabs.weatherapp.android.utils.JSONUtils;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joao on 1/15/2017.
 */

public class OpenWeatherMap_Temperature implements IWeatherDataFilter
{
    public static double ZERO_KELVIN = 273.15;

    @Override
    public List<String> processData(String pRawData)
    {
        LinkedTreeMap map = JSONUtils.getLinkedTreeMap(pRawData);
        ArrayList<String> dataList = new ArrayList<>();

        LinkedTreeMap mainTree = (LinkedTreeMap) map.get("main");
        if (mainTree != null)
        {
            double value = (double) mainTree.get("temp");
            value -= ZERO_KELVIN;
            int valueInt = (int) Math.round(value);
            String valueStr = valueInt + "";

            dataList.add(valueStr);
        }

        return dataList;
    }
}
