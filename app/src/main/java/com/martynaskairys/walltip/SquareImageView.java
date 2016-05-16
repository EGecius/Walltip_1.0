package com.martynaskairys.walltip;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Martynas on 2016-04-28.
 */
public class SquareImageView extends ImageView {

    public SquareImageView(Context context)
    {
        super(context);

    }

        @Override
        protected void onMeasure ( int widthMeasureSpec, int heightMeasureSpec){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            int width = getMeasuredWidth();
            setMeasuredDimension(width, width);
        }


}
