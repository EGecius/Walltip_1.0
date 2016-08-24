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


class ExitAppActivity : AppCompatActivity() {
    private var pendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit_app)

        showArrow()


        /* Retrieve a PendingIntent that will perform a broadcast */
        val alarmIntent = Intent(this@ExitAppActivity, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this@ExitAppActivity, 0, alarmIntent, 0)

        changeWallpaperNow()
        scheduleWallpapersToWork()


        findViewById(R.id.buttonExitApp)!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            Toast.makeText(this@ExitAppActivity, R.string.exit_app_button_message, Toast.LENGTH_LONG).show()
        }

        saveUrls()
    }

    private fun saveUrls() {
        val imageUrls = intent.getStringArrayExtra("images")
        ImageStorage(applicationContext).saveUrls(imageUrls)
    }

    private fun showArrow() {
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha)
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.primary), PorterDuff.Mode.SRC_ATOP)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
    }

    private fun changeWallpaperNow() {
        val intent = Intent(this, WallpaperService::class.java)
        startService(intent)
    }



    fun scheduleWallpapersToWork() {

        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val dayInMillis: Long = 1000 * 60 * 60 * 24
        val firstTrigger = System.currentTimeMillis() + dayInMillis
        manager.setRepeating(AlarmManager.RTC_WAKEUP, firstTrigger, dayInMillis, pendingIntent)

        val intent = Intent()
        intent.action = "AlarmReceiver"
        sendBroadcast(intent)

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



