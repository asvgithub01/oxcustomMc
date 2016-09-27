/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.device.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.gigigo.ggglib.device.AndroidSdkVersion;
import com.gigigo.orchextra.R;
import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification;

public class AndroidNotificationBuilder {

    public static final String EXTRA_NOTIFICATION_ACTION = "OX_EXTRA_NOTIFICATION_ACTION";

    private final Context context;

    public AndroidNotificationBuilder(Context context) {
        this.context = context;
    }

    public void createNotification(OrchextraNotification orchextraNotification, PendingIntent pendingIntent) {

        Notification notification;
        if (AndroidSdkVersion.hasJellyBean16()) {
            notification = createBigNotification(orchextraNotification, pendingIntent);
        } else {
            notification = createNormalNotification(orchextraNotification, pendingIntent);
        }


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) (System.currentTimeMillis() % Integer.MAX_VALUE), notification);
    }

    private Notification createBigNotification(OrchextraNotification orchextraNotification, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = getNotificationBuilder(orchextraNotification, pendingIntent);
        return expandedNotification(builder, orchextraNotification.getBody());
    }

    private Notification createNormalNotification(OrchextraNotification orchextraNotification, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = getNotificationBuilder(orchextraNotification, pendingIntent);
        return builder.build();
    }

    private NotificationCompat.Builder getNotificationBuilder(OrchextraNotification orchextraNotification, PendingIntent pendingIntent) {
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ox_notification_large_icon);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setLargeIcon(largeIcon)
                        .setSmallIcon(getSmallIconResourceId())
                        .setContentTitle(orchextraNotification.getTitle())
                        .setContentText(orchextraNotification.getBody())
                        .setTicker(orchextraNotification.getTitle())
                        .setWhen(System.currentTimeMillis())
                        .setColor(context.getResources().getColor(R.color.ox_notification_background_color))
                        .setAutoCancel(true);


        RemoteViews mContentView = new RemoteViews(this.context.getPackageName(), R.layout.ox_custom_local_notification);
        mContentView.setImageViewResource(R.id.notifimage_custom_local_notification, R.drawable.ox_notification_large_icon);
        //not exists for now  mContentView.setImageViewResource(R.id.notifimage_small_custom_local_notification,getSmallIconResourceId());
        mContentView.setTextViewText(R.id.notiftitle_custom_local_notification, orchextraNotification.getTitle());
        mContentView.setTextViewText(R.id.notiftext_custom_local_notification, orchextraNotification.getBody());
        mContentView.setLong(R.id.time, "setTime", builder.build().when);
        mContentView.setLong(R.id.time_custom_local_notification, "setTime", builder.build().when);

        builder.setContent(mContentView); //set content


        final Notification notification = builder.build();

        if (android.os.Build.VERSION.SDK_INT >= 16)
            notification.bigContentView = mContentView;


        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }

        return builder;
    }

    private android.app.Notification expandedNotification(NotificationCompat.Builder builder, String body) {
        return new NotificationCompat.BigTextStyle(builder)
                .bigText(body).build();
    }

    public PendingIntent getPendingIntent(AndroidBasicAction androidBasicAction) {
        Intent intent = new Intent(context, NotificationReceiver.class)
                .setAction(NotificationReceiver.ACTION_NOTIFICATION_BROADCAST_RECEIVER)
                .putExtra(NotificationReceiver.NOTIFICATION_BROADCAST_RECEIVER, NotificationReceiver.NOTIFICATION_BROADCAST_RECEIVER)
                .putExtra(EXTRA_NOTIFICATION_ACTION, androidBasicAction)
                .setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        return PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        PackageManager pm = context.getPackageManager();
//
//        Intent intent = pm.getLaunchIntentForPackage(context.getPackageName());
//        if (intent != null) {
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.setAction(String.valueOf(System.currentTimeMillis()));
//            intent.putExtra(EXTRA_NOTIFICATION_ACTION, androidBasicAction);
//        }
//
//        return PendingIntent.getActivity(context, 1, intent, 0);
    }

    private int getSmallIconResourceId() {
        return AndroidSdkVersion.hasLollipop21() ? R.drawable.ox_notification_alpha_small_icon : R.drawable.ox_notification_color_small_icon;
    }
}
