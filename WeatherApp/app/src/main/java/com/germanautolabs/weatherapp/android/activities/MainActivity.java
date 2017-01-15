package com.germanautolabs.weatherapp.android.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.germanautolabs.weatherapp.R;
import com.germanautolabs.weatherapp.WeatherApp;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
{

    @Inject
    EventBus mEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((WeatherApp) getApplication()).getAppComponent().inject(this);
    }
}
