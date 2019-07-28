package com.example.hreminder.behindTheScenes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.hreminder.R;
import com.example.hreminder.activities.MainActivity;

/**
 * Class for AlarmManager
 */
@SuppressWarnings("deprecation")
public class AlarmNotificationReceiver extends BroadcastReceiver {

    /**
     * when intent is received notify the user
     * @param context context
     * @param intent intent from the startAlarm method in CalenderActivity
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(notificationIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        String contentTitle = context.getResources().getString(R.string.notificationHeader);
        String contentText = context.getResources().getString(R.string.notificationMessage);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(resultPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

    }

}
