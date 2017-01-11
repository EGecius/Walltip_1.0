package com.martynaskairys.walltip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.martynaskairys.walltip.DataTypes.Folder;
import com.martynaskairys.walltip.networking.ApiService;
import com.martynaskairys.walltip.networking.RetrofitSetup;
import com.martynaskairys.walltip.tracking.UserTracker;
import com.martynaskairys.walltip.tracking.UserTrackerImpl;

import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Activity for user to choose which folder he/she wants Pictures from
 */
public class ChoosingFolderActivity extends AppCompatActivity {

    private ViewGroup content;
    private ProgressBar progressBar;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private ViewGroup rootView;

    private int[] improveWorkHabitsThumbIds = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4,
            R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9};
    private int[] liveHealthyThumbIds = {R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4
            , R.drawable.b5, R.drawable.b6, R.drawable.b7, R.drawable.b8, R.drawable.b9};
    private int[] boostPositivityThumbIds = {R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5, R.drawable.c6,
            R.drawable.c7, R.drawable.c8, R.drawable.c9};

    private UserTracker userTracker = new UserTrackerImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_folder);

        findViews();
        fetchImageUrlsAndUpdateUiAccordingly();
        userTracker.reportInChoosingFolderActivityOnCreate();

        //setupSingButton();
    }

    private void findViews() {
        rootView = (ViewGroup) findViewById(R.id.root);
        content = (ViewGroup) findViewById(R.id.content);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        buttonA = (Button) findViewById(R.id.button_folder_a);
        buttonB = (Button) findViewById(R.id.button_folder_b);
        buttonC = (Button) findViewById(R.id.button_folder_c);

        Button buttonD = (Button) findViewById(R.id.button_folder_d);
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoosingFolderActivity.this, SurveyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchImageUrlsAndUpdateUiAccordingly() {

        showProgressBarOnly();
        ApiService service = new RetrofitSetup().getService();
        service.getFolders(new Callback<List<Folder>>() {
            @Override
            public void success(List<Folder> folders, Response response) {
                //setupButtons(folders);
                String STANDARD = "standard";

                SharedPreferences preferences = getSharedPreferences(STANDARD, Context.MODE_PRIVATE);
                preferences.edit().clear().commit();

                String folderListString = new Gson().toJson(folders);
                setupSingButton(folderListString);
                showContentOnly();
            }

            @Override
            public void failure(RetrofitError error) {
                showRetryButtonOnly();
            }
        });
    }

    private void showRetryButtonOnly() {
        content.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        Snackbar.make(rootView, R.string.something_not_right, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImageUrlsAndUpdateUiAccordingly();
            }
        }).show();
    }

    private void showContentOnly() {
        content.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBarOnly() {
        content.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void setupButtons(List<Folder> folderList) {
        String folderListString = new Gson().toJson(folderList);

        String[] urlsFolderA = toArray(folderList.get(0));
        String[] urlsFolderB = toArray(folderList.get(1));
        String[] urlsFolderC = toArray(folderList.get(2));

        // TODO refactor all those method below into single method with more that 1 parameter
        // eg. at least button reference should be passed as a parameter
        setupFolderAButton(urlsFolderA);
        setupFolderBButton(urlsFolderB);
        setupFolderCButton(urlsFolderC);
    }

    public void setupSingButton(final String folderListString) {
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String folderA = getString(R.string.text_explaining_folder_content_a);
                Intent intent = new Intent(ChoosingFolderActivity.this, ThumbnailActivity.class);
                intent.putExtra(ThumbnailActivity.EXPLANATION, getString(R.string.text_explaining_folder_content_a));
                intent.putExtra(ThumbnailActivity.IMAGES, folderListString);
                intent.putExtra(ThumbnailActivity.TEXTS, getString(R.string.folder_a));
                intent.putExtra(ThumbnailActivity.THUMB_IDS, improveWorkHabitsThumbIds);
//                Bundle bundle = new Bundle();
//                bundle.putInt("image", R.drawable.pic1a);
//                intent.putExtras(bundle);

                userTracker.reportInChoosingFolderActivityFolderA();

                startActivity(intent);
            }
        });
    }

    /**
     * Convert list of image urls to string array.
     * @param folder with image urls
     * @return array of urls
     */
    private String[] toArray(Folder folder) {

        List<String> urlsList = folder.getUrls();

        String[] strings = new String[urlsList.size()];
        for (int i = 0; i < urlsList.size(); i++) {
            strings[i] = urlsList.get(i);
        }

        return strings;
    }

    private void setupFolderAButton(final String[] urlsFolderA) {
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String folderA = getString(R.string.text_explaining_folder_content_a);
                Intent intent = new Intent(ChoosingFolderActivity.this, ThumbnailActivity.class);
                intent.putExtra(ThumbnailActivity.EXPLANATION, getString(R.string.text_explaining_folder_content_a));
                intent.putExtra(ThumbnailActivity.IMAGES, urlsFolderA);
                intent.putExtra(ThumbnailActivity.TEXTS, getString(R.string.folder_a));
                intent.putExtra(ThumbnailActivity.THUMB_IDS, improveWorkHabitsThumbIds);
//                Bundle bundle = new Bundle();
//                bundle.putInt("image", R.drawable.pic1a);
//                intent.putExtras(bundle);

                userTracker.reportInChoosingFolderActivityFolderA();

                startActivity(intent);
            }
        });
    }

    private void setupFolderBButton(final String[] urlsFolderB) {
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderB = getString(R.string.text_explaining_folder_content_b);
                Intent intent = new Intent(ChoosingFolderActivity.this, ThumbnailActivity.class);
                intent.putExtra(ThumbnailActivity.EXPLANATION, folderB);
                intent.putExtra(ThumbnailActivity.IMAGES, urlsFolderB);
                intent.putExtra(ThumbnailActivity.TEXTS, getString(R.string.folder_b));
                intent.putExtra(ThumbnailActivity.THUMB_IDS, liveHealthyThumbIds);
                Bundle bundle = new Bundle();
                bundle.putInt("image", R.drawable.pic1b);
                intent.putExtras(bundle);

                userTracker.reportInChoosingFolderActivityFolderB();

                startActivity(intent);
            }
        });
    }

    private void setupFolderCButton(final String[] urlsFolderC) {
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderC = getString(R.string.text_explaining_folder_content_c);
                Intent intent = new Intent(ChoosingFolderActivity.this, ThumbnailActivity.class);
                intent.putExtra(ThumbnailActivity.EXPLANATION, folderC);
                intent.putExtra(ThumbnailActivity.IMAGES, urlsFolderC);
                intent.putExtra(ThumbnailActivity.TEXTS, getString(R.string.folder_c));
                intent.putExtra(ThumbnailActivity.THUMB_IDS, boostPositivityThumbIds);
                Bundle bundle = new Bundle();
                bundle.putInt("image", R.drawable.pic1c);
                intent.putExtras(bundle);

                userTracker.reportInChoosingFolderActivityFolderC();

                startActivity(intent);
            }
        });
    }

}
