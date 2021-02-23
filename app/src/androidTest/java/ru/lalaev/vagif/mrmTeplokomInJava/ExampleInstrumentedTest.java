package ru.lalaev.vagif.mrmTeplokomInJava;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented fragment_work_plane_design2, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under fragment_work_plane_design2.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("ru.lalaev.vagif.mrmTeplokomInJava", appContext.getPackageName());
    }
}
