package com.martynaskairys.walltip;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/** Launches {@link WallpaperService} when invokes */
public class WallpaperServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //execute wallpaper service (change wallpapers randomly)
        Intent serviceIntent = new Intent(context, WallpaperService.class);
        context.startService(serviceIntent);
    }
}


