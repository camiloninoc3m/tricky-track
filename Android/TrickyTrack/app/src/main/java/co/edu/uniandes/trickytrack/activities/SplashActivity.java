package co.edu.uniandes.trickytrack.activities;

import android.app.Activity;
import android.content.Intent;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.edu.uniandes.trickytrack.R;

public class SplashActivity extends Activity {
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000); // 4 seconds
    }
}
