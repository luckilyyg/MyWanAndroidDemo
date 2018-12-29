package com.crazy.gy.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crazy.gy.R;

public class BaseActivity extends AppCompatActivity {
    public static String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        TAG = getClass().getSimpleName();//获取Activity名称
    }
}
