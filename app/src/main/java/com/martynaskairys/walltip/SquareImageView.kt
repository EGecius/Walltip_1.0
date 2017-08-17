package com.martynaskairys.walltip

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * A square [ImageView] with height automatically set to be same as its width
 */
class SquareImageView : ImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val height = measuredHeight
        setMeasuredDimension(height, height)
    }

}
