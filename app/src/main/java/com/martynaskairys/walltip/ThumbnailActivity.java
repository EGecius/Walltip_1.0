package com.martynaskairys.walltip;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.martynaskairys.walltip.networking.NetworkingUtils;
import com.martynaskairys.walltip.tracking.UserTracker;
import com.martynaskairys.walltip.tracking.UserTrackerImpl;


/** Shows a list of pictures */
public class ThumbnailActivity extends AppCompatActivity {

    public static final String EXPLANATION = "explanation";
    public static final String TEXTS = "texts";
	public static final String IMAGES = "images";
	public static final String THUMB_IDS = "thumb_ids";

	private int[] thumbIds;
	private ViewGroup rootView;
	private ProgressBar progressBar;
	private View makeMagicButtonContainer;

	private UserTracker userTracker = new UserTrackerImpl();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		userTracker.reportInThumbnailActivityOnCreate();

		thumbIds = getIntent().getIntArrayExtra(THUMB_IDS);

        setContentView(R.layout.activity_thumbnail);
		findViews();

		setActionBar();
		setExplanationText();
		setTitle();
		setGridView();
		setupShowingRetryMessageIfThereIsNoNetwork();
	}

	private void findViews() {
		rootView = (ViewGroup) findViewById(R.id.root);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		makeMagicButtonContainer = findViewById(R.id.make_magic_button_container);
	}

	private void setActionBar() {
		final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		upArrow.setColorFilter(ContextCompat.getColor(this, R.color.primary), PorterDuff.Mode.SRC_ATOP);
		getSupportActionBar().setHomeAsUpIndicator(upArrow);
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

	private void setTitle(){

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

	/** set thumbnails while minimizing chances of frame loss. Since we are loading images from drawables, once set,
	 * they are immediately loaded on the main thread, thus adding a long queue of work immediately on the main
	 * thread. Testing has shown activity loads faster if setting of drawables is delayed */
	private void setThumbnailsSmoothly(final ImageAdapter adapter) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.setData(thumbIds);
				progressBar.setVisibility(View.GONE);
			}
		}, 1000 /* ms */ );
	}

	private void setupShowingRetryMessageIfThereIsNoNetwork() {
		//noinspection ConstantConditions
		makeMagicButtonContainer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onUserClickedMakeMagicButton();
			}
		});
	}

	private void onUserClickedMakeMagicButton() {
		if (isConnectedToNetwork()) {
			goToExitAppActivity();
		} else {
			showRetryButtonOnly();
		}
	}

	private void goToExitAppActivity() {
		final String[] images = getIntent().getStringArrayExtra(IMAGES);
		Intent intent = new Intent(ThumbnailActivity.this, ExitAppActivity.class);
		intent.putExtra("images", images);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
		if (id == R.id.questions) {
			composeEmail();
		}
        return super.onOptionsItemSelected(item);
    }

	public void composeEmail() {
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setData(Uri.parse("mailto:")); // only email apps should handle this
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "martynaskairys@gmail.com" });
		intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject_line));
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}

}



