package com.germanautolabs.weatherapp;

import com.germanautolabs.weatherapp.android.components.wordprocess.KeywordSystem;
import com.germanautolabs.weatherapp.android.components.wordprocess.filters.OpenWeatherMap_Description;
import com.germanautolabs.weatherapp.android.components.wordprocess.filters.OpenWeatherMap_Temperature;

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
        mKeywordSystem.addFilter("weather", KeywordSystem.FilterType.WEATHER, new OpenWeatherMap_Description());
        mKeywordSystem.addFilter("temperature", KeywordSystem.FilterType.TEMPERATURE, new OpenWeatherMap_Temperature());
    }

    @Test
    public void filter_weather_isCorrect() throws Exception
    {
        ArrayList<String> wordsRecognized = new ArrayList<>();
        wordsRecognized.add("weather");
        wordsRecognized.add("leather");
        wordsRecognized.add("feather");

        // Mock call to OpenWeatherAPI
        String json = mTestEnvironmentProvider.jsonFromAssetForJunit("weatherMockData.json");
        List<String> resultList = mKeywordSystem.getData(wordsRecognized, json);

        OpenWeatherMap_Description filter = new OpenWeatherMap_Description();
        List<String> filterResults = filter.processData(json);

        assertEquals(1, resultList.size());
        assertEquals(filterResults.get(0), resultList.get(0));
    }

    @Test
    public void filter_temperature_isCorrect() throws Exception
    {
        ArrayList<String> wordsRecognized = new ArrayList<>();
        wordsRecognized.add("forest");
        wordsRecognized.add("pure");
        wordsRecognized.add("temperature");

        // Mock call to OpenWeatherAPI
        String json = mTestEnvironmentProvider.jsonFromAssetForJunit("weatherMockData.json");
        List<String> resultList = mKeywordSystem.getData(wordsRecognized, json);

        OpenWeatherMap_Temperature filter = new OpenWeatherMap_Temperature();
        List<String> filterResults = filter.processData(json);

        assertEquals(1, resultList.size());
        assertEquals(filterResults.get(0), resultList.get(0));
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