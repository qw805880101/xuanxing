package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.utils.SendEvent;
import com.xuanxing.tc.game.utils.XUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * 修改介绍
 * <p>
 * Created by admin on 2017/9/13.
 */

public class ModIntroduceActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.et_introduce)
    EditText etIntroduce;
    @BindView(R.id.txt_hint_num)
    TextView txtHintNum;

    private int editStart;//光标开始位置
    private int editEnd;//光标结束位置

    String intro = "";

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("修改介绍")
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ModIntroduceActivity.this.finish();
                    }
                })
                .setRightText("完成")
                .setRightTextColor(this, R.color.white)
                .setRightOnClickListener(this)
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mod_introduce;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        etIntroduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
                editStart = etIntroduce.getSelectionStart();
                editEnd = etIntroduce.getSelectionEnd();
                txtHintNum.setText(s.length() + "/16");
                if (s.length() > 16) {
                    s.delete(editStart - 1, editEnd);
                }
            }
        });
    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        intro = intent.getStringExtra("intro") != null ? intent.getStringExtra("intro") : "";
        etIntroduce.setText(intro);
        txtHintNum.setText(etIntroduce.length() + "/16");
        etIntroduce.setSelection(etIntroduce.length());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.titlebar_tv_right) {
            String nerIntro = etIntroduce.getText().toString().trim();
            if (intro.equals(nerIntro)) {
                finish();
            } else {
                intro = nerIntro;
                XUtils.modName("", "", "", "", "", nerIntro, mXuanXingApi, mRxManager, mAction1, this);
            }
        }
    }

    Action1<BaseBean> mAction1 = new Action1<BaseBean>() {
        @Override
        public void call(BaseBean baseBean) {
            if (baseBean.getCode().equals("0000")) {
                final Map<String, String> map = new LinkedHashMap();
                map.put("intro", intro);
                //事件发送
                EventBus.getDefault().post(new SendEvent("intro", intro));
                XUtils.modUserInfo(ModIntroduceActivity.this, map);
                ToastUtils.showToast(ModIntroduceActivity.this, baseBean.getMsg());
                finish();
            } else {
                toastMessage(baseBean.getCode(), baseBean.getMsg());
            }
        }
    };
}
