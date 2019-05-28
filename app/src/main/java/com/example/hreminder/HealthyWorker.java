/*package com.example.hreminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class HealthyWorker extends Worker {
    String CHANNEL_ID = "channel_id";

    public HealthyWorker(Context context, WorkerParameters params){
        super(context,params);

    }

    @Override
    public Result doWork() {
        // Backgroundtask kommt hier rein



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        createNotificationChannel();
        CHANNEL_ID = createNotificationChannel().getId();
        }

        //Intent intent = new Intent(getApplicationContext(), CalenderActivity.class);
        //Pending intent

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("HealthyReminder")
                .setContentText("Background Task erledigt.")
                //Intent für Activity, wenn User auf Notification tapt
              //  .setContentIntent(intent)
               // .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        // notificationId is a unique int for each notification that you must define
        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());

        // Indicate whether the task finished successfully with the Result
        return Result.success();

    }

    private NotificationChannel createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ChannelName";
            String description = "ChannelDescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            try {
                notificationManager.createNotificationChannel(channel);
                return channel;
            } catch (NullPointerException ex){
                Log.e("NullPointer", "NotificationChannel Nullpointer");
            }
        }
        return null;
    }



}
*/
