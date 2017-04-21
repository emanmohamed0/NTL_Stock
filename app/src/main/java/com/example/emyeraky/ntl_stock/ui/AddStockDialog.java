package com.example.emyeraky.ntl_stock.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.emyeraky.ntl_stock.R;
import com.example.emyeraky.ntl_stock.widget.StockWidgetProvider;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddStockDialog extends DialogFragment {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.dialog_stock)
    EditText stock;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") View custom = inflater.inflate(R.layout.add_stock_dialog, null);

        ButterKnife.bind(this, custom);

        stock.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                addStock();
                return true;
            }
        });
        builder.setView(custom);

        builder.setMessage(getString(R.string.dialog_title));
        builder.setPositiveButton(getString(R.string.dialog_add),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addStock();
                    }
                });
        builder.setNegativeButton(getString(R.string.dialog_cancel), null);

        Dialog dialog = builder.create();

        Window window = dialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        return dialog;
    }

    private void addStock() {
        Activity parent = getActivity();
        if (parent instanceof MainActivity) {
            String text = stock.getText().toString();
            if (text != null && text.matches("[A-Za-z0-9 ]*")) {
                ((MainActivity) parent).addStock(text);

                RemoteViews remoteViews = new RemoteViews(this.getActivity().getPackageName(),R.layout.widget);

                Intent clickIntent = new Intent(this.getActivity(),StockWidgetProvider.class);

                Context context = getActivity();
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisWidget = new ComponentName(context, StockWidgetProvider.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

                clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                        appWidgetIds);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, clickIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.widget_title, pendingIntent);
                appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

//                StockWidgetProvider s = new StockWidgetProvider();
//                Context context = getActivity();
//                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//                ComponentName thisWidget = new ComponentName(context, StockWidgetProvider.class);
//                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
//                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
////                s.onUpdate(getContext(), AppWidgetManager.ACTION_APPWIDGET_UPDATE,R.id.list_view);
////                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
            }
        }
        dismissAllowingStateLoss();
    }


}
