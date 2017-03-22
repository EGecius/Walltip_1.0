package com.martynaskairys.walltip;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.martynaskairys.walltip.tracking.UserTracker;
import com.martynaskairys.walltip.tracking.UserTrackerImpl;

public class PictureActivity extends AppCompatActivity {

	public static final String IMAGE_INT = "Image Int";

	private UserTracker userTracker = new UserTrackerImpl();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);


        userTracker.reportInPictureActivityOnCreate();

        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.primary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

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
