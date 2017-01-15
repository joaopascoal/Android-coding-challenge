package com.germanautolabs.weatherapp;

import android.app.Application;

/**
 * Created by Joao on 1/15/2017.
 */

public class WeatherApp extends Application
{
    private AppComponent mAppComponent;
    private static WeatherApp instance;

    public static WeatherApp getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    public AppComponent getAppComponent()
    {
        return this.mAppComponent;
    }
}