package com.germanautolabs.weatherapp;

import com.germanautolabs.weatherapp.android.components.connection.DefaultURLConnection;
import com.germanautolabs.weatherapp.android.components.connection.IURLConnection;
import com.germanautolabs.weatherapp.android.components.location.LocationSystem;
import com.germanautolabs.weatherapp.android.components.voice.DefaultVoiceRecognition;
import com.germanautolabs.weatherapp.android.components.voice.IVoiceRecognition;

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
}
