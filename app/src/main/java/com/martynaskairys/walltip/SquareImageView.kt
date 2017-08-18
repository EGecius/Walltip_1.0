package com.martynaskairys.walltip

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by Martynas on 2016-04-28.
 */
//TODO this class was used for thumbnail_activity thumb_pics to show square (if val width = measure width...)
//should I just use image_adapter_item or this class for the right measure

class SquareImageView : ImageView {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val height = measuredHeight
        setMeasuredDimension(height, height)
    }


}
