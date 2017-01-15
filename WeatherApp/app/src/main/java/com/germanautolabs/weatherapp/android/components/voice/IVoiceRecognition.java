package com.germanautolabs.weatherapp.android.components.voice;

/**
 * Created by Joao on 1/15/2017.
 */

public interface IVoiceRecognition
{
    void init(Object... params);
    void start();
    void stop();
    void postEvent();
}
