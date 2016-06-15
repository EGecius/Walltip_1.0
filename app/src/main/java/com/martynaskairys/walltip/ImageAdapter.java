package com.martynaskairys.walltip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Martynas on 2016-04-28.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Holder> {

	private Activity mActivity;
    private Context mContext;
	/** references to our images */
	private int[] mThumbIds = new int[0];

	/**
	 * @param activity activity where this adapter is used
	 *
	 */
    public ImageAdapter(final Activity activity) {
		mActivity = activity;
        mContext = activity.getApplicationContext();
    }

	@Override
	public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_adapter_item, parent, false);
		return new Holder(view);
	}

	@Override
	public void onBindViewHolder(final Holder holder, final int position) {
		Picasso.with(mContext)
						.load(mThumbIds[position])
				.fit()
				.centerCrop()
				.placeholder(R.drawable.mok)
				.into(holder.imageView);

		setClickListener(holder.imageView, position);
	}

	@Override
	public int getItemCount() {
		return mThumbIds.length;
	}

	private void setClickListener(final ImageView imageView, final int position) {
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

	static class Holder extends RecyclerView.ViewHolder {

		final ImageView imageView;

		public Holder(final View itemView) {
			super(itemView);
			imageView = (ImageView) itemView.findViewById(R.id.imageView);
		}
	}

	/** sets thumb ids on the adapter */
	void setData(int[] thumbIds) {
		mThumbIds = thumbIds;
		notifyDataSetChanged();
	}

}
