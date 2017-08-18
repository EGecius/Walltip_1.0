package com.martynaskairys.walltip.features.selectcategory;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.martynaskairys.walltip.R;
import com.martynaskairys.walltip.shared.tracking.UserTracker;
import com.martynaskairys.walltip.shared.tracking.UserTrackerImpl;

/** Shows single image */
public class SingleImageActivity extends AppCompatActivity {

	public static final String IMAGE_INT = "Image Int";

	private UserTracker userTracker = new UserTrackerImpl();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
		setupImage();

		trackScreen();
	}

	private void setupImage() {
		setImageOnClickListener();
		showImageProvided();
	}

	private void setImageOnClickListener() {
		findViewById(R.id.image_view).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				onBackPressed();
			}
		});
	}

	/** Shows image passed via Intent */
	private void showImageProvided() {
		int imageInt = getIntent().getIntExtra(IMAGE_INT, R.drawable.mok);
		ImageView imageView = (ImageView)findViewById(R.id.image);
		ViewCompat.setTransitionName(imageView, IMAGE_INT);
		imageView.setImageResource(imageInt);
	}

	private void trackScreen() {
		userTracker.reportInPictureActivityOnCreate();
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
