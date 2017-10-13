package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.psylife.wrmvplibrary.utils.SpUtils;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.LoginInfo;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

import static com.xuanxing.tc.game.MyApplication.USER_INFO;

/**
 * Created by admin on 2017/8/23.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_sms)
    EditText etSms;
    @BindView(R.id.bt_get_sms)
    Button btGetSms;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.image_qq_login)
    ImageView imageQqLogin;
    @BindView(R.id.image_wx_login)
    ImageView imageWxLogin;

    private String phoneNum;

    private String smsCode;

    private SmsThread smsThread = new SmsThread();

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setLeftImage(R.mipmap.close)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginActivity.this.finish();
                    }
                })
                .setTitleText("登录")
                .setTitleTextColor(this, R.color.white)
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        btLogin.setOnClickListener(this);

        btGetSms.setOnClickListener(this);

        imageQqLogin.setOnClickListener(this);
        imageWxLogin.setOnClickListener(this);
    }

    @Override
    public void initdata() {

    }

    @Override
    public void onClick(View v) {

        if (v == btGetSms) {

            phoneNum = etPhoneNum.getText().toString().trim();

            if (null == phoneNum || ("").equals(phoneNum)) {
                ToastUtils.showToast(this, "请先输入手机号码");
                return;
            }
            smsCode();
        }

        if (v == btLogin) {
            phoneNum = etPhoneNum.getText().toString().trim();
            smsCode = etSms.getText().toString().trim();
            if (null == phoneNum || ("").equals(phoneNum)) {
                ToastUtils.showToast(this, "请先输入手机号码");
                return;
            }
            if (phoneNum.length() < 11) {
                ToastUtils.showToast(this, "请输入正确的手机号码");
                return;
            }
            if (null == smsCode || ("").equals(smsCode)) {
                ToastUtils.showToast(this, "请先输入验证码");
                return;
            }
            login();
        }

        if (v == imageQqLogin) {

        }

        if (v == imageWxLogin) {

        }
    }

    /**
     * 获取验证码
     */
    private void smsCode() {
        Observable<BaseBean> getSmsCode = mXuanXingApi.getSmsCode(phoneNum).compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(getSmsCode.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    smsThread.start();
                    btGetSms.setEnabled(false);
                    ToastUtils.showToast(LoginActivity.this, "验证码发送成功!");
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

    /**
     * 登录
     */
    private void login() {
        Observable<BaseBeanClass<LoginInfo>> login = mXuanXingApi.login("", "123", "1", "1", "",
                "", phoneNum, smsCode).compose(RxUtil.<BaseBeanClass<LoginInfo>>rxSchedulerHelper());
        mRxManager.add(login.subscribe(new Action1<BaseBeanClass<LoginInfo>>() {
            @Override
            public void call(BaseBeanClass<LoginInfo> loginInfoBaseBeanClass) {
                if (loginInfoBaseBeanClass.getCode().equals("0000")) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("loginInfo", loginInfoBaseBeanClass.getData());
                    SpUtils.putString(LoginActivity.this, USER_INFO, JSON.toJSONString(loginInfoBaseBeanClass.getData()));
                    startActivity(intent);
                    finish();
                } else {
                    toastMessage(loginInfoBaseBeanClass.getCode(), loginInfoBaseBeanClass.getMsg());
                }
            }
        }, this));
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int a = (int) msg.obj;
            if (a == 0) {
                btGetSms.setText("重新获取验证码");
                btGetSms.setEnabled(true);
            } else {
                btGetSms.setText(a + "秒后重新获取");
            }

        }
    };

    class SmsThread extends Thread {

        int a = 59;

        @Override
        public void run() {
            while (a >= 0) {
                try {
                    Message message = new Message();
                    message.obj = a;
                    handler.sendMessage(message);
                    Thread.sleep(1000);
                    a--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void close() {
            a = -1;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        smsThread.close();
    }
}
