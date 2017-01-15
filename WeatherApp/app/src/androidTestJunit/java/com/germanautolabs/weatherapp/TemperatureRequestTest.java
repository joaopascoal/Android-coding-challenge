package com.germanautolabs.weatherapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.germanautolabs.weatherapp.android.activities.MainActivity;
import com.germanautolabs.weatherapp.android.components.location.LocationSystem;
import com.germanautolabs.weatherapp.android.components.voice.DefaultVoiceRecognition;
import com.germanautolabs.weatherapp.android.components.wordprocess.filters.OpenWeatherMap_Temperature;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Joao on 1/15/2017.
 */

public class TemperatureRequestTest extends ActivityInstrumentationTestCase2<MainActivity>
{
    private TestEnvironmentProvider mTestEnvironmentProvider;

    public TemperatureRequestTest()
    {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception
    {
        mTestEnvironmentProvider = TestEnvironmentProvider.getInstance(getInstrumentation());
        mTestEnvironmentProvider.resetMocks();
        mTestEnvironmentProvider.setup();

        super.setUp();
        getActivity();
    }

    @SmallTest
    public void testTemperatureRequest() throws Exception
    {
        DefaultVoiceRecognition mockVoiceRecognition = (DefaultVoiceRecognition) this.mTestEnvironmentProvider.getMockedVoiceRecognition();
        LocationSystem mockLocationSystem = this.mTestEnvironmentProvider.getMockedLocationSystem();

        // Mock "weather" keyword
        ArrayList<String> list = new ArrayList<>();
        list.add("temperature");
        list.add("forest");
        list.add("pure");
        doReturn(list).when(mockVoiceRecognition).getWordsSpoken();

        // Mock call to OpenWeatherAPI
        String json = TestEnvironmentProvider.jsonFromAsset("weatherMockData.json");

        // Mock "weather" filter recognizer
        OpenWeatherMap_Temperature filter = new OpenWeatherMap_Temperature();
        List<String> results = filter.processData(json);
        doReturn(json).when(mockLocationSystem).getWeatherData();

        // Test
        onView(withId(R.id.voice_btn)).perform(click());
        onView(withId(R.id.hello_txt)).check(matches(withText(results.get(0))));
    }

    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
    }
}
