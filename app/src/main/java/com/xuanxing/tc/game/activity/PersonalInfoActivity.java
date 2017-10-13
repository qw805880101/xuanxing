package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.MemberInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xuanxing.tc.game.MyApplication.USER_INFO;

/**
 * Created by admin on 2017/9/3.
 */

public class PersonalInfoActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.lin_head)
    LinearLayout linHead;
    @BindView(R.id.lin_name)
    LinearLayout linName;
    @BindView(R.id.lin_birthday)
    LinearLayout linBirthday;
    @BindView(R.id.lin_interest)
    LinearLayout linInterest;
    @BindView(R.id.lin_mod_introduce)
    LinearLayout linModIntroduce;
    @BindView(R.id.txt_user_name)
    TextView mTxtUserName;
    @BindView(R.id.txt_birthday)
    TextView mTxtBirthday;
    @BindView(R.id.txt_interest)
    TextView mTxtInterest;
    @BindView(R.id.txt_intro)
    TextView mTxtIntro;

    private MemberInfo mMemberInfo;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("个人信息")
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PersonalInfoActivity.this.finish();
                    }
                })
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        linHead.setOnClickListener(this);
        linName.setOnClickListener(this);
        linBirthday.setOnClickListener(this);
        linInterest.setOnClickListener(this);
        linModIntroduce.setOnClickListener(this);
    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        mMemberInfo = (MemberInfo) intent.getSerializableExtra(USER_INFO);

        mTxtUserName.setText(mMemberInfo.getNickName());
        mTxtBirthday.setText(mMemberInfo.getBirthdayStr() != null && !mMemberInfo.getBirthdayStr().equals("") ? mMemberInfo.getBirthdayStr() : "1991-01-01");
        mTxtInterest.setText(mMemberInfo.getNickName());
        mTxtIntro.setText(mMemberInfo.getIntro());
    }

    @Override
    public void onClick(View v) {
        if (v == linHead) {

        }

        if (v == linName) {
            Intent intent = new Intent(this, ModNameActivity.class);
            intent.putExtra("userName", mMemberInfo.getNickName());
            startActivity(intent);
        }

        if (v == linBirthday) {

        }

        if (v == linInterest) {

        }

        if (v == linModIntroduce) {
            Intent intent = new Intent(this, ModIntroduceActivity.class);
            startActivity(intent);
        }
    }
}
