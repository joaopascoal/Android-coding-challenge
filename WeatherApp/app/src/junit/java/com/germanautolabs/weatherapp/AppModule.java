package com.germanautolabs.weatherapp;

import com.germanautolabs.weatherapp.android.components.connection.DefaultURLConnection;
import com.germanautolabs.weatherapp.android.components.connection.IURLConnection;
import com.germanautolabs.weatherapp.android.components.location.LocationSystem;
import com.germanautolabs.weatherapp.android.components.voice.DefaultVoiceRecognition;
import com.germanautolabs.weatherapp.android.components.voice.IVoiceRecognition;

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
}
