package com.germanautolabs.weatherapp.android.components.connection;

/**
 * Created by Joao on 1/15/2017.
 */

public interface IURLConnection
{
    String getResult(String urlString);
    void clearError();
    int getError();
}
