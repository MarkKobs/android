package com.mmj.localservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;

import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.string.no;

/**
 * Created by Administrator on 2017/11/15.
 */

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    private static final int NOTIF_ID=1;
    @Override
    public void onReceive(Context context, Intent intent){
        NotificationCompat.Builder mBuilder= (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("i'm a notification")
                .setContentTitle("NOTIFICATION");
        Intent resultIntent =new Intent(context,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIF_ID, mBuilder.build());

    }
}
