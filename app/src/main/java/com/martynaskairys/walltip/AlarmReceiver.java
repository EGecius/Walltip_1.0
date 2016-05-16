package com.martynaskairys.walltip;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //execute wallpaper service (change wallpapers randomly)
        Intent serviceIntent = new Intent(context, WallpaperService.class);
        context.startService(serviceIntent);

    }
}


