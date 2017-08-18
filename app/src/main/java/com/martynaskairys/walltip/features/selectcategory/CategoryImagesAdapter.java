package com.martynaskairys.walltip.features.selectcategory;

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

import com.martynaskairys.walltip.R;
import com.squareup.picasso.Picasso;

/**
 * {@link RecyclerView} adapter for images shown in {@link CategoryActivity}
 */
class CategoryImagesAdapter extends RecyclerView.Adapter<CategoryImagesAdapter.Holder> {

	private Activity activity;
    private Context context;
	/** references to our images */
	private int[] thumbIds = new int[0];

	/**
	 * @param activity activity where this adapter is used
	 *
	 */
	CategoryImagesAdapter(final Activity activity) {
		this.activity = activity;
        context = activity.getApplicationContext();
    }

	@Override
	public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_adapter_item, parent, false);
		return new Holder(view);
	}

	@Override
	public void onBindViewHolder(final Holder holder, final int position) {
		Picasso.with(context)
				.load(thumbIds[position])
				.fit()
				.centerCrop()
				.placeholder(R.drawable.mok)
				.into(holder.imageView);

		setClickListener(holder.imageView, position);
	}

	@Override
	public int getItemCount() {
		return thumbIds.length;
	}

	private void setClickListener(final ImageView imageView, final int position) {
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				final Intent intent = new Intent(activity, SingleImageActivity.class);
				intent.putExtra(SingleImageActivity.Companion.getIMAGE_INT(), thumbIds[position]);

				final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
						imageView, SingleImageActivity.Companion.getIMAGE_INT());
				ActivityCompat.startActivity(activity, intent, options.toBundle());
			}
		});
	}

	static class Holder extends RecyclerView.ViewHolder {

		final ImageView imageView;

		Holder(final View itemView) {
			super(itemView);
			imageView = (ImageView) itemView.findViewById(R.id.imageView);
		}
	}

	/** sets thumb ids on the adapter */
	void setData(int[] thumbIds) {
		this.thumbIds = thumbIds;
		notifyDataSetChanged();
	}

}
