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

import com.bigkoo.pickerview.TimePickerView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.HeadInfo;
import com.xuanxing.tc.game.bean.MemberInfo;
import com.xuanxing.tc.game.utils.DateUtils;
import com.xuanxing.tc.game.utils.DialogUtil;
import com.xuanxing.tc.game.utils.SendEvent;
import com.xuanxing.tc.game.utils.TakePhotosDispose;
import com.xuanxing.tc.game.utils.XUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Action1;

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
    RoundedImageView mIvHead;
    @BindView(R.id.txt_interest)
    TextView mTxtInterest;
    @BindView(R.id.txt_intro)
    TextView mTxtIntro;

    private MemberInfo mMemberInfo;

    private String path;

    private String birthday;

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
        birthday = mTxtBirthday.getText().toString().trim();
        mTxtInterest.setText(mMemberInfo.getNickName());
        mTxtIntro.setText(mMemberInfo.getIntro());
        //TODO 添加照片
        XUtils.loadHeadIcon(mContext, mMemberInfo.getHeadIcon(), mIvHead);
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
            try {
                Calendar selectedDate = Calendar.getInstance();
                Date date = DateUtils.getDateFormat().parse(birthday);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        birthday = DateUtils.getDateFormat().format(date);
                        mTxtBirthday.setText(birthday);
                        XUtils.modName("", birthday, "", "", "", "", mXuanXingApi, mRxManager, mAction1, PersonalInfoActivity.this);
                    }
                })
//                    .setType(new boolean[]{true, true, true})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("完成")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(false)//是否循环滚动
                        .setSubmitColor(this.getResources().getColor(R.color.tx_e83545))//确定按钮文字颜色
                        .setCancelColor(this.getResources().getColor(R.color.tx_e83545))//取消按钮文字颜色
                        .setTitleBgColor(this.getResources().getColor(R.color.white))//标题背景颜色 Night mode
                        .setBgColor(this.getResources().getColor(R.color.bg_f4f4f4))//滚轮背景颜色 Night mode
                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                    .setRangDate(startDate,endDate)//起始终止年月日设定
//                    .setLabel("年","月","日","","","")//默认设置为年月日时分秒
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .isDialog(false)//是否显示为对话框样式
                        .build();
                pvTime.show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
            uploadHead();
//            Glide.with(this).load(new File(path))
//                    .placeholder(R.mipmap.wellcom) //设置占位图
//                    .error(R.mipmap.wellcom) //设置错误图片
//                    .crossFade() //设置淡入淡出效果，默认300ms，可以传参
//                    .transform(new GlideCircleTransform(context)).into(mIvHead);
        }
    }

    /**
     * 上传头像
     */
    private void uploadHead() {
        startProgressDialog(this);
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);
        Observable<BaseBeanClass<HeadInfo>> uploadHead = mXuanXingApi.uploadHead(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), requestBody).compose(RxUtil.<BaseBeanClass<HeadInfo>>rxSchedulerHelper());
        mRxManager.add(uploadHead.subscribe(new Action1<BaseBeanClass<HeadInfo>>() {
            @Override
            public void call(BaseBeanClass<HeadInfo> baseBean) {
                stopProgressDialog();
                if (baseBean.getCode().equals("0000")) {
                    final Map<String, String> map = new LinkedHashMap();
                    map.put("headicon", baseBean.getData().getHeadIcon());
                    //事件发送 通知我的界面更新头像
                    EventBus.getDefault().post(new SendEvent("headicon", baseBean.getData().getHeadIcon()));
                    XUtils.modUserInfo(PersonalInfoActivity.this, map);
                    XUtils.loadHeadIcon(mContext, new File(path), mIvHead);

                } else {
                    ToastUtils.showToast(PersonalInfoActivity.this, baseBean.getMsg());
                }
            }
        }, this));
    }

    Action1<BaseBean> mAction1 = new Action1<BaseBean>() {
        @Override
        public void call(BaseBean baseBean) {
            if (baseBean.getCode().equals("0000")) {
                final Map<String, String> map = new LinkedHashMap();
                map.put("birthday", birthday);
                //事件发送
                XUtils.modUserInfo(PersonalInfoActivity.this, map);
                ToastUtils.showToast(PersonalInfoActivity.this, baseBean.getMsg());
                finish();
            } else {
                toastMessage(baseBean.getCode(), baseBean.getMsg());
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册
        EventBus.getDefault().unregister(this);
    }
}
