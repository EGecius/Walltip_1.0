package com.martynaskairys.walltip;

import android.app.IntentService;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.martynaskairys.walltip.DataTypes.Folder;
import com.martynaskairys.walltip.images.ImageStorageImpl;
import com.martynaskairys.walltip.images.ImageStorageManager;
import com.martynaskairys.walltip.networking.ApiService;
import com.martynaskairys.walltip.networking.RetrofitSetup;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Sets wallpapers for device's home-screen
 */
public class WallpaperService extends IntentService {

    public static final String TAG = "WallpaperService"; //vienas tagas visoje klaseje

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
        Log.i(TAG, "Image to be shown: " + randomImageUrl);

        try {
            InputStream ins = new URL(randomImageUrl).openStream();

            Bitmap tempBitmap = BitmapFactory.decodeStream(ins);
            Bitmap bitmap = Bitmap.createScaledBitmap(tempBitmap, width, height, true);

            Log.i(TAG, "Wallpaper size in bytes: " + bitmap.getByteCount());

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            wallpaperManager.setWallpaperOffsetSteps(1, 1);
            wallpaperManager.suggestDesiredDimensions(width, height);
            wallpaperManager.setBitmap(bitmap);

            Log.i(TAG, "Wallpaper should appear in a sec");

        } catch (IOException e) {
            String msg = "Exception reading image";
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Log.e(TAG, msg, e);
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

                ImageStorageManager manager = new ImageStorageManager(new ImageStorageImpl(getApplicationContext()));
                int index = manager.getUserChosenFolderIndex();
                String[] folderUrls = toArray(folders.get(index));

                manager.saveUserChosenUrls(folderUrls);
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
