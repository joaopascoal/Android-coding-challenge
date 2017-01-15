package com.germanautolabs.weatherapp.android.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.germanautolabs.weatherapp.R;
import com.germanautolabs.weatherapp.WeatherApp;
import com.germanautolabs.weatherapp.android.components.voice.DefaultVoiceRecognition;
import com.germanautolabs.weatherapp.android.components.voice.IVoiceRecognition;
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

    @BindView(R.id.hello_txt)
    TextView mHello;

    @BindView(R.id.voice_btn)
    Button mVoiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((WeatherApp) getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);

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
                List<String> wordList = pEvent.getWordList();
                String result = "";
                for (String word : wordList)
                {
                    result += word + " ";
                }

                mHello.setText(result);
                break;
        }
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
