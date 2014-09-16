package com.rabidaudio.dev.icalopener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ICALReceiver extends BroadcastReceiver {
    public static final String TAG = ICALReceiver.class.getCanonicalName();

    public ICALReceiver() {
        //no-op
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, intent.getData().toString());
        Toast.makeText(context, intent.getDataString(), Toast.LENGTH_LONG).show();
    }
}
