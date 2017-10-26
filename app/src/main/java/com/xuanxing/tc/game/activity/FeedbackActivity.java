package com.xuanxing.tc.game.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.FeedbackAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.utils.DialogUtil;
import com.xuanxing.tc.game.utils.FeedbackSpaceItemDecoration;
import com.xuanxing.tc.game.utils.SendEvent;
import com.xuanxing.tc.game.utils.SpaceItemDecoration;
import com.xuanxing.tc.game.utils.TakePhotosDispose;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Action1;

import static android.os.Build.VERSION_CODES.M;

/**
 * 反馈
 * <p>
 * Created by admin on 2017/9/3.
 */

public class FeedbackActivity extends BaseActivity implements OnClickListener {

    public static final int DEL = 001;

    public static final int EXTERNAL_STORAGE_REQ_CAMERA_CODE = 10;

    @BindView(R.id.bt_feedback_type_01)
    Button mBtFeedbackType01;
    @BindView(R.id.bt_feedback_type_02)
    Button mBtFeedbackType02;
    @BindView(R.id.bt_feedback_type_03)
    Button mBtFeedbackType03;
    @BindView(R.id.et_feedback_msg)
    EditText mEtFeedbackMsg;
    @BindView(R.id.rv_feedback_image)
    RecyclerView mRvFeedbackImage;
    @BindView(R.id.iv_take_feedback)
    ImageView mIvTakeFeedback;
    @BindView(R.id.et_feedback_link)
    EditText mEtFeedbackLink;

    private String path;

    private int feedbackType = -1; //反馈类型

    private String feedbackName = ""; //反馈类型名称

    private List<String> photoPaths = new ArrayList<>();

    private FeedbackAdapter mFeedbackAdapter;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this).setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("我要反馈")
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FeedbackActivity.this.finish();
                    }
                })
                .setRightText("提交")
                .setRightOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .build();
    }

    @Override
    public int getLayoutId() {
        //事件注册
        EventBus.getDefault().register(this);
        return R.layout.activity_feedback;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBtFeedbackType01.setOnClickListener(this);
        mBtFeedbackType02.setOnClickListener(this);
        mBtFeedbackType03.setOnClickListener(this);
        mIvTakeFeedback.setOnClickListener(this);

        mFeedbackAdapter = new FeedbackAdapter(this, photoPaths);
        mRvFeedbackImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvFeedbackImage.addItemDecoration(new FeedbackSpaceItemDecoration(this.getResources().getDimensionPixelSize(R.dimen.bottom_10)));
        mRvFeedbackImage.setAdapter(mFeedbackAdapter);
    }

    @Override
    public void initdata() {

    }

    @Override
    public void onClick(View view) {
        if (view == mBtFeedbackType01) {
            feedbackType = 1;
            feedbackName = "功能建议";
            mBtFeedbackType01.setSelected(true);
            mBtFeedbackType02.setSelected(false);
            mBtFeedbackType03.setSelected(false);
        }
        if (view == mBtFeedbackType02) {
            feedbackType = 2;
            feedbackName = "闪退崩溃";
            mBtFeedbackType01.setSelected(false);
            mBtFeedbackType02.setSelected(true);
            mBtFeedbackType03.setSelected(false);
        }
        if (view == mBtFeedbackType03) {
            feedbackType = 3;
            feedbackName = "其他";
            mBtFeedbackType01.setSelected(false);
            mBtFeedbackType02.setSelected(false);
            mBtFeedbackType03.setSelected(true);
        }
        if (view == mIvTakeFeedback) {
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
    }

    private void submit(){

        String contactPhone = mEtFeedbackLink.getText().toString().trim();
        String feedbackDesc = mEtFeedbackMsg.getText().toString().trim();

        if (feedbackType <= -1){
            ToastUtils.showToast(this, "请选择反馈类型!");
            return;
        }
        if (feedbackDesc.equals("")){
            ToastUtils.showToast(this, "请填写反馈信息!");
            return;
        }
        if (contactPhone.equals("")){
            ToastUtils.showToast(this, "请填写联系方式!");
            return;
        }

        startProgressDialog(this);

//        RequestBody requestBody =

        Observable<BaseBean> submit = mXuanXingApi.feedBack(MyApplication.loginInfo.getMemberInfo().getMemberId(), MyApplication.loginInfo.getP_token(),
                                                            feedbackType, feedbackName, contactPhone, feedbackDesc, null);

        mRxManager.add(submit.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {

            }
        }, this));
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
                        String[] filePathColumn = {Media.DATA};
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
            photoPaths.add(path);
            mFeedbackAdapter.setNewData(photoPaths);
            if (photoPaths.size() >= 3) {
                mIvTakeFeedback.setVisibility(View.GONE);
            }
//            Glide.with(this).load(new File(path))
//                    .placeholder(R.mipmap.wellcom) //设置占位图
//                    .error(R.mipmap.wellcom) //设置错误图片
//                    .crossFade() //设置淡入淡出效果，默认300ms，可以传参
//                    .transform(new GlideCircleTransform(context)).into(mIvHead);
//            Glide.with(this).load(new File(path))
//                    .crossFade() //设置淡入淡出效果，默认300ms，可以传参
//                    .into(mIvHead);

        }
    }

    //事件接受
    @Subscribe
    public void onEventMainThread(SendEvent event) {
        if (event != null) {
            if (event.getCode() == DEL && event.getPosition() != -1) {
                photoPaths.remove(event.getPosition());
                mFeedbackAdapter.setNewData(photoPaths);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_REQ_CAMERA_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                DialogUtil.showTakePhotoDialog(this);
            } else {
                ToastUtils.showToast(this, "请先获取权限");
            }
        }
    }
}
