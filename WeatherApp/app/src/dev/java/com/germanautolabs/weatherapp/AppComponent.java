package com.germanautolabs.weatherapp;

import com.germanautolabs.weatherapp.android.activities.MainActivity;
import com.germanautolabs.weatherapp.android.services.MainService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Joao on 1/15/2017.
 */

@Singleton
@Component(modules={AppModule.class})
public interface AppComponent
{
    void inject(MainActivity mainActivity);
    void inject(MainService mainService);
}

