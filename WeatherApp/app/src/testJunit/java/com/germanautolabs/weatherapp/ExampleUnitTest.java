package com.germanautolabs.weatherapp;

import com.germanautolabs.weatherapp.android.components.wordprocess.KeywordSystem;
import com.germanautolabs.weatherapp.android.components.wordprocess.filters.OpenWeatherMap_Description;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    private TestEnvironmentProvider mTestEnvironmentProvider;
    private KeywordSystem mKeywordSystem;

    @Before
    public void init()
    {
        mTestEnvironmentProvider = TestEnvironmentProvider.getInstance();

        mKeywordSystem = new KeywordSystem();
        mKeywordSystem.addFilter("weather", new OpenWeatherMap_Description());
    }

    @Test
    public void filter_isCorrect() throws Exception
    {
        ArrayList<String> wordsRecognized = new ArrayList<>();
        wordsRecognized.add("weather");
        wordsRecognized.add("leather");
        wordsRecognized.add("feather");

        // Mock call to OpenWeatherAPI
        String json = mTestEnvironmentProvider.jsonFromAssetForJunit("weatherMockData.json");
        List<String> resultList = mKeywordSystem.getData(wordsRecognized, json);

        assertEquals(1, resultList.size());
        assertEquals("overcast clouds", resultList.get(0));
    }

    @Test
    public void filter_isNotCorrect() throws Exception
    {
        ArrayList<String> wordsRecognized = new ArrayList<>();
        wordsRecognized.add("star");
        wordsRecognized.add("scar");
        wordsRecognized.add("far");

        // Mock call to OpenWeatherAPI
        String json = mTestEnvironmentProvider.jsonFromAssetForJunit("weatherMockData.json");
        List<String> resultList = mKeywordSystem.getData(wordsRecognized, json);

        assertEquals(0, resultList.size());
    }
}