package com.germanautolabs.weatherapp;

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
}
