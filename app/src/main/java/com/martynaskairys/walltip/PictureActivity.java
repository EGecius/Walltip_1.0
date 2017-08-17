package com.martynaskairys.walltip;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.martynaskairys.walltip.shared.tracking.UserTracker;
import com.martynaskairys.walltip.shared.tracking.UserTrackerImpl;

public class PictureActivity extends AppCompatActivity {

	public static final String IMAGE_INT = "Image Int";

	private UserTracker userTracker = new UserTrackerImpl();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        userTracker.reportInPictureActivityOnCreate();

        int imageInt = getIntent().getIntExtra(IMAGE_INT, R.drawable.mok);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
		ViewCompat.setTransitionName(imageView, IMAGE_INT);
        imageView.setImageResource(imageInt);

    }

    public void goBack(View view) {

        switch (android.R.id.home) {
            case android.R.id.home:
                onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }
}
