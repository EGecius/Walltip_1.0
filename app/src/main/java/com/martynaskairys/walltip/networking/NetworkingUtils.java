package com.martynaskairys.walltip.networking;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Common network-related enquiries
 */
public final class NetworkingUtils {

	private final Context context;

	public NetworkingUtils(final Context context) {
		this.context = context;
	}

	/** Whether device is connected to network */
	public boolean isConnectedToNetwork() {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null;
	}

}
