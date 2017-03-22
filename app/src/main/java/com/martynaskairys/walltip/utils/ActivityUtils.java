package com.martynaskairys.walltip.utils;

public class ActivityUtils {
    @SafeVarargs
    public static void startActivityWithTransitionAnimation(Activity activity, Intent intent, Pair<View, String>... sharedViews) {
        ActivityOptionsCompat ao = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedViews);
        Bundle options = ao.toBundle();
        activity.startActivity(intent, options);
    }
}