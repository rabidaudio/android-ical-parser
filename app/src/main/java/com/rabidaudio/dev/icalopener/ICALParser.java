package com.rabidaudio.dev.icalopener;

import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.PhantomReference;
import java.util.Iterator;

/**
 * Convert ICS files into Calendar Provider intents.
 */
public class ICALParser {
    public static final String TAG = ICALParser.class.getCanonicalName();
    public static final boolean DEBUG = MyActivity.DEBUG;

    private Calendar calendar;
    private VEvent event;

    public ICALParser(InputStream ics) throws IOException, ParserException {
        CalendarBuilder  builder = new CalendarBuilder();
        calendar = builder.build(ics);
        event = findFirstEvent();
    }

    public Intent buildIntent(){
        Intent i = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);

        if(DEBUG) {
            for (Object o : event.getProperties()) {
                Property property = (Property) o;
                Log.d(TAG, "Property [" + property.getName() + ", " + property.getValue() + "]");
            }
        }

        i.putExtra(CalendarContract.Events.TITLE, getValueOrNull(Property.SUMMARY));
        i.putExtra(CalendarContract.Events.DESCRIPTION, getValueOrNull(Property.DESCRIPTION));
        i.putExtra(CalendarContract.Events.EVENT_LOCATION, getValueOrNull(Property.LOCATION));

        long start = event.getStartDate().getDate().getTime();
        long end = event.getEndDate().getDate().getTime();
        i.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,start);
        i.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);

        boolean allDayEvent = ((event.getProperty("X-MICROSOFT-CDO-ALLDAYEVENT") != null            //Microsoft's custom field exists
                    && event.getProperty("X-MICROSOFT-CDO-ALLDAYEVENT").getValue().equals("true"))  //  and is true, or
                || (end-start % 1000*60*60*24 == 0 ) );                                             //the duration is an integer number of days

        i.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, allDayEvent);

        return i;
    }

    private String getValueOrNull(String name){
        Property p = event.getProperty(name);
        return p == null ? null : p.getValue();
    }

    private VEvent findFirstEvent(){
        for (Object o : calendar.getComponents()) {
            Component c = (Component) o;
            VEvent e = c instanceof VEvent ? ((VEvent) c) : null;
            if (e != null) {
                return e;
            }
        }
        return null;
    }

    /*
            Intent out = new Intent();
        out.setAction(Intent.ACTION_INSERT);
        out.setData(CalendarContract.Events.CONTENT_URI);
        out.putExtra(CalendarContract.Events.TITLE, "a title");
        out.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        out.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis());
        startActivity(out);
     */
}
