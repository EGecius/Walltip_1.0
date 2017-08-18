package com.martynaskairys.walltip.features.selectcategory

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView

import com.martynaskairys.walltip.R
import com.martynaskairys.walltip.shared.tracking.UserTrackerImpl

/** Shows single image  */
class SingleImageActivity : AppCompatActivity() {

    private val userTracker = UserTrackerImpl()
    private var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_image)

        trackScreen()
        setupImage()
    }

    private fun trackScreen() {
        userTracker.reportInPictureActivityOnCreate()
    }

    private fun setupImage() {
        imageView = findViewById(R.id.imageView) as ImageView
        showImageProvided()
        setImageListener()
    }

    /** Shows image passed via Intent  */
    private fun showImageProvided() {
        val imageInt = intent.getIntExtra(IMAGE_INT, R.drawable.mok)
        ViewCompat.setTransitionName(imageView, IMAGE_INT)
        imageView!!.setImageResource(imageInt)
    }

    private fun setImageListener() {
        imageView!!.setOnClickListener { onBackPressed() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {

        val IMAGE_INT = "Image Int"
    }
}
