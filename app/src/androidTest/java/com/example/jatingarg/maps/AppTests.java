package com.example.jatingarg.maps;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by jatingarg on 28/04/17.
 */
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static junit.framework.Assert.assertTrue;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
public class AppTests {

    @Rule
    public ActivityTestRule<MapsActivity> mActivityTestRule = new ActivityTestRule<MapsActivity>(MapsActivity.class);

    @Rule
    public ActivityTestRule<SensorActivity> mSensorActivity = new ActivityTestRule<SensorActivity>(SensorActivity.class);

    @Test
    public void testApp(){

        //NOTE: I cant actually test google map APIs with espresso as its not supported
        //NOTE
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        onView(withId(R.id.sensorOverview)).perform(click());

        //checking if appropriate widgets are displayed on next activity
        onView(withId(R.id.headProximity)).check(matches(isDisplayed()));
        onView(withId(R.id.textProximity)).check(matches(isDisplayed()));
        onView(withId(R.id.headLight)).check(matches(isDisplayed()));
        onView(withId(R.id.textLight)).check(matches(isDisplayed()));
        onView(withId(R.id.headAccelerometer)).check(matches(isDisplayed()));
        onView(withId(R.id.textAccelerometer)).check(matches(isDisplayed()));

        //Testing if the device has requested sensors and if the data is being displayed if it does
        SensorManager sensorManager  = (SensorManager) mActivityTestRule.getActivity().getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(lightSensor != null){
            //getting sensor readings
            assertTrue(mSensorActivity.getActivity().isLightSensorPresent());
            String text = ((TextView) mSensorActivity.getActivity().findViewById(R.id.textLight))
                            .getText().toString();
            assertNotNull(text);

        }
        if(accelerometerSensor != null){
            assertTrue(mSensorActivity.getActivity().isAccelerometerPresent());
            String text = ((TextView) mSensorActivity.getActivity().findViewById(R.id.textAccelerometer))
                    .getText().toString();
            assertNotNull(text);
        }
        if(proximitySensor != null){
            assertTrue(mSensorActivity.getActivity().isProximityPresent());
            String text = ((TextView) mSensorActivity.getActivity().findViewById(R.id.textProximity))
                    .getText().toString();
            assertNotNull(text);
        }




    }


}
