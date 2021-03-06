package com.germanautolabs.weatherapp.android.components.voice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joao on 1/15/2017.
 */

public class DefaultVoiceRecognition implements IVoiceRecognition, RecognitionListener
{
    private SpeechRecognizer mSpeech;
    private Intent mRecognizerIntent;
    private List<String> mWordsSpoken;

    private static String TAG = "[VoiceRecognition]";

    public DefaultVoiceRecognition()
    {
        this.mWordsSpoken = new ArrayList<>();
    }

    public DefaultVoiceRecognition(Context context)
    {
        this();
        this.init(context);
    }

    public List<String> getWordsSpoken()
    {
        return this.mWordsSpoken;
    }

    /**
     * Methods from IVoiceRecognition
     */
    @Override
    public void init(Object... params)
    {
        Context context;
        if (params != null && params.length > 0)
            context = (Context) params[0];
        else
            return;

        this.mWordsSpoken.clear();
        this.mSpeech = SpeechRecognizer.createSpeechRecognizer(context);
        this.mSpeech.setRecognitionListener(this);
        this.mRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        this.mRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        this.mRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
        this.mRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
    }

    @Override
    public void start()
    {
        if (!isMock())
        {
            // TODO: Error treatment if the statement below is false
            if (this.mSpeech != null && this.mRecognizerIntent != null)
                this.mSpeech.startListening(this.mRecognizerIntent);
        }
        else
        {
            this.postEvent();
        }
    }

    @Override
    public void stop()
    {
        this.mSpeech.stopListening();
    }

    @Override
    public void postEvent()
    {
        EventBus.getDefault().post(new Event(this.getWordsSpoken()));
    }

    @Override
    public boolean isMock()
    {
        return false;
    }

    /**
     * Methods from RecognitionListener
     */
    @Override
    public void onReadyForSpeech(Bundle params)
    {

    }

    @Override
    public void onBeginningOfSpeech()
    {

    }

    @Override
    public void onRmsChanged(float rmsdB)
    {

    }

    @Override
    public void onBufferReceived(byte[] buffer)
    {

    }

    @Override
    public void onEndOfSpeech()
    {

    }

    @Override
    public void onError(int error)
    {
        // TODO: Post event to dispatch error to activity
        this.stop();
        this.mWordsSpoken.clear();
        EventBus.getDefault().post(new Event(error));
    }

    @Override
    public void onResults(Bundle results)
    {
        this.mWordsSpoken.clear();
        ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        for (int i = 0; i < data.size(); i++)
        {
            String word = (String) data.get(i);
            Log.d(TAG, "result " + word);
            this.mWordsSpoken.add(word);
        }

        // TODO: Add else clause to dispatch an error informing that no words were found
        if (this.mWordsSpoken.size() > 0)
        {
            postEvent();
        }

    }

    @Override
    public void onPartialResults(Bundle partialResults)
    {

    }

    @Override
    public void onEvent(int eventType, Bundle params)
    {

    }

    public class Event
    {
        private List<String> mWordList;
        private int mError;

        public Event(List<String> pList)
        {
            this.mWordList = pList;
            this.mError = 0;
        }

        public Event(int pError)
        {
            this.mError = pError;
        }

        public int getErrorCode()
        {
            return this.mError;
        }

        public List<String> getWordList()
        {
            return this.mWordList;
        }
    }
}
