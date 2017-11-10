package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.PhotoActivity;
import com.xuanxing.tc.game.bean.CommentList;
import com.xuanxing.tc.game.utils.XUtils;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 评论列表
 * <p>
 * Created by tianchao on 2017/10/16.
 */

public class CommentAdapter extends BaseQuickAdapter<CommentList, BaseViewHolder> {
    private Context context;
    private List<CommentList> list;

    public CommentAdapter(Context context, List<CommentList> list) {
        super(R.layout.item_comment, list);
        this.context = context;
        this.list = list;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CommentList item) {

        if (list.size() == 3) {
            if (helper.getLayoutPosition() == 2) {
                helper.setVisible(R.id.lin_comment, false);
            }
        }

        CircleImageView circleImageView = helper.getView(R.id.iv_head);
        XUtils.loadHeadIcon(mContext, item.getFromHeadIcon(), circleImageView);

        helper.setText(R.id.txt_comment_name, item.getFromNickName())
                .setText(R.id.txt_comment_time, item.getCommentTime())
                .setText(R.id.txt_comment_content, item.getContent());

        RecyclerView recyclerView = helper.getView(R.id.rv_comment_reply);
        if (item.getToMid() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }

    }
}
