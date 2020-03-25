package com.crazy.gy.adapter;

import android.app.ActivityOptions;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.gy.R;
import com.crazy.gy.entity.FeedArticleBean;
import com.crazy.gy.entity.NavigationListBean;
import com.crazy.gy.util.CommonUtils;
import com.crazy.gy.viewholder.NavigationViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;


/**
 * @author quchao
 * @date 2018/3/2
 */

public class NavigationAdapter extends BaseQuickAdapter<NavigationListBean, NavigationViewHolder> {

    public NavigationAdapter(int layoutResId, @Nullable List<NavigationListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(NavigationViewHolder helper, NavigationListBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.item_navigation_tv, item.getName());
        }
        final TagFlowLayout mTagFlowLayout = helper.getView(R.id.item_navigation_flow_layout);
        final List<FeedArticleBean> mArticles = item.getArticles();
        mTagFlowLayout.setAdapter(new TagAdapter<FeedArticleBean>(mArticles) {
            @Override
            public View getView(FlowLayout parent, int position, FeedArticleBean feedArticleData) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_layout_tv, mTagFlowLayout, false);
                if (feedArticleData == null) {
                    return null;
                }
                String name = feedArticleData.getTitle();
                tv.setPadding(CommonUtils.dp2px(10), CommonUtils.dp2px(10),
                        CommonUtils.dp2px(10), CommonUtils.dp2px(10));
                tv.setText(name);
                tv.setTextColor(CommonUtils.randomColor());
                mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position1, FlowLayout parent) {
                        startNavigationPager(view, position1, parent, mArticles);
                        return false;
                    }
                });
                return tv;
            }
        });
    }

    private void startNavigationPager(View view, int position1, FlowLayout parent2, List<FeedArticleBean> mArticles) {

        Log.e(TAG, "id: " + mArticles.get(position1).getId());

        Log.e(TAG, "title: " + mArticles.get(position1).getTitle());

        Log.e(TAG, "link: " + mArticles.get(position1).getLink());

        Log.e(TAG, "collect: " + mArticles.get(position1).isCollect());
    }

}
