package com.germanautolabs.weatherapp.android.utils;

import android.content.Context;

/**
 * Created by Joao on 1/15/2017.
 */

public class StringUtils
{
    public static String getString(Context pContext, Integer pStringID)
    {
        String string = pContext.getString(pStringID);
        return string;
    }
}
