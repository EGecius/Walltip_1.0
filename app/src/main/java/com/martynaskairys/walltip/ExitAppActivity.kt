package com.martynaskairys.walltip

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.martynaskairys.walltip.images.ImageStorageImpl
import com.martynaskairys.walltip.images.ImageStorageManager


class ExitAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_exit_app)
        showArrow()
        setupExiButton()

        saveUrls()
        changeWallpaperNow()
        scheduleRegularWallpaperUpdates()
    }

    private fun showArrow() {
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha)
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.primary), PorterDuff.Mode.SRC_ATOP)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
    }

    private fun setupExiButton() {
        findViewById(R.id.buttonExitApp)!!.setOnClickListener {
            val intent = Intent (this@ExitAppActivity, LauncherInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveUrls() {
        val imageUrls = intent.getStringArrayExtra("images")
        if (imageUrls.isEmpty()) {
            throw IllegalArgumentException("ExitAppActivity received empty urls list: " + imageUrls)
        } else {
            val imageStorageManager = ImageStorageManager(ImageStorageImpl(applicationContext))
            imageStorageManager.saveUserChosenUrls(imageUrls)
        }
    }

    private fun changeWallpaperNow() {
        val intent = Intent(this, WallpaperService::class.java)
        startService(intent)
    }

    fun scheduleRegularWallpaperUpdates() {

        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val dayInMillis: Long = 1000 * 60 * 60 * 24
        val firstTrigger = System.currentTimeMillis() + dayInMillis
        manager.setRepeating(AlarmManager.RTC_WAKEUP, firstTrigger, dayInMillis, getWallpaperPendingIntent())

        val intent = Intent()
        intent.action = "WallpaperServiceReceiver"
        sendBroadcast(intent)
    }

    private fun getWallpaperPendingIntent(): PendingIntent? {
        /* Retrieve a PendingIntent that will perform a broadcast */
        val wallpaperReceiverIntent = Intent(this@ExitAppActivity, WallpaperServiceReceiver::class.java)
        val wallpaperReceiverPendingIntent = PendingIntent.getBroadcast(this@ExitAppActivity, 0,
                wallpaperReceiverIntent, 0)
        return wallpaperReceiverPendingIntent
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
}



