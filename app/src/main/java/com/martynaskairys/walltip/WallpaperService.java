package com.martynaskairys.walltip;

import android.app.IntentService;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.martynaskairys.walltip.DataTypes.Folder;
import com.martynaskairys.walltip.images.ImageStorage;
import com.martynaskairys.walltip.images.ImageStorageImpl;
import com.martynaskairys.walltip.images.ImageStorageManager;
import com.martynaskairys.walltip.networking.ApiService;
import com.martynaskairys.walltip.networking.RetrofitSetup;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Sets wallpapers for device's home-screen
 */
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
            String mdh = "Exception reading image";
            Toast.makeText(context, mdh, Toast.LENGTH_SHORT).show();
        }
    }

    private String getRandomUrl() {

        ImageStorageManager randomGenerator = new ImageStorageManager(new ImageStorageImpl(getApplicationContext()));
        fetchImageUrls();
        return randomGenerator.takeRandomImage();


    }


    private void fetchImageUrls() {
        ApiService service = new RetrofitSetup().getService();
        service.getFolders(new Callback<List<Folder>>() {
            @Override
            public void success(List<Folder> folders, Response response) {
                String STANDARD = "standard";

                SharedPreferences preferences = getSharedPreferences(STANDARD, Context.MODE_PRIVATE);
                preferences.edit().clear().commit();

                String folderListString = new Gson().toJson(folders);

                ImageStorageManager imageStorage = new ImageStorageManager(new ImageStorageImpl(getApplicationContext()));
                imageStorage.saveUserChosenFolders(folderListString);

                String[] urlsFolderA = toArray(folders.get(0));
                String[] urlsFolderB = toArray(folders.get(1));
                String[] urlsFolderC = toArray(folders.get(2));

            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }


    private String[] toArray(Folder folder) {

        List<String> urlsList = folder.getUrls();

        String[] strings = new String[urlsList.size()];
        for (int i = 0; i < urlsList.size(); i++) {
            strings[i] = urlsList.get(i);
        }

        return strings;
    }
}
