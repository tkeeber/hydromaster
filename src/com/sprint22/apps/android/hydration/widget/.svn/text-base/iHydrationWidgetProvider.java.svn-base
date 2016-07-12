package com.sprint22.apps.android.hydration.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.sprint22.apps.android.hydration.R;
import com.sprint22.apps.android.hydration.configuration.SPConstants;

public class iHydrationWidgetProvider extends AppWidgetProvider {

	public void onEnabled(Context context) {
		super.onEnabled(context);
		AppWidgetManager gm = AppWidgetManager.getInstance(context);
		updateAppWidget(context, gm, 0);
	}

	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		super.onUpdate(context, appWidgetManager, appWidgetIds);

		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			updateAppWidget(context, appWidgetManager, i);
		}
	}

	public RemoteViews buildUpdate(Context context) {

		RemoteViews updateViews = null;

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		long glassesDrankToday = sharedPreferences.getLong(SPConstants.KEY_GLASSES_DRANK_TODAY, 0);
		float glassesToDrinkToday = sharedPreferences.getFloat(SPConstants.KEY_WATER_GLASSES_A_DAY,
				SPConstants.DEFAULT_GLASSES_A_DAY);

		// Build an update that holds the updated widget contents
		updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_hydration);
		updateViews.setTextViewText(R.id.info, glassesDrankToday + "/" + glassesToDrinkToday);

		// When user clicks on widget, refresh.
		Intent clickintent = new Intent("com.sprint22.apps.android.ihydration.widget.CLICK");
		PendingIntent pendingIntentClick = PendingIntent.getBroadcast(context, 0, clickintent, 0);
		updateViews.setOnClickPendingIntent(R.id.smallBase, pendingIntentClick);

		return updateViews;
	}

	public void onReceive(Context context, Intent intent) {
		// v1.5 fix that doesn't call onDelete Action
		final String action = intent.getAction();
		if (intent.getAction().equals("com.sprint22.apps.android.ihydration.widget.CLICK")) {
			Intent i = new Intent(context, WidgetOptionsActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		} else if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
			final int appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				this.onDeleted(context, new int[] { appWidgetId });
			}
		} else {
			super.onReceive(context, intent);
		}
	}

	public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
			int appWidgetId) {

		RemoteViews updateViews = null;

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		long glassesDrankToday = sharedPreferences.getLong(SPConstants.KEY_GLASSES_DRANK_TODAY, 0);
		float glassesToDrinkToday = sharedPreferences.getFloat(SPConstants.KEY_WATER_GLASSES_A_DAY,
				SPConstants.DEFAULT_GLASSES_A_DAY);

		// Build an update that holds the updated widget contents
		updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_hydration);
		updateViews.setTextViewText(R.id.info, glassesDrankToday + "/" + glassesToDrinkToday);

		// When user clicks on widget, refresh.
		Intent clickintent = new Intent("com.sprint22.apps.android.ihydration.widget.CLICK");
		PendingIntent pendingIntentClick = PendingIntent.getBroadcast(context, 0, clickintent, 0);
		updateViews.setOnClickPendingIntent(R.id.smallBase, pendingIntentClick);

		// Push update for this widget to the home screen
		ComponentName thisWidget = new ComponentName(context, iHydrationWidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(thisWidget, updateViews);
	}
}
