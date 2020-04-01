package com.crazy.gy.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.gy.R;

import java.util.List;

/**
 * Created on 2020/4/1 09:54
 *
 * @auther super果
 * @annotation
 */
public class SearechAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SearechAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_history, item);
        helper.addOnClickListener(R.id.image_close);
    }
}
