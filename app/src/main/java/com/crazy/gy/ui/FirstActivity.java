package com.crazy.gy.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crazy.gy.MainActivity;
import com.crazy.gy.R;
import com.crazy.gy.util.Sharedpreferences_Utils;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Sharedpreferences_Utils.getInstance(FirstActivity.this).getString("niName").equals("")) {
                    startActivity(new Intent(FirstActivity.this, LoginActivity.class));
                    FirstActivity.this.finish();
                } else {
                    startActivity(new Intent(FirstActivity.this, MainActivity.class));
                    FirstActivity.this.finish();
                }
            }
        }, 2000);

    }
}
