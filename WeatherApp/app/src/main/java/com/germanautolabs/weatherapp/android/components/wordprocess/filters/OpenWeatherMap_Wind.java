package com.germanautolabs.weatherapp.android.components.wordprocess.filters;

import com.germanautolabs.weatherapp.android.components.wordprocess.IWeatherDataFilter;
import com.germanautolabs.weatherapp.android.utils.JSONUtils;
import com.google.gson.internal.LinkedTreeMap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joao on 1/15/2017.
 */

public class OpenWeatherMap_Wind implements IWeatherDataFilter
{
    private static float MS_TO_KMH = 3.6f;

    @Override
    public List<String> processData(String pRawData)
    {
        LinkedTreeMap map = JSONUtils.getLinkedTreeMap(pRawData);
        ArrayList<String> dataList = new ArrayList<>();

        LinkedTreeMap windTree = (LinkedTreeMap) map.get("wind");
        if (windTree != null)
        {
            double speed = (double) windTree.get("speed");
            speed *= MS_TO_KMH;

            DecimalFormat df = new DecimalFormat("0.00");
            String speedStr = df.format(speed);

            dataList.add(speedStr);
        }

        return dataList;
    }
}
