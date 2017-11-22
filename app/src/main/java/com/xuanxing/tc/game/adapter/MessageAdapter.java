package com.xuanxing.tc.game.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.MessageActivity;
import com.xuanxing.tc.game.bean.NoticeInfo;
import com.xuanxing.tc.game.utils.DateUtils;

import java.util.List;

/**
 * 消息列表
 * <p>
 * Created by admin on 2017/8/31.
 */

public class MessageAdapter extends BaseQuickAdapter<NoticeInfo, BaseViewHolder> {

    private MessageActivity messageActivity;

    private List<NoticeInfo> data;

    public MessageAdapter(MessageActivity messageActivity, @Nullable List<NoticeInfo> data) {
        super(R.layout.item_message, data);
        this.messageActivity = messageActivity;
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NoticeInfo item) {
        RoundedImageView circleImageView = helper.getView(R.id.iv_head);
        if (item.getNoticeType() == 0) { //系统消息
            helper.setText(R.id.txt_message_title, "系统消息")
                    .setText(R.id.txt_message_content, item.getNoticeContent());
        }

        if (item.getNoticeType() == 1) { //评论消息
            helper.setText(R.id.txt_message_title, item.getNickName())
                    .setText(R.id.txt_message_content, item.getNoticeContent());
            //TODO 添加照片
        }

        if (item.getNoticeType() == 2) { //关注消息
            helper.setText(R.id.txt_message_title, item.getNickName())
                    .setText(R.id.txt_message_content, item.getNoticeContent());

        }
        helper.setText(R.id.txt_message_time, DateUtils.getDate(item.getCreateTimeStr()));

        helper.getView(R.id.right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO {"code":"1008","msg":"获取数据错误"}
                messageActivity.delNotice("" + item.getId(), helper.getLayoutPosition());
            }
        });
    }
}
