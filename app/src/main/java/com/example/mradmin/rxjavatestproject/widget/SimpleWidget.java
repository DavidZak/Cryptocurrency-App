package com.example.mradmin.rxjavatestproject.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.mradmin.rxjavatestproject.MainApplication;
import com.example.mradmin.rxjavatestproject.R;
import com.example.mradmin.rxjavatestproject.model.CryptoEntity;
import com.example.mradmin.rxjavatestproject.util.LastSeen;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yks-11 on 12/28/17.
 */

public class SimpleWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        getCryptoDetail(context, appWidgetManager, appWidgetId);

    }

    private void initData (Context context, AppWidgetManager appWidgetManager,
                           int appWidgetId, List<CryptoEntity> cryptoEntities) {

        if (cryptoEntities.size() > 1 || cryptoEntities.size() < 1) {
            return;
        }

        CryptoEntity cryptoEntity = cryptoEntities.get(0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.simple_app_widget);

        //views.setTextViewText(R.id.textViewCryptoInfo, number);
        //getCryptoDetail(views);
        views.setTextViewText(R.id.textViewCryptoInfo, "Bitcoin | BTC");
        views.setTextViewText(R.id.textViewCryptoInfoValue, String.valueOf(cryptoEntity.getPriceUSD()) + " $");

        long lastUpdated = cryptoEntity.getLastUpdated();

        LastSeen timeSinceAgo = new LastSeen();
        String lastSeen = timeSinceAgo.getFullStringDate(lastUpdated);

        views.setTextViewText(R.id.textViewCryptoInfoLastUpdated, lastSeen);

        // Construct an Intent object includes web adresss.
        Intent intent = new Intent(context, SimpleWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] idArray = new int[]{appWidgetId};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);
        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.imageButtonUpdateCryptoInfo, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    private void getCryptoDetail (Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        MainApplication.getCryptoAPI().getCryptoDetail("bitcoin")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            initData(context, appWidgetManager,
                            appWidgetId,  result);
                        }
                        , Throwable::printStackTrace);
    }

}
