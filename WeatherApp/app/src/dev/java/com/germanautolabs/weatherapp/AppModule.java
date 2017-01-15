package com.germanautolabs.weatherapp;

import com.germanautolabs.weatherapp.android.components.connection.DefaultURLConnection;
import com.germanautolabs.weatherapp.android.components.connection.IURLConnection;
import com.germanautolabs.weatherapp.android.components.location.LocationSystem;
import com.germanautolabs.weatherapp.android.components.voice.DefaultVoiceRecognition;
import com.germanautolabs.weatherapp.android.components.voice.IVoiceRecognition;
import com.germanautolabs.weatherapp.android.components.wordprocess.KeywordSystem;
import com.germanautolabs.weatherapp.android.components.wordprocess.filters.OpenWeatherMap_Description;
import com.germanautolabs.weatherapp.android.components.wordprocess.filters.OpenWeatherMap_Temperature;
import com.germanautolabs.weatherapp.android.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Joao on 1/15/2017.
 */

@Module
public class AppModule
{
    @Provides
    @Singleton
    public EventBus provideEventBus()
    {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    public IVoiceRecognition provideVoiceRecognition()
    {
        return new DefaultVoiceRecognition();
    }

    @Provides
    @Singleton
    public LocationSystem provideLocationSystem()
    {
        return new LocationSystem();
    }

    @Provides
    @Singleton
    public IURLConnection provideURLConnection()
    {
        return new DefaultURLConnection();
    }

    @Provides
    @Singleton
    public KeywordSystem provideKeywordSystem()
    {
        KeywordSystem keywordSystem = new KeywordSystem();
        keywordSystem.addFilter(StringUtils.getString(WeatherApp.getAppContext(), R.string.filter_weather), new OpenWeatherMap_Description());
        keywordSystem.addFilter(StringUtils.getString(WeatherApp.getAppContext(), R.string.filter_temperature), new OpenWeatherMap_Temperature());

        return keywordSystem;
    }
}
