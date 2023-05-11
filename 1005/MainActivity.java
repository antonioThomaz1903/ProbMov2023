package com.example.bomdia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import com.example.bomdia.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private AlarmManager alarmMgr;
    boolean isRepeat = false;
    private Button btnOneTime, btnRepeat;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btnOneTime = binding.btnOneTime;
        btnRepeat = binding.btnRepeat;
        btnOneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRepeat = false;
                startAlarm();
            }
        });
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRepeat = true;
                startAlarm();
            }
        });
    }

    private void startAlarm(){
        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmeToastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        if(!isRepeat){
            alarmMgr.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1/2*60*1000, pendingIntent);
        }
        else{
            alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+1/2*60*1000, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);

        }

    }

    public void cancelAlarm(View view){
        if(alarmMgr!=null){
            alarmMgr.cancel(pendingIntent);
        }
    }

}