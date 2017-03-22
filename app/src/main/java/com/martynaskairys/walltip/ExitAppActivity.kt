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
import com.martynaskairys.walltip.ThumbnailActivity.FOLDER_INDEX
import com.martynaskairys.walltip.ThumbnailActivity.IMAGES
import com.martynaskairys.walltip.images.ImageStorageImpl
import com.martynaskairys.walltip.images.ImageStorageManager


class ExitAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_exit_app)
        showArrow()
        setupExitButton()

        saveFolderIndexAndUrls()
        changeWallpaperNow()
        scheduleRegularWallpaperUpdates()
    }

    private fun showArrow() {
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.primary), PorterDuff.Mode.SRC_ATOP)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
    }

    private fun setupExitButton() {
        findViewById(R.id.buttonExitApp)!!.setOnClickListener {
            val intent = Intent(this@ExitAppActivity, LauncherInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveFolderIndexAndUrls() {
        val imageUrls = intent.getStringArrayExtra(IMAGES)
        val folderIndex = intent.getIntExtra(FOLDER_INDEX, -1)

        if (folderIndex == -1) throw IllegalArgumentException("folder index not found")
        if (imageUrls.isEmpty()) throw IllegalArgumentException("ExitAppActivity received empty urls list: " + imageUrls)

        val imageStorageManager = ImageStorageManager(ImageStorageImpl(applicationContext))
        imageStorageManager.saveUserChosenUrls(imageUrls)
        imageStorageManager.saveUserChosenFolderIndex(folderIndex)
    }

    private fun changeWallpaperNow() {
        val intent = Intent(this, WallpaperService::class.java)
        startService(intent)
    }

    fun scheduleRegularWallpaperUpdates() {

        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val dayInMillis: Long = 1000 * 60
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



