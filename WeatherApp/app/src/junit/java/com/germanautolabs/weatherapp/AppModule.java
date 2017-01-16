package com.germanautolabs.weatherapp;

import com.germanautolabs.weatherapp.android.components.connection.DefaultURLConnection;
import com.germanautolabs.weatherapp.android.components.connection.IURLConnection;
import com.germanautolabs.weatherapp.android.components.location.LocationSystem;
import com.germanautolabs.weatherapp.android.components.voice.DefaultVoiceRecognition;
import com.germanautolabs.weatherapp.android.components.voice.IVoiceRecognition;
import com.germanautolabs.weatherapp.android.components.wordprocess.KeywordSystem;
import com.germanautolabs.weatherapp.android.components.wordprocess.filters.OpenWeatherMap_Description;
import com.germanautolabs.weatherapp.android.components.wordprocess.filters.OpenWeatherMap_Temperature;
import com.germanautolabs.weatherapp.android.components.wordprocess.filters.OpenWeatherMap_Wind;
import com.germanautolabs.weatherapp.android.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.mockito.Mockito;

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
        return Mockito.spy(EventBus.getDefault());
    }

    @Provides
    @Singleton
    public IVoiceRecognition provideVoiceRecognition()
    {
        return Mockito.mock(DefaultVoiceRecognition.class);
    }

    @Provides
    @Singleton
    public LocationSystem provideLocationSystem()
    {
        return Mockito.mock(LocationSystem.class);
    }

    @Provides
    @Singleton
    public IURLConnection provideURLConnection()
    {
        return Mockito.mock(DefaultURLConnection.class);
    }

    @Provides
    @Singleton
    public KeywordSystem provideKeywordSystem()
    {
        KeywordSystem keywordSystem = new KeywordSystem();
        keywordSystem.addFilter(StringUtils.getString(WeatherApp.getAppContext(), R.string.filter_weather), KeywordSystem.FilterType.WEATHER, new OpenWeatherMap_Description());
        keywordSystem.addFilter(StringUtils.getString(WeatherApp.getAppContext(), R.string.filter_temperature), KeywordSystem.FilterType.TEMPERATURE, new OpenWeatherMap_Temperature());
        keywordSystem.addFilter(StringUtils.getString(WeatherApp.getAppContext(), R.string.filter_wind), KeywordSystem.FilterType.WIND, new OpenWeatherMap_Wind());

        return keywordSystem;
    }
}
