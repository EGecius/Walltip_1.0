package com.martynaskairys.walltip.widgets;

import android.appwidget.AppWidgetProvider;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.RemoteViews;

import com.martynaskairys.walltip.R;

import java.util.Random;

public class WidgetHealth extends AppWidgetProvider {


    public CharSequence[] getFacts(Resources res) {
        return res.getTextArray(R.array.tips_health);
    }

    public CharSequence getFact(Resources res) {
        CharSequence fact = "";

        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(getFacts(res).length);

        fact = getFacts(res)[randomNumber];

        return fact;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {


        ComponentName thisWidget = new ComponentName(context,
                WidgetHealth.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {

            CharSequence fact = this.getFact(context.getResources());

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
            remoteViews.setTextViewText(R.id.update, String.valueOf(fact));

            Intent intent = new Intent(context, WidgetHealth.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }
    }
}