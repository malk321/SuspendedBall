package com.meizu.trustmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        View floatView = LayoutInflater.from(this).inflate(R.layout.layout_float, null);
        SuspendedBall suspendedBall = new SuspendedBall(this);
        suspendedBall.setSuspendedBall(floatView);
    }
}
