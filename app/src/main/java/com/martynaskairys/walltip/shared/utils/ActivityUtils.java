package com.martynaskairys.walltip.shared.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

public class ActivityUtils {

    @SafeVarargs
    public static void startActivityWithTransitionAnimation(Activity activity, Intent intent, Pair<View, String>... sharedViews) {
        ActivityOptionsCompat ao = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedViews);
        Bundle options = ao.toBundle();
        activity.startActivity(intent, options);
    }
}