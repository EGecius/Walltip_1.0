package com.martynaskairys.walltip.shared.utils

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View

object ActivityUtils {

    @SafeVarargs
    fun startActivityWithTransitionAnimation(activity: Activity, intent: Intent, vararg sharedViews: Pair<View, String>) {
        val ao = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *sharedViews)
        val options = ao.toBundle()
        activity.startActivity(intent, options)
    }
}