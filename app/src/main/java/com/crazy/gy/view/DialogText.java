package com.crazy.gy.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazy.gy.R;


/**
 * 功能：
 *自定义提示框
 */

public class DialogText extends Dialog{
    Context mContext;//上下文
    //回传的值
    String texts="";//提示文字
    String state="";//状态
    Animation mAnimation;//补间动画
    //控件
    ImageView mImageView;//加载图
    TextView mTextView;//加载提示文字
    public DialogText(Context context) {
        super(context);
    }
    public DialogText(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }
    /**
     * 构造方法
     * context 上下文
     * texts 传过来的提示文字
     * state 传过来的返回状态
     */
    public DialogText(Context context, String texts, String state){
        super(context);
        mContext=context;
        this.texts=texts;
        this.state=state;
        initListen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_text);
        this.setCancelable(true);// 设置点击屏幕Dialog不消失
        //去黑角
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        initView();
        initListen();
    }



    private void initView() {
        mTextView = (TextView) findViewById(R.id.hint_text_title);
        mImageView = (ImageView) findViewById(R.id.hint_text_image);
        mAnimation = AnimationUtils.loadAnimation(mContext,
                R.anim.dialog_rotate);
    }
    public void setText(String text, String state) {
        mTextView.setText(text);
        if ("".equals(state)) {
            //补间动画
            mImageView.setImageResource(R.drawable.loading);
            mAnimation.start();
            mImageView.startAnimation(mAnimation);
        } else if ("success".equals(state)) {
            //访问成功
            mImageView.setImageResource(R.drawable.dialog_success);
            mAnimation.cancel();
            mImageView.clearAnimation();
        } else if ("fail".equals(state)) {
            //访问失败
            mImageView.setImageResource(R.drawable.dialog_fail);
            mAnimation.cancel();
            mImageView.clearAnimation();
        }
    }
    /**
     * 功能：接受传的值改变textview和imageview的值
     * 事件监听
     */
    private void initListen() {
        mTextView.setText(texts);
        if ("".equals(state)) {
            //补间动画
            mImageView.setImageResource(R.drawable.loading);
            mAnimation.start();
            mImageView.startAnimation(mAnimation);
        } else if ("success".equals(state)) {
            //访问成功
            mImageView.setImageResource(R.drawable.dialog_success);
            mAnimation.cancel();
            mImageView.clearAnimation();
        } else if ("fail".equals(state)) {
            //访问失败
            mImageView.setImageResource(R.drawable.dialog_fail);
            mAnimation.cancel();
            mImageView.clearAnimation();
        }
    }

}
