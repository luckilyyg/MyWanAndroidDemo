package com.crazy.gy.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.gy.R;
import com.crazy.gy.entity.TodoSection;

import java.util.List;

/**
 * 作者：Administrator
 * 时间：2018/8/20
 * 功能：
 */

public class ToDoListAdapter extends BaseSectionQuickAdapter<TodoSection, BaseViewHolder> {
   private boolean isDone;
    public ToDoListAdapter(boolean isDone, List<TodoSection> mData) {
        super(R.layout.todo_item_view, R.layout.todo_item_head, mData);
        this.isDone=isDone;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, TodoSection item) {
        helper.setText(R.id.todo_head, item.header);
        if (isDone) {
            helper.setTextColor(R.id.todo_head, mContext.getResources().getColor(R.color.done_todo_date));
        } else {
            helper.setTextColor(R.id.todo_head, mContext.getResources().getColor(R.color.notdone_todo_date));
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, TodoSection item) {
        helper.setText(R.id.item_name, item.t.getTitle());
        if (TextUtils.isEmpty(item.t.getContent())) {
            helper.setGone(R.id.item_des, false);
        } else {
            helper.setGone(R.id.item_des, true);
            helper.setText(R.id.item_des, item.t.getContent());
        }

        if (isDone) {
            helper.setGone(R.id.item_done_time, true);
            helper.setText(R.id.item_done_time, String.format("完成时间：%s",item.t.getCompleteDateStr()));
            helper.setImageResource(R.id.item_complete, R.drawable.cancel_todo);
        } else {
            helper.setGone(R.id.item_done_time, false);
            helper.setImageResource(R.id.item_complete, R.drawable.complete_todo);
        }

        helper.addOnClickListener(R.id.item_complete);
        helper.addOnClickListener(R.id.item_delete);
    }
}
