package com.germanautolabs.weatherapp.android.utils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by Joao on 1/15/2017.
 */

public class JSONUtils
{
    public static LinkedTreeMap getLinkedTreeMap(String pRawData)
    {
        Gson gson = new Gson();
        LinkedTreeMap map = (LinkedTreeMap) gson.fromJson(pRawData, LinkedTreeMap.class);

        return map;
    }
}
