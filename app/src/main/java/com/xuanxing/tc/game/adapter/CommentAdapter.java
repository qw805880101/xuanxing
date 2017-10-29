package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.PhotoActivity;

import java.io.File;
import java.util.List;

/**
 * 评论列表
 *
 * Created by tianchao on 2017/10/16.
 */

public class CommentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    private List<String> list;
    public CommentAdapter(Context context, List<String> list) {
        super(R.layout.item_comment, list);
        this.context = context;
        this.list = list;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {

    }
}
