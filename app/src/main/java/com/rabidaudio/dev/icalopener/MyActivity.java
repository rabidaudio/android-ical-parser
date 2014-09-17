package com.rabidaudio.dev.icalopener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;

import junit.framework.Assert;

import net.fortuna.ical4j.data.ParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;


public class MyActivity extends Activity {

    public static final String TAG = MyActivity.class.getCanonicalName();
    public static final Boolean DEBUG = true; //BuildConfig.DEBUG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        Intent i = getIntent();

        Log.d("z", i.getType());

        if(i.getDataString() == null){
            if(DEBUG) Log.w(TAG, "No file found");
            finish();
        }

        try {
            Intent out = new ICALParser(new FileInputStream(i.getData().getPath())).buildIntent();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }

        if(DEBUG) Log.d(TAG, "Done");
//        finish();

//        Intent out = new Intent();
//        out.setAction(Intent.ACTION_INSERT);
//        out.setData(CalendarContract.Events.CONTENT_URI);
//        out.putExtra(CalendarContract.Events.TITLE, "a title");
//        out.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
//        out.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis());
//        startActivity(out);
        /*

        EDIT

INSERT
content://com.android.calendar/events

You can also refer to the URI with Events.CONTENT_URI. For an example of using this intent, see Using an intent to insert an event.	Create an event.	Any of the extras listed in the table below.
The following table lists the intent extras supported by the Calendar Provider:

Intent Extra	Description
Events.TITLE	Name for the event.
CalendarContract.EXTRA_EVENT_BEGIN_TIME	Event begin time in milliseconds from the epoch.
CalendarContract.EXTRA_EVENT_END_TIME	Event end time in milliseconds from the epoch.
CalendarContract.EXTRA_EVENT_ALL_DAY	A boolean that indicates that an event is all day. Value can be true or false.
Events.EVENT_LOCATION	Location of the event.
Events.DESCRIPTION	Event description.
Intent.EXTRA_EMAIL	Email addresses of those to invite as a comma-separated list.
Events.RRULE	The recurrence rule for the event.
Events.ACCESS_LEVEL	Whether the event is private or public.
Events.AVAILABILITY	If this event counts as busy time or is free time that can be scheduled over.
         */
    }

}
