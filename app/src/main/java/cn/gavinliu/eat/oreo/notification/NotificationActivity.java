package cn.gavinliu.eat.oreo.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.gavinliu.eat.oreo.MainActivity;
import cn.gavinliu.eat.oreo.R;

import static android.app.Notification.BADGE_ICON_SMALL;

/**
 * Created by gavin on 2017/8/22.
 */

public class NotificationActivity extends AppCompatActivity {

    private NotificationManager mNotificationManager;

    private String groupId = "groupId";
    private CharSequence groupName = "Group1";

    private String groupId2 = "groupId2";
    private CharSequence groupName2 = "Group2";
    private String chatChannelId2 = "chatChannelId2";
    private String adChannelId2 = "adChannelId2";

    private String chatChannelId = "chatChannelId";
    private String chatChannelName = "聊天通知";
    private String chatChannelDesc = "这是一个聊天通知，建议您置于开启状态，这样才不会漏掉女朋友的消息哦";
    private int chatChannelImportance = NotificationManager.IMPORTANCE_MAX;

    private String adChannelId = "adChannelId";
    private String adChannelName = "广告通知";
    private String adChannelDesc = "这是一个广告通知，可以关闭的，但是如果您希望我们做出更好的软件服务于你，请打开广告支持一下吧";
    private int adChannelImportance = NotificationManager.IMPORTANCE_LOW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createGroup();
    }

    public void notification(View view) {
        createNotificationChannel(chatChannelId, chatChannelName, chatChannelImportance, chatChannelDesc, groupId2);

        Notification.Builder builder = new Notification.Builder(this, chatChannelId);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Gavin")
                .setContentText("Today released Android 8.0 version of its name is Oreo")
                .setBadgeIconType(BADGE_ICON_SMALL)
                .setNumber(1)
                .setAutoCancel(true);

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        mNotificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    public void notification2(View view) {
        createNotificationChannel(adChannelId, adChannelName, adChannelImportance, adChannelDesc, groupId2);

        Notification.Builder builder = new Notification.Builder(this, adChannelId);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ad")
                .setContentText("Oreo is Coming.")
                .setBadgeIconType(BADGE_ICON_SMALL)
                .setNumber(1)
                .setAutoCancel(true);

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        mNotificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    public void createNotificationChannel(String id, String name, int importance, String desc, String groupId) {
        if (mNotificationManager.getNotificationChannel(id) != null) return;

        NotificationChannel notificationChannel = new NotificationChannel(id, name, importance);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);

        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationChannel.setShowBadge(true);
        notificationChannel.setBypassDnd(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400});
        notificationChannel.setDescription(desc);
        notificationChannel.setGroup(groupId);
//        notificationChannel.setSound();

        mNotificationManager.createNotificationChannel(notificationChannel);
    }

    public void delNotification(View view) {
        mNotificationManager.deleteNotificationChannel(chatChannelId);
    }

    public void delNotification2(View view) {
        mNotificationManager.deleteNotificationChannel(adChannelId);
    }

    public void setting(View view) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, chatChannelId);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(intent);
    }

    public void createGroup() {
        mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, groupName));
        mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId2, groupName2));

        createNotificationChannel(chatChannelId2, chatChannelName, chatChannelImportance, chatChannelDesc, groupId);
        createNotificationChannel(adChannelId2, adChannelName, adChannelImportance, adChannelDesc, groupId);
    }

    public void delNotificationGroup(View view) {
        mNotificationManager.deleteNotificationChannelGroup(groupId2);
    }

}
