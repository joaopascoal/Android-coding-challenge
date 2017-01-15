package com.germanautolabs.weatherapp;

import android.app.Instrumentation;

import com.germanautolabs.weatherapp.android.components.voice.IVoiceRecognition;

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

    private static TestEnvironmentProvider instance;

    private TestEnvironmentProvider(Instrumentation instrumentation)
    {
        WeatherApp app = (WeatherApp) instrumentation.getTargetContext().getApplicationContext().getApplicationContext();
        app.getAppComponent().inject(this);
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
        doCallRealMethod().when(mVoiceRecognition).start();
        doCallRealMethod().when(mVoiceRecognition).postEvent();
    }

    public IVoiceRecognition getMockedVoiceRecognition()
    {
        return this.mVoiceRecognition;
    }
}
