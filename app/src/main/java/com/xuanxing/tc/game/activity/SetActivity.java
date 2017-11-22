package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.SpUtil;
import com.psylife.wrmvplibrary.utils.SpUtils;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.fragment.MyFragment;
import com.xuanxing.tc.game.utils.XUtils;
import com.xuanxing.tc.game.view.SwitchView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

import static com.xuanxing.tc.game.MyApplication.USER_INFO;

/**
 * 设置
 * <p>
 * Created by admin on 2017/9/3.
 */

public class SetActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.sw_tui)
    SwitchView mSwTui;
    @BindView(R.id.txt_cash)
    TextView mTxtCash;
    @BindView(R.id.txt_phone)
    TextView mTxtPhone;
    @BindView(R.id.sw_wx)
    SwitchView mSwWx;
    @BindView(R.id.sw_qq)
    SwitchView mSwQq;
    @BindView(R.id.txt_version)
    TextView mTxtVersion;
    @BindView(R.id.txt_login_out)
    TextView mTxtLoginOut;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("设置")
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetActivity.this.finish();
                    }
                })
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTxtLoginOut.setOnClickListener(this);
        try {
            mTxtVersion.setText(XUtils.getVersionName(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTxtPhone.setText(MyApplication.loginInfo.getPhoneNum());
    }

    @Override
    public void initdata() {

    }

    @Override
    public void onClick(View view) {
        if (view == mTxtLoginOut) {
            loginOut();
        }
    }

    /**
     * 退出app
     */
    private void loginOut() {
        Observable<BaseBean> loginOut = mXuanXingApi.loginOut(MyApplication.loginInfo.getMemberInfo().getMemberId(), MyApplication.loginInfo.getP_token()).
                compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(loginOut.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    SpUtils.remove(SetActivity.this, USER_INFO);
                    MyApplication.loginInfo = null;
                    Intent intent = new Intent(SetActivity.this, HomeActivity.class);
                    intent.putExtra("isLoginOut", true);
                    startActivity(intent);

                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));

    }
}
