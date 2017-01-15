package com.germanautolabs.weatherapp.android.components.connection;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Joao on 1/15/2017.
 */

public class WeatherRequestAsyncTask extends AsyncTask<String, Void, String>
{
    private IURLConnection mURLConnection;

    public WeatherRequestAsyncTask(IURLConnection pURLConnection)
    {
        this.setURLConnection(pURLConnection);
    }

    public void setURLConnection(IURLConnection pURLConnection)
    {
        this.mURLConnection = pURLConnection;
    }

    @Override
    protected String doInBackground(String... params)
    {
        String url = params[0];
        String result = this.mURLConnection.getResult(url);
        return result;
    }

    @Override
    protected void onPostExecute(String data)
    {
        EventBus.getDefault().post(new Event(data));
    }

    /**
     * Event
     */
    public class Event
    {
        private String mData;

        public Event(String pData)
        {
            this.mData = pData;
        }

        public String getData()
        {
            return this.mData;
        }
    }
}
