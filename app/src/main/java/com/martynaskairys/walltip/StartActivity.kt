package com.martynaskairys.walltip

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_start)

        val btn = findViewById(R.id.button) as Button?
        btn!!.setOnClickListener {
            val `in` = Intent(this@StartActivity, ChoosingFolderActivity::class.java)
            startActivity(`in`)
        }

        val arrow = findViewById(R.id.imageButton) as ImageButton?
        arrow!!.setOnClickListener {
            val `in` = Intent(this@StartActivity, ChoosingFolderActivity::class.java)
            startActivity(`in`)
        }
    }
}
