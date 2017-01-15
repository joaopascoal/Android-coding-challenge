package com.germanautolabs.weatherapp.android.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.germanautolabs.weatherapp.WeatherApp;
import com.germanautolabs.weatherapp.android.activities.MainActivity;
import com.germanautolabs.weatherapp.android.components.connection.IURLConnection;
import com.germanautolabs.weatherapp.android.components.connection.WeatherRequestAsyncTask;
import com.germanautolabs.weatherapp.android.components.location.LocationSystem;
import com.germanautolabs.weatherapp.android.components.voice.DefaultVoiceRecognition;
import com.germanautolabs.weatherapp.android.components.voice.IVoiceRecognition;
import com.germanautolabs.weatherapp.android.components.wordprocess.KeywordSystem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Joao on 1/15/2017.
 */

public class MainService extends Service
{
    @Inject
    EventBus mEventBus;

    @Inject
    IVoiceRecognition mVoiceRecognition;

    @Inject
    LocationSystem mLocationSystem;

    @Inject
    IURLConnection mURLConnection;

    @Inject
    KeywordSystem mKeywordSystem;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        ((WeatherApp) getApplication()).getAppComponent().inject(this);
        this.mEventBus.register(this);

        // Speech
        this.mVoiceRecognition.init(this);

        // Location
        this.mLocationSystem.init(this);
        this.mLocationSystem.start();
    }

    @Override
    public void onDestroy()
    {
        this.mEventBus.unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onMainActivityEvent(MainActivity.Event pEvent)
    {
        switch (pEvent.getType())
        {
            case VOICE_RECOGNITION:
                this.mVoiceRecognition.start();
                break;
        }
    }

    @Subscribe
    public void onVoiceRecognitionSuccess(DefaultVoiceRecognition.Event pEvent)
    {
        List<String> filteredData = this.mKeywordSystem.getData(pEvent.getWordList(), this.mLocationSystem.getWeatherData(), this);
        this.mEventBus.post(new Event(filteredData));
    }

    @Subscribe
    public void onLocationSuccess(LocationSystem.Event pEvent)
    {
        String url = "http://api.openweathermap.org/data/2.5/weather?" + "lat=" + pEvent.getLatitude() + "&" + "lon=" + pEvent.getLongitude() + "&appid=3ef15745554d9e2cefa574ca5d2dd19f";

        this.mURLConnection.clearError();
        WeatherRequestAsyncTask weatherRequestAsyncTask = new WeatherRequestAsyncTask(this.mURLConnection);
        weatherRequestAsyncTask.execute(url);
    }

    @Subscribe
    public void onWeatherDataSuccess(WeatherRequestAsyncTask.Event pEvent)
    {
        this.mLocationSystem.setWeatherData(pEvent.getData());
        this.mEventBus.post(new Event());
    }

    /**
     * Event class
     */
    public enum EventType
    {
        VOICE_RECOGNITION,
        LOCATION_FOUND
    }

    public class Event
    {
        private EventType mType;

        private List<String> mDataList;

        public Event(List<String> pDataList)
        {
            this.mDataList = pDataList;
            this.mType = EventType.VOICE_RECOGNITION;
        }

        public Event()
        {
            this.mType = EventType.LOCATION_FOUND;
        }

        public EventType getType()
        {
            return this.mType;
        }

        public List<String> getDataList()
        {
            return this.mDataList;
        }
    }
}
