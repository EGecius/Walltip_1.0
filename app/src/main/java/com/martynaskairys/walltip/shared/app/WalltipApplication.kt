package com.martynaskairys.walltip.shared.app

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

/**
 * Launched when application starts
 */
class WalltipApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initCrashReporting()
    }

    private fun initCrashReporting() {
        Fabric.with(this, Crashlytics())
    }
}