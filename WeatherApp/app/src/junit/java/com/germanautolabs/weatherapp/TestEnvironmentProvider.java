package com.germanautolabs.weatherapp;

import android.app.Instrumentation;

import com.germanautolabs.weatherapp.android.components.location.LocationSystem;
import com.germanautolabs.weatherapp.android.components.voice.IVoiceRecognition;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;

/**
 * Created by Joao on 1/15/2017.
 */

public class TestEnvironmentProvider
{
    @Inject
    IVoiceRecognition mVoiceRecognition;

    @Inject
    LocationSystem mLocationSystem;

    private static TestEnvironmentProvider instance;

    public TestEnvironmentProvider() {}

    private TestEnvironmentProvider(Instrumentation instrumentation)
    {
        WeatherApp app = (WeatherApp) instrumentation.getTargetContext().getApplicationContext().getApplicationContext();
        app.getAppComponent().inject(this);
    }

    public static TestEnvironmentProvider getInstance()
    {
        if (instance == null)
        {
            instance = new TestEnvironmentProvider();
        }
        return instance;
    }

    public static TestEnvironmentProvider getInstance(Instrumentation instrumentation)
    {
        if (instance == null)
        {
            instance = new TestEnvironmentProvider(instrumentation);
        }
        return instance;
    }

    public void resetMocks()
    {
        reset(mVoiceRecognition);
    }

    public void setup()
    {
        doReturn(true).when(mVoiceRecognition).isMock();
        doReturn(true).when(mLocationSystem).isMock();

        doReturn(true).when(mLocationSystem).isFindLocation();

        doCallRealMethod().when(mVoiceRecognition).start();
        doCallRealMethod().when(mVoiceRecognition).postEvent();
        doCallRealMethod().when(mLocationSystem).start();
        doCallRealMethod().when(mLocationSystem).postEvent();
    }

    public IVoiceRecognition getMockedVoiceRecognition()
    {
        return this.mVoiceRecognition;
    }

    public LocationSystem getMockedLocationSystem()
    {
        return this.mLocationSystem;
    }

    public static String jsonFromAsset(String jsonFileName)
    {
        String json;
        try
        {
            InputStream is = WeatherApp.getInstance().getAssets().open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex)
        {
            throw new RuntimeException();
        }
        return json;
    }

    public String jsonFromAssetForJunit(String jsonFileName)
    {
        String json;
        try
        {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("weatherMockData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex)
        {
            throw new RuntimeException();
        }
        return json;
    }
}
