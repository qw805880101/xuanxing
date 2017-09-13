package com.xuanxing.tc.game.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.CollectionActivity;
import com.xuanxing.tc.game.activity.FansActivity;
import com.xuanxing.tc.game.activity.FeedbackActivity;
import com.xuanxing.tc.game.activity.MessageActivity;
import com.xuanxing.tc.game.activity.PersonalInfoActivity;
import com.xuanxing.tc.game.activity.SetActivity;
import com.xuanxing.tc.game.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tc on 2017/8/24.
 */

public class MyFragment extends BaseFragment implements OnClickListener {

    @BindView(R.id.lin_personal_info)
    LinearLayout linPersonalInfo;
    @BindView(R.id.lin_leveling)
    LinearLayout linLeveling;
    @BindView(R.id.lin_message)
    LinearLayout linMessage;
    @BindView(R.id.lin_feedback)
    LinearLayout linFeedback;
    @BindView(R.id.lin_set)
    LinearLayout linSet;
    @BindView(R.id.lin_collection)
    LinearLayout linCollection;
    @BindView(R.id.lin_follow)
    LinearLayout linFollow;
    @BindView(R.id.lin_fans)
    LinearLayout linFans;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        linLeveling.setOnClickListener(this);
        linMessage.setOnClickListener(this);
        linFeedback.setOnClickListener(this);
        linSet.setOnClickListener(this);
        linPersonalInfo.setOnClickListener(this);
        linCollection.setOnClickListener(this);
        linFans.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == linPersonalInfo) {
            Intent intent = new Intent(this.getContext(), PersonalInfoActivity.class);
            startActivity(intent);
        }

        if (v == linFeedback) {
            Intent intent = new Intent(this.getContext(), FeedbackActivity.class);
            startActivity(intent);
        }

        if (v == linMessage) {
            Intent intent = new Intent(this.getContext(), MessageActivity.class);
            startActivity(intent);
        }

        if (v == linSet) {
            Intent intent = new Intent(this.getContext(), SetActivity.class);
            startActivity(intent);
        }

        if (v == linCollection) {
            Intent intent = new Intent(this.getContext(), CollectionActivity.class);
            startActivity(intent);
        }
        if (v == linFans){
            Intent intent = new Intent(this.getContext(), FansActivity.class);
            startActivity(intent);
        }
    }
}