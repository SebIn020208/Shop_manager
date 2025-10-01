package com.shop_manager.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.shop_manager.R;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "shop_manager_notifications";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        // DB 인스턴스 가져오기
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("userTokens");

        // 예시: 사용자 ID 또는 기기 고유 ID로 저장
        String userId = "test_user"; // TODO: 로그인 연동 시 사용자 ID 가져오기

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("updatedAt", System.currentTimeMillis());

        // userTokens/test_user 에 저장
        ref.child(userId).setValue(data)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FCM", "토큰 저장 성공: " + token);
                })
                .addOnFailureListener(e -> {
                    Log.e("FCM", "토큰 저장 실패", e);
                });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = "가게매니저";
        String body = "새 알림이 도착했습니다.";

        // 1. Notification payload 있는 경우
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
        }
        // 2. Data payload 있는 경우
        else if (!remoteMessage.getData().isEmpty()) {
            title = remoteMessage.getData().get("title") != null ? remoteMessage.getData().get("title") : title;
            body = remoteMessage.getData().get("body") != null ? remoteMessage.getData().get("body") : body;
        }

        sendNotification(title, body);
    }

    private void sendNotification(String title, String messageBody) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (manager == null) return; // 안전 처리

        // Android 8.0 이상은 채널 생성 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "가게매니저 알림",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification) // ⚠️ 알림 전용 아이콘 권장 (투명 배경)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        // System.currentTimeMillis() → 고유 ID로 사용 (알림이 덮어쓰이지 않음)
        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
