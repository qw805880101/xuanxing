package com.xuanxing.tc.game.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.activity.AuthActivity;
import com.xuanxing.tc.game.activity.CollectionActivity;
import com.xuanxing.tc.game.activity.FansActivity;
import com.xuanxing.tc.game.activity.FeedbackActivity;
import com.xuanxing.tc.game.activity.HomeActivity;
import com.xuanxing.tc.game.activity.LoginActivity;
import com.xuanxing.tc.game.activity.MessageActivity;
import com.xuanxing.tc.game.activity.PersonalInfoActivity;
import com.xuanxing.tc.game.activity.SetActivity;
import com.xuanxing.tc.game.base.BaseFragment;
import com.xuanxing.tc.game.bean.LoginInfo;
import com.xuanxing.tc.game.bean.MemberInfo;
import com.xuanxing.tc.game.utils.SendEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.xuanxing.tc.game.MyApplication.USER_INFO;

/**
 * Created by tc on 2017/8/24.
 */

public class MyFragment extends BaseFragment implements OnClickListener {

    @BindView(R.id.lin_personal_info)
    LinearLayout linPersonalInfo;
    @BindView(R.id.lin_auth)
    LinearLayout linAuth;
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
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.txt_user_name)
    TextView mTxtUserName;
    @BindView(R.id.txt_intro)
    TextView mTxtIntro;
    @BindView(R.id.txt_attention_num)
    TextView mTxtAttentionNum;
    @BindView(R.id.txt_fans_num)
    TextView mTxtFansNum;
    @BindView(R.id.txt_collect_num)
    TextView mTxtCollectNum;

    private MemberInfo memberInfo;

    @Override
    public int getLayoutId() {
        //事件注册
        EventBus.getDefault().register(this);
        return R.layout.fragment_my;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        linAuth.setOnClickListener(this);
        linMessage.setOnClickListener(this);
        linFeedback.setOnClickListener(this);
        linSet.setOnClickListener(this);
        linPersonalInfo.setOnClickListener(this);
        linCollection.setOnClickListener(this);
        linFans.setOnClickListener(this);
    }

    @Override
    public void initData() {
        memberInfo = MyApplication.loginInfo.getMemberInfo();
        mTxtAttentionNum.setText(MyApplication.loginInfo.getAttentionNum());
        mTxtFansNum.setText(MyApplication.loginInfo.getFansNum());
        mTxtCollectNum.setText(MyApplication.loginInfo.getCollectNum());
        mTxtUserName.setText(memberInfo.getNickName());
        mTxtIntro.setText(memberInfo.getIntro());
    }

    @Override
    public void onClick(View v) {
        if (v == linPersonalInfo) {
            Intent intent = new Intent(this.getContext(), PersonalInfoActivity.class);
            intent.putExtra(USER_INFO, memberInfo);
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
        if (v == linFans) {
            Intent intent = new Intent(this.getContext(), FansActivity.class);
            startActivity(intent);
        }
        if (v == linAuth) {
            Intent intent = new Intent(this.getContext(), AuthActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    //事件接受
    @Subscribe
    public void onEventMainThread(SendEvent event) {
        if (event != null) {
            if (event.getKey().equals("nickname") && !event.getValue().equals("")) {
                mTxtUserName.setText(event.getValue());
            }
            if (event.getKey().equals("intro")) {
                mTxtIntro.setText(event.getValue());
            }
            if (event.getKey().equals("followNum")){
                mTxtAttentionNum.setText(event.getValue());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onNewBundle(Bundle args) {
        memberInfo = MyApplication.loginInfo.getMemberInfo();
        mTxtAttentionNum.setText(MyApplication.loginInfo.getAttentionNum());
        mTxtFansNum.setText(MyApplication.loginInfo.getFansNum());
        mTxtCollectNum.setText(MyApplication.loginInfo.getCollectNum());
        mTxtUserName.setText(memberInfo.getNickName());
        mTxtIntro.setText(memberInfo.getIntro());
    }
}