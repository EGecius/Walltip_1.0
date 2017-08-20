package com.martynaskairys.walltip.features.selectcategory

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
import com.martynaskairys.walltip.R
import com.martynaskairys.walltip.features.changewallpapers.WallpaperService
import com.martynaskairys.walltip.features.changewallpapers.WallpaperServiceReceiver
import com.martynaskairys.walltip.features.selectcategory.CategoryActivity.FOLDER_INDEX
import com.martynaskairys.walltip.features.selectcategory.CategoryActivity.IMAGES
import com.martynaskairys.walltip.shared.images.ImageStorageImpl
import com.martynaskairys.walltip.shared.images.ImageStorageManager


/**
 * Screen shown after user selects which images use wants to shown on his screen.
 * Images from the chosen folder will keep changing.
 * */
class CategorySelectedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_category_selected)
        setupUpButton()
        setupExitButton()

        saveFolderIndexAndUrls()
        changeWallpaperNow()
        scheduleRegularWallpaperUpdates()
    }

    private fun setupUpButton() {
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.primary), PorterDuff.Mode.SRC_ATOP)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
    }

    private fun setupExitButton() {
        findViewById(R.id.buttonExitApp)!!.setOnClickListener {
            val intent1 = Intent(this, MainActivity::class.java)
            val intent2 = Intent(Intent.ACTION_MAIN)
            intent2.addCategory(Intent.CATEGORY_HOME)
            intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            Toast.makeText(this, R.string.exit_message, Toast.LENGTH_SHORT).show()

            startActivity(intent1)
            startActivity(intent2)
        }
    }

    private fun saveFolderIndexAndUrls() {
        val imageUrls = intent.getStringArrayExtra(IMAGES)
        val folderIndex = intent.getIntExtra(FOLDER_INDEX, -1)

        if (folderIndex == -1) throw IllegalArgumentException("folder index not found")
        if (imageUrls.isEmpty()) throw IllegalArgumentException("CategorySelectedActivity received empty urls list: " + imageUrls)

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
        val dayInMillis: Long = 1000 * 60 * 60 * 24
        val firstTrigger = System.currentTimeMillis() + dayInMillis
        manager.setRepeating(AlarmManager.RTC_WAKEUP, firstTrigger, dayInMillis, getWallpaperPendingIntent())

        val intent = Intent()
        intent.action = "WallpaperServiceReceiver"
        sendBroadcast(intent)
    }

    private fun getWallpaperPendingIntent(): PendingIntent? {
        /* Retrieve a PendingIntent that will perform a broadcast */
        val wallpaperReceiverIntent = Intent(this@CategorySelectedActivity, WallpaperServiceReceiver::class.java)
        val wallpaperReceiverPendingIntent = PendingIntent.getBroadcast(this@CategorySelectedActivity, 0,
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



