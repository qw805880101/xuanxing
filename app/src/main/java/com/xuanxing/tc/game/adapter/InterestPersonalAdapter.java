package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.HotGameNewActivity;
import com.xuanxing.tc.game.activity.LoginActivity;
import com.xuanxing.tc.game.bean.HotGameList;
import com.xuanxing.tc.game.utils.XUtils;

import java.util.List;

/**
 * Created by tianchao on 2017/12/14.
 */

public class InterestPersonalAdapter extends BaseQuickAdapter<HotGameList, BaseViewHolder> {
    private Context context;
    private List<HotGameList> list;

    public InterestPersonalAdapter(Context context, List<HotGameList> list) {
        super(R.layout.item_interest_personal, list);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HotGameList item) {

        if (helper.getLayoutPosition() == 3) {
            XUtils.loadHeadIcon(mContext, R.mipmap.morentouxiang, (RoundedImageView) helper.getView(R.id.iv_interest));
        } else {

            if (item.getCategoryPic() != null && !item.getCategoryPic().equals(""))
                XUtils.loadHeadIcon(mContext, item.getCategoryPic(), (RoundedImageView) helper.getView(R.id.iv_interest));
            else
                XUtils.loadHeadIcon(mContext, R.mipmap.touxiang, (RoundedImageView) helper.getView(R.id.iv_interest));
        }


//        helper.getView(R.id.rl_hot_game).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (MyApplication.loginInfo == null){
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    context.startActivity(intent);
//                    return;
//                }
//                Intent intent = new Intent(context, HotGameNewActivity.class);
//                intent.putExtra("gameInfo", item);
//                context.startActivity(intent);
//            }
//        });
    }
}
