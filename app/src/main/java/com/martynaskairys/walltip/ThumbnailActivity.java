package com.martynaskairys.walltip;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.martynaskairys.walltip.shared.networking.NetworkingUtils;
import com.martynaskairys.walltip.shared.tracking.UserTracker;
import com.martynaskairys.walltip.shared.tracking.UserTrackerImpl;


/**
 * Shows a list of pictures
 */
public class ThumbnailActivity extends AppCompatActivity {
    // TODO: 03/01/2017 explain public static final (what does it mean)
    public static final String EXPLANATION = "explanation";
    public static final String TEXTS = "texts";
    public static final String IMAGES = "images";
    public static final String THUMB_IDS = "thumb_ids";
    public static final String FOLDER_INDEX = "folder_index";
    public static final String COVER_IMAGE = "cover_image";
    // FIXME: 03/01/2017
    private int[] thumbIds;
    private ViewGroup rootView;
    private ProgressBar progressBar;

    private UserTracker userTracker = new UserTrackerImpl();
    private int folderIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userTracker.reportInThumbnailActivityOnCreate();

        thumbIds = getIntent().getIntArrayExtra(THUMB_IDS);
        folderIndex = getIntent().getIntExtra(FOLDER_INDEX, -1);
        if (folderIndex == -1) throw new IllegalArgumentException("folder index was not found");

        setContentView(R.layout.activity_thumbnail);
        findViews();

        setExplanationText();
        setTitle();
        setGridView();
        setupShowingRetryMessageIfThereIsNoNetwork();
        setCoverImage();
    }


    private void findViews() {
        rootView = (ViewGroup) findViewById(R.id.root);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void setCoverImage() {
        ImageView imageCover = (ImageView) findViewById(R.id.thumbnail_cover_image);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            int j = (int) b.get(COVER_IMAGE);
            imageCover.setImageResource(j);
        }

    }

    private void setExplanationText() {
        TextView textExplainingFolderChoice = (TextView) findViewById(R.id.text_explaining_folder_content);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            String j = (String) b.get(EXPLANATION);
            textExplainingFolderChoice.setText(j);
        }
    }

    private void setTitle() {

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            String j = (String) b.get(TEXTS);
            setTitle(j);
        }
    }

    private void setGridView() {

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        final ImageAdapter adapter = new ImageAdapter(ThumbnailActivity.this);
        recyclerView.setAdapter(adapter);

        setThumbnailsSmoothly(adapter);

    }

    /**
     * set thumbnails while minimizing chances of frame loss. Since we are loading images from drawables, once set,
     * they are immediately loaded on the main thread, thus adding a long queue of work immediately on the main
     * thread. Testing has shown activity loads faster if setting of drawables is delayed
     */
    private void setThumbnailsSmoothly(final ImageAdapter adapter) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setData(thumbIds);
                progressBar.setVisibility(View.GONE);
            }
        }, 1000 /* ms */);
    }

    private void setupShowingRetryMessageIfThereIsNoNetwork() {
        //noinspection ConstantConditions
        findViewById(R.id.RL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserClickedMakeMagicButton();
            }
        });
    }

    private void onUserClickedMakeMagicButton() {
        if (isConnectedToNetwork()) {
            goToChangeWallpaperActivity();
        } else {
            showRetryButtonOnly();
        }
    }

    private void goToChangeWallpaperActivity() {
        final String[] images = getIntent().getStringArrayExtra(IMAGES);
        Intent intent = new Intent(ThumbnailActivity.this, CategorySelectedActivity.class);
        intent.putExtra(IMAGES, images);
        intent.putExtra(FOLDER_INDEX, folderIndex);

        startActivity(intent);
    }

    private boolean isConnectedToNetwork() {
        return new NetworkingUtils(getApplicationContext()).isConnectedToNetwork();
    }

    private void showRetryButtonOnly() {
        Snackbar.make(rootView, R.string.something_not_right, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onUserClickedMakeMagicButton();
                    }
                }).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}



