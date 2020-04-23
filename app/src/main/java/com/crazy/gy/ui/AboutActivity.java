package com.crazy.gy.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crazy.gy.R;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * xiabibi
 * 123456
 */
public class AboutActivity extends AppCompatActivity {

    @Bind(R.id.img_titleleft)
    ImageView imgTitleleft;
    @Bind(R.id.tv_titlecontent)
    TextView tvTitlecontent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        tvTitlecontent.setText("关于");
        imgTitleleft.setVisibility(View.VISIBLE);
        imgTitleleft.setImageResource(R.drawable.ic_left);
    }



    @OnClick(R.id.img_titleleft)
    public void onViewClicked() {
        AboutActivity.this.finish();
    }
}
