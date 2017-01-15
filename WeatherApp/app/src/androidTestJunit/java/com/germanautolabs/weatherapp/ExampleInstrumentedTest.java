package com.germanautolabs.weatherapp;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.germanautolabs.weatherapp.android.activities.MainActivity;
import com.germanautolabs.weatherapp.android.components.voice.DefaultVoiceRecognition;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.doReturn;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleInstrumentedTest extends ActivityInstrumentationTestCase2<MainActivity>
{
    private TestEnvironmentProvider mTestEnvironmentProvider;

    public ExampleInstrumentedTest()
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
    public void testBasic() throws Exception
    {
        DefaultVoiceRecognition mockVoiceRecognition = (DefaultVoiceRecognition) this.mTestEnvironmentProvider.getMockedVoiceRecognition();

        ArrayList<String> list = new ArrayList<>();
        list.add("weather");
        list.add("leather");

        doReturn(list).when(mockVoiceRecognition).getWordsSpoken();

        onView(withId(R.id.voice_btn)).perform(click());
        onView(withId(R.id.hello_txt)).check(matches(withText(getListWord(list))));
    }

    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
    }

    private String getListWord(List<String> pList)
    {
        String result = "";
        for (String word : pList)
        {
            result += word + " ";
        }

        return result;
    }
}
