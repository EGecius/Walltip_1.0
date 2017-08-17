package com.martynaskairys.walltip.features.changewallpapers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Broadcast receiver, which notifies when the device boots.
 * Start your repeating alarm here.
 */
public class DeviceBootReceiver extends BroadcastReceiver {

	/** 1 day in milliseconds */
	public static final int A_DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	public static final String DEVICE_BOOTED_ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (hasDeviceJustBooted(intent)) {
			startChangingWallpapers(context);
		}
	}

	private void startChangingWallpapers(final Context context) {
		Intent alarmIntent = new Intent(context, WallpaperServiceReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				A_DAY_IN_MILLIS, pendingIntent);
	}

	private boolean hasDeviceJustBooted(final Intent intent) {
		return intent.getAction().equals(DEVICE_BOOTED_ACTION);
	}
}