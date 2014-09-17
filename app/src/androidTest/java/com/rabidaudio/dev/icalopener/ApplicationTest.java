package com.rabidaudio.dev.icalopener;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.provider.CalendarContract;
import android.test.ActivityTestCase;
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
public class ApplicationTest extends ActivityTestCase {

    public void testProperFileParsing() throws Exception {
        InputStream is = getInstrumentation().getTargetContext().getResources().openRawResource(R.raw.testfile2);

        Assert.assertNotNull("Test file is available", is);

        ICALParser parser = new ICALParser(is);

        Assert.assertNotNull("Parser is created", parser);

        Intent output = parser.buildIntent();

        Assert.assertNotNull("Intent is created", output);
        Assert.assertEquals("Proper Action", output.getAction(), "content://com.android.calendar/events");

        String title = output.getStringExtra(CalendarContract.Events.TITLE);
        long startTime = output.getLongExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, -1);
        long endTime = output.getLongExtra(CalendarContract.EXTRA_EVENT_END_TIME, -1);
        boolean allDay = output.getBooleanExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        String location = output.getStringExtra(CalendarContract.Events.EVENT_LOCATION);
        String description = output.getStringExtra(CalendarContract.Events.DESCRIPTION);
        String email = output.getStringExtra(Intent.EXTRA_EMAIL);
        String rrule = output.getStringExtra(CalendarContract.Events.RRULE);
        //Events.ACCESS_LEVEL
        //Events.AVAILABILITY

        Assert.assertEquals("Proper title", title, "Bass lesson");

        Assert.assertEquals("Proper start time", startTime, new Date(2014, 7, 23, 18, 0).getTime());
        Assert.assertEquals("Proper end time", endTime, new Date(2014, 7, 23, 19, 0).getTime());
        //Assert.assertFalse("Proper full-day event status", allDay);

        Assert.assertEquals("Proper location", location, "Atlanta, GA, USA");
        Assert.assertEquals("Proper description", description, "something");

        Assert.assertEquals("Proper repetition rules", rrule, "FREQ=WEEKLY;WKST=SU;INTERVAL=2;BYDAY=WE");


    }
}