package com.martynaskairys.walltip;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class PictureActivity extends AppCompatActivity {

	public static final String IMAGE_INT = "Image Int";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.primary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        setTitle(" ");

        int imageInt = getIntent().getIntExtra(IMAGE_INT, R.drawable.mok);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
		ViewCompat.setTransitionName(imageView, IMAGE_INT);
        imageView.setImageResource(imageInt);

    }
}
