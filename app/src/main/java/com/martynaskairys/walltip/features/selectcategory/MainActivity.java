package com.martynaskairys.walltip.features.selectcategory;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.martynaskairys.walltip.features.provideuserinfo.OnboardingActivity;
import com.martynaskairys.walltip.R;
import com.martynaskairys.walltip.features.allowuserfeedback.SurveyActivity;
import com.martynaskairys.walltip.features.provideuserinfo.AboutActivity;
import com.martynaskairys.walltip.shared.datatypes.Folder;
import com.martynaskairys.walltip.shared.networking.ApiService;
import com.martynaskairys.walltip.shared.networking.RetrofitSetup;
import com.martynaskairys.walltip.shared.utils.ActivityUtils;
import com.martynaskairys.walltip.shared.utils.Utils;

import java.util.List;

import io.fabric.sdk.android.Fabric;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * First screen that users see. Here they will see a list of image categories.
 */
public class MainActivity extends AppCompatActivity {

    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;

    public static final int BUTTON_FOLDER_INDEX_A = 0;
    public static final int BUTTON_FOLDER_INDEX_B = 1;
    public static final int BUTTON_FOLDER_INDEX_C = 2;
    private ViewGroup content;
    private ProgressBar progressBar;
    private ImageView buttonA;
    private ImageView buttonB;
    private ImageView buttonC;
    private Button buttonMenu;
    private ViewGroup rootView;
    private TextView textView;

    private int improveWorkHabitsImage = R.drawable.pic1a;
    private int liveHealthyImage = R.drawable.pic1b;
    private int boostPositivityImage = R.drawable.pic1c;

    private int[] improveWorkHabitsThumbIds = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4,
            R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9};
    private int[] liveHealthyThumbIds = {R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4
            , R.drawable.b5, R.drawable.b6, R.drawable.b7, R.drawable.b8, R.drawable.b9};
    private int[] boostPositivityThumbIds = {R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5, R.drawable.c6,
            R.drawable.c7, R.drawable.c8, R.drawable.c9};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Wallpee);
        super.onCreate(savedInstanceState);

        //Checks if user is first time here
        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(MainActivity.this, PREF_USER_FIRST_TIME, "true"));
        Intent introIntent = new Intent(MainActivity.this, OnboardingActivity.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);
        if (isUserFirstTime) {
            startActivity(introIntent);
        }

        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_choosing_folder);

        buttonMenu = (Button) findViewById(R.id.button_menu);
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, buttonMenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());


                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.about:
                                openAboutActivityFromMenuClick();
                                return true;
                            case R.id.feedback:
                                composeEmail();
                                return true;

                        }
                        return true;

                    }

                });

                popup.show(); //showing popup menu
            }
        });


        findViews();
        fetchImageUrlsAndUpdateUiAccordingly();
    }

    public void openAboutActivityFromMenuClick() {

        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);

    }

    private void findViews() {
        rootView = (ViewGroup) findViewById(R.id.root);
        content = (ViewGroup) findViewById(R.id.content);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textView = (TextView) findViewById(R.id.walltip_text);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/bernadette.ttf");
        textView.setTypeface(tf);


        buttonA = (ImageView) findViewById(R.id.button_folder_a);
        buttonB = (ImageView) findViewById(R.id.button_folder_b);
        buttonC = (ImageView) findViewById(R.id.button_folder_c);


        Button buttonD = (Button) findViewById(R.id.button_folder_make_your_suggestion);
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
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
                setupButtons(folders);
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

    private void setupButtons(List<Folder> folderList) {
        String[] urlsFolderA = toArray(folderList.get(0));
        String[] urlsFolderB = toArray(folderList.get(1));
        String[] urlsFolderC = toArray(folderList.get(2));

        setupFolderButton(urlsFolderA, buttonA, BUTTON_FOLDER_INDEX_A, R.string.folder_a, improveWorkHabitsThumbIds, improveWorkHabitsImage);
        setupFolderButton(urlsFolderB, buttonB, BUTTON_FOLDER_INDEX_B, R.string.folder_b, liveHealthyThumbIds, liveHealthyImage);
        setupFolderButton(urlsFolderC, buttonC, BUTTON_FOLDER_INDEX_C, R.string.folder_c, boostPositivityThumbIds, boostPositivityImage);

    }

    /**
     * Convert list of image urls to string array.
     *
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

    private void setupFolderButton(final String[] urlsFolder, ImageView button, final int buttonFolderIndex,
                                   @StringRes final int folderDescription, final int[] thumbIds, final int coverImage) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                intent.putExtra(CategoryActivity.EXPLANATION, getString(R.string.text_explaining_folder_content_a));
                intent.putExtra(CategoryActivity.IMAGES, urlsFolder);
                intent.putExtra(CategoryActivity.FOLDER_INDEX, buttonFolderIndex);
                intent.putExtra(CategoryActivity.TEXTS, getString(folderDescription));
                intent.putExtra(CategoryActivity.THUMB_IDS, thumbIds);
                intent.putExtra(CategoryActivity.COVER_IMAGE, coverImage);
                ActivityUtils.startActivityWithTransitionAnimation(MainActivity.this,
                        intent,
                        new Pair<View, String>(v, getString(R.string.transition_cover)));
            }
        });
    }

    public void composeEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"martynaskairys@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject_line));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
