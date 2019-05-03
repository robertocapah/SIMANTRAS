package com.kalbenutritionals.simantra.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kalbenutritionals.simantra.ActivityMainMenu;
import com.kalbenutritionals.simantra.CustomView.Utils.Config;
import com.kalbenutritionals.simantra.Network.FastNetworking.FastNetworkingUtils;
import com.kalbenutritionals.simantra.R;

import java.util.Map;


/**
 * Created by dewi.oktaviani on 27/03/2019.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingServ";


    Target target = new Target() {
        @Override
        public void onStart() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void onLoadStarted(Drawable placeholder) {

        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {

        }

        @Override
        public void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {

        }

        @Override
        public void onLoadCleared(Drawable placeholder) {

        }

        @Override
        public void getSize(SizeReadyCallback cb) {

        }

        @Override
        public void removeCallback(@NonNull SizeReadyCallback cb) {

        }

        @Override
        public void setRequest(Request request) {

        }

        @Override
        public Request getRequest() {
            return null;
        }

    };

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> data = remoteMessage.getData();
        if(data.size()>0){
            Config.title = data.get("title").toString();
            Config.content = data.get("content").toString();
            Config.imageUrl = data.get("imgUrl").toString();
            Config.role = data.get("role").toString();
            Config.id = data.get("id").toString();
        }
        if (remoteMessage.getData() != null)
//            Bitmap bitmap = getImage(remoteMessage);

            sendNotification();
//            getImage(remoteMessage);
    }

    private void sendNotification() {

//inflating the views (custom_normal.xml and custom_expanded.xml)
        RemoteViews remoteCollapsedViews = new RemoteViews(getPackageName(), R.layout.custom_normal);
        RemoteViews remoteExpandedViews = new RemoteViews(getPackageName(), R.layout.custom_expanderd);

        remoteCollapsedViews.setTextViewText(R.id.tvTitleNotif,Config.title);
        remoteCollapsedViews.setTextViewText(R.id.tvDescNotif,Config.content);
        remoteExpandedViews.setTextViewText(R.id.tvTitleNotif,Config.title);
        remoteExpandedViews.setTextViewText(R.id.tvDescNotif,Config.content);
        try {
            Bitmap bitmap = Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(Config.imageUrl)
                    .submit(512, 512)
                    .get();

            remoteExpandedViews.setImageViewBitmap(R.id.ivImage, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        remoteExpandedViews.setViewVisibility(R.id.ivImage, View.VISIBLE);

//        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
//        style.bigPicture(bitmap);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(getApplicationContext(), ActivityMainMenu.class);
        intent.putExtra("idNotification", Config.id);
        intent.putExtra("idRole", "1");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle bundle = new Bundle();
        bundle.putString("buzz", "buzz");
        intent.putExtras(bundle);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(ActivityMainMenu.class);
        stackBuilder.addNextIntent(intent);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "101";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX);

            //Configure Notification Channel
            notificationChannel.setDescription("Game Notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);


            notificationManager.createNotificationChannel(notificationChannel);
        }
        /*NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_notification)
                .setContentTitle(Config.title)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText(Config.content)
                .setContentIntent(pendingIntent)
//                .setStyle(style)
//                .setLargeIcon(bitmap)
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(remoteCollapsedViews)
                .setCustomBigContentView(remoteExpandedViews)
                .setPriority(Notification.PRIORITY_MAX);*/

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "123");
        //icon
        builder.setSmallIcon(R.drawable.ic_simantra);
        //set priority
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //dismiss on tap
        builder.setAutoCancel(true);
        //start intent on notification tap (MainActivity)
        builder.setContentIntent(pendingIntent);
        //custom style
        builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        builder.setCustomContentView(remoteCollapsedViews);
        builder.setCustomBigContentView(remoteExpandedViews);

        //notification manager
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);



        String CHANNEL_ID = "kalbenutritionals_channel";
        CharSequence name = "kalbenutritionals_channel";
        String Description = "kalbenutritionals channel";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);

            if (notificationManager != null) {

                notificationManager.createNotificationChannel(mChannel);
                notificationManager.notify(123, builder.build());
            }

        } else {
            Notification note = builder.build();
            note.defaults |= Notification.DEFAULT_VIBRATE;
            note.defaults |= Notification.DEFAULT_SOUND;
            if (notificationManager != null) {

                notificationManager.notify(Integer.parseInt(Config.id), note);
            }
        }


    }

    private void getImage(final RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();
        Config.title = data.get("title");
        Config.content = data.get("content");
        Config.imageUrl = data.get("imgUrl");
        Config.role = data.get("role");
        Config.id = data.get("id");
        //Create thread to fetch image from notification
        if (remoteMessage.getData() != null) {

            Handler uiHandler = new Handler(Looper.getMainLooper());
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    // Get image from data Notification
                    Glide.with(getApplicationContext()).load(Config.imageUrl).into(target);
                }
            });
        }
    }

}
