package com.rabidaudio.dev.icalopener;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.provider.CalendarContract;
import android.test.ActivityTestCase;
import android.test.ActivityUnitTestCase;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityUnitTestCase<MyActivity> {
    public ApplicationTest(){
        super(MyActivity.class);
    }

    InputStream is;
    ICALParser parser;
    Intent output;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        is = getInstrumentation().getTargetContext().getResources().openRawResource(R.raw.testfile2);

        Assert.assertNotNull("Test file is available", is);

        parser = new ICALParser(is);

        Assert.assertNotNull("Parser is created", parser);

        output = parser.buildIntent();

        Assert.assertNotNull("Intent is created", output);
    }




    public void testBasicIntentProperties() throws Exception {

        Assert.assertEquals("Proper Action", Intent.ACTION_INSERT, output.getAction());
        Assert.assertEquals("Proper URI", "content://com.android.calendar/events", output.getDataString());
    }

    public void testTitleDescription() throws Exception {
        String title = output.getStringExtra(CalendarContract.Events.TITLE);
        String location = output.getStringExtra(CalendarContract.Events.EVENT_LOCATION);
        String description = output.getStringExtra(CalendarContract.Events.DESCRIPTION);

        Assert.assertEquals("Proper title", "Bass lesson", title);
        Assert.assertEquals("Proper location", "Atlanta, GA, USA", location);
        Assert.assertEquals("Proper description", "something", description);

    }

    public void testProperTime() throws Exception {
        long startTime = output.getLongExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, -1);
        long endTime = output.getLongExtra(CalendarContract.EXTRA_EVENT_END_TIME, -1);
        boolean allDay = output.getBooleanExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

        Assert.assertEquals("Proper start time", new Date(2014, 7, 23, 18, 0).getTime(), startTime);
        Assert.assertEquals("Proper end time", new Date(2014, 7, 23, 19, 0).getTime(), endTime);
        //Assert.assertFalse("Proper full-day event status", allDay);
    }

    public void testProperExtendedFields() throws Exception {
        String email = output.getStringExtra(Intent.EXTRA_EMAIL);
        String rrule = output.getStringExtra(CalendarContract.Events.RRULE);
        //Events.ACCESS_LEVEL
        //Events.AVAILABILITY

        Assert.assertEquals("Proper repetition rules", "FREQ=WEEKLY;WKST=SU;INTERVAL=2;BYDAY=WE", rrule);
    }

    @Override
    protected void tearDown() throws Exception{
        is.close();
        super.tearDown();
    }
}