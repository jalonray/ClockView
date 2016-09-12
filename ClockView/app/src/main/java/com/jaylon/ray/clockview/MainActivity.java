package com.jaylon.ray.clockview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    boolean finish = false;
    ClockView clockView;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            clockView.update((long)msg.obj);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clockView = new ClockView(this);
        setContentView(clockView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        finish = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread timerTask = new Thread(new Runnable() {
            @Override
            public void run() {
                long time;
                while (!finish) {
                    time = System.currentTimeMillis();
                    if (time % 1000 == 0) {
                        Message msg = handler.obtainMessage();
                        msg.obj = time;
                        handler.sendMessage(msg);
                    }
                }
            }
        });
        timerTask.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish = true;
    }
}
