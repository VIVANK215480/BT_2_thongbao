package com.example.bt_2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNotify = findViewById(R.id.btnNotify);
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

        // Yêu cầu quyền POST_NOTIFICATIONS trên Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 1);
        }
    }

    private void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Âm thanh từ file trong res/raw
        Uri soundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.war1);

        // Tạo kênh thông báo (Android 8.0 trở lên)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Kênh Thông Báo",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Kênh thông báo của ứng dụng");
            channel.setSound(soundUri, null); // Gán âm thanh cho kênh
            notificationManager.createNotificationChannel(channel);
        }

        // Intent mở app khi nhấn thông báo
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Xây dựng thông báo
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Icon thông báo
                .setContentTitle("Có thông báo khẩn !") // Tiêu đề
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Cảnh báo khẩn cấp! Kiểm tra ngay.")) // Hiển thị nội dung dài hơn
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(soundUri) // Thêm âm thanh từ raw
                .setContentIntent(pendingIntent) // Khi bấm vào sẽ mở app
                .setAutoCancel(true); // Xóa thông báo khi người dùng bấm vào nó

        // Gửi thông báo
        notificationManager.notify(NOTIFICATION_ID, builder.build());

        // Rung điện thoại khi thông báo
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(500);
            }
        }
    }
}
