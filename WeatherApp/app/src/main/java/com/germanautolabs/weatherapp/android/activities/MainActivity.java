package com.germanautolabs.weatherapp.android.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.germanautolabs.weatherapp.R;
import com.germanautolabs.weatherapp.WeatherApp;
import com.germanautolabs.weatherapp.android.components.voice.DefaultVoiceRecognition;
import com.germanautolabs.weatherapp.android.components.voice.IVoiceRecognition;

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
    IVoiceRecognition mVoiceRecognition;

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

        this.mVoiceRecognition.init(this);
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
        mVoiceRecognition.start();
    }

    @Subscribe
    public void onVoiceRecognitionSuccess(DefaultVoiceRecognition.Event pEvent)
    {
        String words = "";

        List<String> wordList = pEvent.getWordList();
        for (String word : wordList)
        {
            words += word + " ";
        }

        mHello.setText(words);
    }
}
