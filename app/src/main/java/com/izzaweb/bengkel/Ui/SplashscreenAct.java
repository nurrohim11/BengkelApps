package com.izzaweb.bengkel.Ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.izzaweb.bengkel.R;
import com.izzaweb.bengkel.SessionManager.UserSession;

public class SplashscreenAct extends AppCompatActivity {
    UserSession userSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
            userSession = new UserSession(getApplicationContext());

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    userSession.checkLogin();
                    finish();
                }
            },5000);
        }
}
