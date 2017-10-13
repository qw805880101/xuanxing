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
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;

import butterknife.BindView;

/**
 * 修改昵称
 * <p>
 * Created by admin on 2017/9/13.
 */

public class ModNameActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.et_mod_name)
    EditText etName;

    private int editStart;//光标开始位置
    private int editEnd;//光标结束位置

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("修改昵称")
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ModNameActivity.this.finish();
                    }
                })
                .setRightText("完成")
                .setRightTextColor(this, R.color.white)
                .setRightOnClickListener(this)
                .build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mod_name;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        etName.setSelection(etName.length());
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
                editStart = etName.getSelectionStart();
                editEnd = etName.getSelectionEnd();
                if (s.length() > 20) {
                    s.delete(editStart - 1, editEnd);
                }
            }
        });
    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        String name = intent.getStringExtra("userName");
        etName.setText(name);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.titlebar_tv_right) {

        }
    }
}
