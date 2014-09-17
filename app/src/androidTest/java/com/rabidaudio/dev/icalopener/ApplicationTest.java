package com.rabidaudio.dev.icalopener;

import android.app.Activity;
import android.app.Application;
import android.test.ActivityTestCase;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityTestCase {

    public void testProperFileParsing() throws Exception {
        InputStream is = getInstrumentation().getTargetContext().getResources().openRawResource(R.raw.testfile);

        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        Assert.assertEquals(r.readLine(), "BEGIN:VCALENDAR");
    }
}