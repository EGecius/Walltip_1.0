package com.martynaskairys.walltip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by Martynas on 2016-04-28.
 */
public class ImageAdapter extends BaseAdapter {

	private Activity mActivity;
    private Context mContext;
	// references to our images
	private final int[] mThumbIds;

	/**
	 * @param activity activity where this adapter is used
	 * @param thumbIds ids of images to show
	 */
    public ImageAdapter(final Activity activity, final int[] thumbIds) {
		mActivity = activity;
        mContext = activity.getApplicationContext();
		mThumbIds = thumbIds;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        SquareImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new SquareImageView(mContext);
            // imageView.setLayoutParams(new GridView.LayoutParams(185,185));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);

			setClickListener(imageView, position);

		} else {
            imageView = (SquareImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

	private void setClickListener(final SquareImageView imageView, final int position) {
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				final Intent intent = new Intent(mActivity, PictureActivity.class);
				intent.putExtra(PictureActivity.IMAGE_INT, mThumbIds[position]);

				final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,
						imageView, PictureActivity.IMAGE_INT);
				ActivityCompat.startActivity(mActivity, intent, options.toBundle());
			}
		});
	}

}
