package com.example.bomdia;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmeToastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "NOTIFICACAO EMITIDA", Toast.LENGTH_LONG).show();
        MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.vibracaocelul);

        if(mPlayer != null){
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
        Intent it = new Intent(context, Notificacao.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_IMMUTABLE);

        String CHANNEL_ID = "com.example.alarme_22_2";
        String CHANNEL_NAME = "CHANNEL_NAME_APP_NOTIFICATION";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        String titulo = "ALARME";
        String mensagem = "Notificação emitida";
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(android.R.drawable.stat_notify_more).
                setContentTitle(titulo).setContentText(mensagem).setPriority(NotificationCompat.PRIORITY_DEFAULT).
                setContentIntent(pendingIntent).setAutoCancel(true);

        NotificationManagerCompat notif = NotificationManagerCompat.from(context);
        notif.notify(0, mBuilder.build());

    }
}
