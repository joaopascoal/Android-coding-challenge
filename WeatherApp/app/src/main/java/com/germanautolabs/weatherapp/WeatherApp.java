package com.germanautolabs.weatherapp;

import android.app.Application;
import android.content.Context;

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

    public static Context getAppContext()
    {
        return instance.getApplicationContext();
    }

    public AppComponent getAppComponent()
    {
        return this.mAppComponent;
    }
}