package com.example.emyeraky.ntl_stock.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.emyeraky.ntl_stock.R;
import com.example.emyeraky.ntl_stock.ui.DetailsActivity;
import com.example.emyeraky.ntl_stock.ui.MainActivity;

/**
 * Created by Emy Eraky on 4/16/2017.
 */
public class StockWidgetProvider extends AppWidgetProvider {
//    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
//    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName thisWidget = new ComponentName(context.getApplicationContext(), StockWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            if (appWidgetIds != null && appWidgetIds.length > 0) {
                onUpdate(context, appWidgetManager, appWidgetIds);
            }
        }
        super.onReceive(context, intent);
        //       Bundle extras = intent.getExtras();
//        if(extras!=null) {
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), StockWidgetProvider.class.getName());
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
//
//            onUpdate(context, appWidgetManager, appWidgetIds);
//        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//
//        for (int i = 0; i < appWidgetIds.length; ++i) {
//            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
//            Intent intent = new Intent(context, StockRemoteViewsService.class);
//            rv.setRemoteAdapter(appWidgetIds[i], R.id.list_view, intent);
//
//            rv.setEmptyView(R.id.list_view,R.id.empty_view);
//
//            //Sets up click listener for title to go to Main Activity
//            Intent mainActivityIntent = new Intent(context, MainActivity.class);
//            PendingIntent mainActivityPendingIntent = PendingIntent.getActivity
//                    (context, 1, mainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            rv.setOnClickPendingIntent(R.id.widget_title, mainActivityPendingIntent);
//
//            //Sets up click listener for list items to go to detail view.
//            Intent detailsIntent = new Intent(context, DetailsActivity.class);
//            PendingIntent DetailsPendingIntent = PendingIntent.getActivity(context, 1, detailsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            rv.setPendingIntentTemplate(R.id.list_view, DetailsPendingIntent);
//
//            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
//        }
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
//
//}


////////////////////
        for (int i = 0; i < appWidgetIds.length; ++i) {

            Intent intent = new Intent(context, StockRemoteViewsService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
            rv.setRemoteAdapter(appWidgetIds[i], R.id.list_view, intent);

            rv.setEmptyView(R.id.list_view, R.id.empty_view);

//            Intent toastIntent = new Intent(context, StockWidgetProvider.class);
//            toastIntent.setAction(StockWidgetProvider.TOAST_ACTION);
//            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
//            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.list_view, toastPendingIntent);

            Intent mainActivityIntent = new Intent(context, MainActivity.class);
            PendingIntent mainActivityPendingIntent = PendingIntent.getActivity
                    (context, 1, mainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.widget_title, mainActivityPendingIntent);

            Intent detailsIntent = new Intent(context, DetailsActivity.class);
            PendingIntent DetailsPendingIntent = PendingIntent.getActivity(context, 1, detailsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.list_view, DetailsPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}