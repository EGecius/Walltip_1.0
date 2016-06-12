package com.martynaskairys.walltip

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_start)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        val onClickListener: (View) -> Unit = {
            val intent = Intent(this, ChoosingFolderActivity::class.java)
            startActivity(intent)
        }

        findViewById(R.id.button)!!.setOnClickListener(onClickListener)
        findViewById(R.id.arrowBtn)!!.setOnClickListener(onClickListener)
    }
}
