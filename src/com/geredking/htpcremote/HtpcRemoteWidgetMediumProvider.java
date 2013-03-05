package com.geredking.htpcremote;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.util.Log;

public class HtpcRemoteWidgetMediumProvider extends HtpcRemoteWidgetProvider
{
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		Log.d("HtpcRemoteWidgetMediumProvider", "onUpdate");
		setupWidget(context, appWidgetManager, appWidgetIds, R.layout.widget_remote_control_medium);
	}
}
