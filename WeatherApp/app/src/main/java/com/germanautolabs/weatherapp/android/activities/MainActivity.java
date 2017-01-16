package com.germanautolabs.weatherapp.android.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.germanautolabs.weatherapp.R;
import com.germanautolabs.weatherapp.WeatherApp;
import com.germanautolabs.weatherapp.android.components.location.LocationSystem;
import com.germanautolabs.weatherapp.android.services.MainService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{

    @Inject
    EventBus mEventBus;

    @Inject
    LocationSystem mLocationSystem;

    @BindView(R.id.hello_txt)
    TextView mHello;

    @BindView(R.id.location_txt)
    TextView mLocation;

    @BindView(R.id.requested_info_txt)
    TextView mRequestedInfo;

    @BindView(R.id.voice_btn)
    Button mVoiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((WeatherApp) getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);

        if (this.mLocationSystem.isFindLocation())
        {
            mVoiceBtn.setEnabled(true);
            this.setLocationText();
        }

        Intent serviceIntent = new Intent(this, MainService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.mEventBus.register(this);
    }

    @Override
    protected void onPause()
    {
        this.mEventBus.unregister(this);
        super.onPause();
    }

    @OnClick(R.id.voice_btn)
    public void onVoiceClick(Button button)
    {
        this.mEventBus.post(new Event(EventType.VOICE_RECOGNITION));
    }

    @Subscribe
    public void onServiceEvent(MainService.Event pEvent)
    {
        switch (pEvent.getType())
        {
            case VOICE_RECOGNITION:
                this.processDataByFilter(pEvent);
                break;

            case LOCATION_FOUND:
                this.setLocationText();
                this.setRequestInfoIntro();
                mVoiceBtn.setEnabled(true);
                break;
        }
    }

    private void processDataByFilter(MainService.Event pEvent)
    {
        List<String> dataList = pEvent.getDataList();
        if (pEvent.getFilterType() == null || dataList.size() == 0)
        {
            mRequestedInfo.setText(getBaseContext().getString(R.string.label_data_not_found));
            return;
        }

        String info = dataList.get(0);
        switch (pEvent.getFilterType())
        {
            case WEATHER:
                mRequestedInfo.setText(info);
                break;

            case TEMPERATURE:
                mRequestedInfo.setText(info + "ºC");
                break;

            case WIND:
                mRequestedInfo.setText(info + " km/h");
                break;
        }
    }

    private void setLocationText()
    {
        mLocation.setText(getBaseContext().getString(R.string.label_you_are) + " " + mLocationSystem.getCity() + ", " + mLocationSystem.getCountryName());
    }

    private void setRequestInfoIntro()
    {
        // TODO: Add this string to string.xml and translated them
        mRequestedInfo.setText("Press 'voice' button and ask WeatherApp the data you want");
    }

    /**
     * Event class/enum
     */
    public enum EventType
    {
        VOICE_RECOGNITION
    }

    public class Event
    {
        private EventType mType;

        public Event(EventType pType)
        {
            this.mType = pType;
        }

        public EventType getType()
        {
            return this.mType;
        }
    }
}
