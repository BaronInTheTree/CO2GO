package ca.cmpt276.carbonTracker.UI;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;

import com.example.sasha.carbontracker.R;

/**
 * Notification class creates the notification that will be displayed.
 * It sets all needed parameters and what will happen when the notification
 * is pressed.
 */

public class Notification {

    public Notification(Context context, String message, PendingIntent pendingIntent) {

        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("My test notification")
                        .setContentText(message);

        // A notification plays sounds and vibrates when it is displayed
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setVibrate(new long[] { 1000, 1000});

        // Set given pendingIntent to load onto stackBuilder
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());
    }
}
