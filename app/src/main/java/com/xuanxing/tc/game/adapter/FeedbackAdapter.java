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
import com.xuanxing.tc.game.bean.HotGameList;
import com.xuanxing.tc.game.utils.SendEvent;
import com.xuanxing.tc.game.utils.XUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import static com.xuanxing.tc.game.activity.FeedbackActivity.DEL;

/**
 * 反馈图片列表
 *
 * Created by tianchao on 2017/10/16.
 */

public class FeedbackAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    private List<String> list;
    public FeedbackAdapter(Context context, List<String> list) {
        super(R.layout.item_feedback, list);
        this.context = context;
        this.list = list;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        ImageView imageView = helper.getView(R.id.iv_feedback);
        XUtils.loadHeadIcon(mContext, new File(item), imageView);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                //事件发送
//                SendEvent sendEvent = new SendEvent();
//                sendEvent.setPosition(helper.getLayoutPosition());
//                sendEvent.setValue(item);
//                EventBus.getDefault().post(sendEvent);
                Intent intent = new Intent(context, PhotoActivity.class);
                intent.putExtra("position", helper.getLayoutPosition());
                intent.putExtra("total", list.size());
                intent.putExtra("path", item);
                context.startActivity(intent);
            }
        });

    }
}
