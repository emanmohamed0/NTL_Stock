package com.example.emyeraky.ntl_stock.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.emyeraky.ntl_stock.R;
import com.example.emyeraky.ntl_stock.ui.DetailsActivity;
import com.example.emyeraky.ntl_stock.ui.MainActivity;

/**
 * Created by Emy Eraky on 4/16/2017.
 */
public class StockWidgetProvider extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; ++i) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
            Intent intent = new Intent(context, StockRemoteViewsService.class);

            rv.setRemoteAdapter(appWidgetIds[i], R.id.list_view, intent);
            rv.setEmptyView(R.id.list_view, R.id.empty_view);

            Intent mainActivityIntent = new Intent(context, MainActivity.class);
            PendingIntent mainActivityPendingIntent = PendingIntent.getActivity
                    (context, 1, mainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.widget_title, mainActivityPendingIntent);

            Intent detailsIntent = new Intent(context, DetailsActivity.class);
            PendingIntent DetailsPendingIntent = PendingIntent.getActivity(context, 1, detailsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.list_view, DetailsPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
