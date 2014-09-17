package com.rabidaudio.dev.icalopener;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
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
import java.util.Iterator;
import java.util.Set;


public class MyActivity extends Activity {

    public static final String TAG = MyActivity.class.getCanonicalName();
    public static final Boolean DEBUG = true; //BuildConfig.DEBUG;


    //TODO make loading icon on Activity for big files

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Intent i = getIntent();


        Log.d(TAG, "Dumping Intent start");
        Log.d(TAG, "Action: "+i.getAction());
        Log.d(TAG, "URI: "+i.getDataString());
        Log.d(TAG, "Type: "+i.getType());
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = it.next();
                Log.d(TAG, "[" + key + "=" + bundle.get(key) + "]");
            }
        }
        Log.d(TAG, "Dumping Intent end");

        Log.d(TAG, getFilePathFromUri(this, i.getData()));
        try {
            startActivity(new ICALParser(new FileInputStream(getFilePathFromUri(this, i.getData()))).buildIntent());
        } catch (Exception e) {
            if(DEBUG) Log.e(TAG, "Couldn't parse",e);
        }

        if(DEBUG) Log.d(TAG, "Done");
        finish();

/*
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


    //http://stackoverflow.com/a/23750660
    public static String getFilePathFromUri(Context c, Uri uri) {
        String filePath = null;
        if ("content".equals(uri.getScheme())) {
            String[] filePathColumn = { MediaStore.MediaColumns.DATA };
            ContentResolver contentResolver = c.getContentResolver();
            Cursor cursor = contentResolver.query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        } else if ("file".equals(uri.getScheme())) {
            filePath = new File(uri.getPath()).getAbsolutePath();
        }
        return filePath;
    }
}
