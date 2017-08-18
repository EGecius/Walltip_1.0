package com.martynaskairys.walltip.features.provideuserinfo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View

import com.martynaskairys.walltip.features.selectcategory.MainActivity
import com.martynaskairys.walltip.R

/** Tells user about the app and how to use it  */
class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setScreenTitle()
    }

    private fun setScreenTitle() {
        title = getString(R.string.about_screen_title)
    }

    fun selectCategory(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //up buttons behave like back button
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
