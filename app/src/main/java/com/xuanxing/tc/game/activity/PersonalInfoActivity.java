package com.xuanxing.tc.game.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.MemberInfo;
import com.xuanxing.tc.game.utils.DialogUtil;
import com.xuanxing.tc.game.utils.SendEvent;
import com.xuanxing.tc.game.utils.TakePhotosDispose;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Build.VERSION_CODES.M;
import static com.xuanxing.tc.game.MyApplication.USER_INFO;

/**
 * Created by admin on 2017/9/3.
 */

public class PersonalInfoActivity extends BaseActivity implements OnClickListener {

    public static final int EXTERNAL_STORAGE_REQ_CAMERA_CODE = 10;

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
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.txt_interest)
    TextView mTxtInterest;
    @BindView(R.id.txt_intro)
    TextView mTxtIntro;

    private MemberInfo mMemberInfo;

    private String path;

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
        //事件注册
        EventBus.getDefault().register(this);
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
        //TODO 添加照片
        Glide.with(mContext).load(mMemberInfo.getHeadIcon()).into(mIvHead);
    }

    @Override
    public void onClick(View v) {
        if (v == linHead) {
            if (android.os.Build.VERSION.SDK_INT >= M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //进行权限请求
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            EXTERNAL_STORAGE_REQ_CAMERA_CODE);
                } else {
                    DialogUtil.showTakePhotoDialog(this);
                }
            } else {
                DialogUtil.showTakePhotoDialog(this);
            }
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
            intent.putExtra("intro", mMemberInfo.getIntro());
            startActivity(intent);
        }
    }

    //事件接受
    @Subscribe
    public void onEventMainThread(SendEvent event) {
        if (event != null) {
            if (event.getKey().equals("nickname") && !event.getValue().equals("")) {
                mTxtUserName.setText(event.getValue());
            }
            if (event.getKey().equals("birthday") && !event.getValue().equals("")) {
                mTxtBirthday.setText(event.getValue());
            }
            if (event.getKey().equals("headicon") && !event.getValue().equals("")) {
//                .loginInfo.getMemberInfo().setHeadIcon(event.getValue());
            }
            if (event.getKey().equals("intro")) {
                mTxtIntro.setText(event.getValue());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TakePhotosDispose.CROPIMAGE:
                case TakePhotosDispose.TAKEPHOTO:
                    path = DialogUtil.currentFileName.getAbsolutePath();
                    break;
                case TakePhotosDispose.PICKPHOTO:
                    // 相册选图
                    Uri selectedImage = data.getData();
                    if (!selectedImage.toString().substring(0, 7).equals("content")) {
                        // 如果路径错误
                        String picturePath = selectedImage.getPath();
                        path = picturePath;
                    } else {
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        path = picturePath;
                    }
                    break;
                default:
                    break;
            }
            System.out.println("path = " + path);
//            Glide.with(this).load(new File(path))
//                    .placeholder(R.mipmap.wellcom) //设置占位图
//                    .error(R.mipmap.wellcom) //设置错误图片
//                    .crossFade() //设置淡入淡出效果，默认300ms，可以传参
//                    .transform(new GlideCircleTransform(context)).into(mIvHead);
            Glide.with(this).load(new File(path))
                    .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                    .into(mIvHead);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册
        EventBus.getDefault().unregister(this);
    }
}
