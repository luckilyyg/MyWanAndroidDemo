package com.crazy.gy.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazy.gy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
