package com.BlackDiamond2010.hzs;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation TestBean, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under TestBean.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.laotan.easyreader", appContext.getPackageName());
    }
}
