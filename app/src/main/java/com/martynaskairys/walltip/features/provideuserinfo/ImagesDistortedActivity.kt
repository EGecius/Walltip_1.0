package com.martynaskairys.walltip.features.provideuserinfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import com.martynaskairys.walltip.R
import com.martynaskairys.walltip.features.selectcategory.MainActivity

/** Provides instructions to user in case images dow not show properly  */
class ImagesDistortedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images_distorted)
    }

    fun downloadGoogleLauncher(view: View) {

        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(URL_ANDROID_LAUNCHER)
        startActivity(i)
    }

    fun linkToPlayStore(view: View) {

        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(URL_PLAY_STORE)
        startActivity(i)
    }

    fun exitApp(view: View) {

        val intent1 = Intent(this, MainActivity::class.java)
        val intent2 = Intent(Intent.ACTION_MAIN)
        intent2.addCategory(Intent.CATEGORY_HOME)
        intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        Toast.makeText(this, R.string.exit_message, Toast.LENGTH_SHORT).show()

        startActivity(intent1)
        startActivity(intent2)
    }

    companion object {

        val URL_ANDROID_LAUNCHER = "https://play.google.com/store/apps/details?id=com.google.android.launcher&hl=en"
        val URL_PLAY_STORE = "https://play.google.com/store/apps?hl=en"
    }
}
