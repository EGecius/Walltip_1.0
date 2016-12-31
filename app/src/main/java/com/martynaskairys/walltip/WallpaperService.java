package com.martynaskairys.walltip;

import android.app.IntentService;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.DisplayMetrics;

import com.martynaskairys.walltip.images.ImageStorage;
import com.martynaskairys.walltip.images.ImageStorageImpl;
import com.martynaskairys.walltip.images.ImageStorageManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/** Sets wallpapers for device's home-screen */
public class WallpaperService extends IntentService {

	public WallpaperService() {
		super("WallpaperService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		changeRandomly(this);
	}

	public void changeRandomly(Context context) {

		DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
		int height = metrics.heightPixels;
		int width = metrics.widthPixels;

		String randomImageUrl = getRandomUrl();

		try {
			InputStream ins = new URL(randomImageUrl).openStream();

			Bitmap tempBitmap = BitmapFactory.decodeStream(ins);
			Bitmap bitmap = Bitmap.createScaledBitmap(tempBitmap, width, height, true);

			WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
			wallpaperManager.setWallpaperOffsetSteps(1, 1);
			wallpaperManager.suggestDesiredDimensions(width, height);
			wallpaperManager.setBitmap(bitmap);

		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	private String getRandomUrl() {

		ImageStorageManager randomGenerator = new ImageStorageManager(new ImageStorageImpl(getApplicationContext()));

			return randomGenerator.takeRandomImage();

	}
}
