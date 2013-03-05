package com.geredking.htpcremote;

import com.geredking.htpcremote.remotes.RemoteButton;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public abstract class HtpcRemoteWidgetProvider extends AppWidgetProvider 
{
	protected void setupWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, int viewId)
	{
		Log.d("HtpcRemoteWidgetProvider", "Setting button click event handling intents");
		
		RemoteViews views = new RemoteViews(context.getPackageName(), viewId);
		ComponentName widget = new ComponentName(context, this.getClass());
		
		setButtonClick(context, views, R.id.remoteButtonOK, RemoteButton.OK);
		setButtonClick(context, views, R.id.remoteButtonUp, RemoteButton.DPAD_UP);
		setButtonClick(context, views, R.id.remoteButtonLeft, RemoteButton.DPAD_LEFT);
		setButtonClick(context, views, R.id.remoteButtonRight, RemoteButton.DPAD_RIGHT);
		setButtonClick(context, views, R.id.remoteButtonDown, RemoteButton.DPAD_DOWN);
		setButtonClick(context, views, R.id.remoteButtonOsd, RemoteButton.OSD);
		setButtonClick(context, views, R.id.remoteButtonMenu, RemoteButton.MENU);
		setButtonClick(context, views, R.id.remoteButtonPower, RemoteButton.POWER);
		setButtonClick(context, views, R.id.remoteButtonBack, RemoteButton.BACK);
		setButtonClick(context, views, R.id.remoteButtonPlay, RemoteButton.PLAY);
		setButtonClick(context, views, R.id.remoteButtonStop, RemoteButton.STOP);
		setButtonClick(context, views, R.id.remoteButtonPrevious, RemoteButton.SKIP_PREVIOUS);
		setButtonClick(context, views, R.id.remoteButtonNext, RemoteButton.SKIP_NEXT);

		appWidgetManager.updateAppWidget(widget, views);
	}
	
	private void setButtonClick(Context context, RemoteViews views, int viewId, String action)
	{
		Intent intent = new Intent(context, HtpcRemoteService.class);
		intent.setAction(action);
		intent.addCategory(HtpcRemoteService.INTENT_CATEGORY_BUTTON_PRESS);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
		views.setOnClickPendingIntent(viewId, pendingIntent);
	}
}