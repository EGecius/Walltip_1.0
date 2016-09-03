package com.martynaskairys.walltip;

import android.app.IntentService;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


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

        String randomUrl = getRandomUrl();

        try {
            InputStream ins = new URL(randomUrl).openStream();

            Bitmap tempbitMap = BitmapFactory.decodeStream(ins);
            Bitmap bitmap = Bitmap.createScaledBitmap(tempbitMap, width, height, true);

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            wallpaperManager.setWallpaperOffsetSteps(1, 1);
            wallpaperManager.suggestDesiredDimensions(width, height);
            wallpaperManager.setBitmap(bitmap);

        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    private String getRandomUrl() {
		RandomImageGenerator randomGenerator = new RandomImageGenerator(new ImageStorageImpl(getApplicationContext()));
		return randomGenerator.takeRandomImage();
    }
}
