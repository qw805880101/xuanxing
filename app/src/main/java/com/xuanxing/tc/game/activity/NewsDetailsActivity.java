package com.xuanxing.tc.game.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.psylife.wrmvplibrary.webview.MyWebView;
import com.psylife.wrmvplibrary.webview.MyWebView.Take;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.CommentAdapter;
import com.xuanxing.tc.game.adapter.NewsAdapter;
import com.xuanxing.tc.game.adapter.RecommendAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBean;
import com.xuanxing.tc.game.bean.BaseBeanClass;
import com.xuanxing.tc.game.bean.CommentList;
import com.xuanxing.tc.game.bean.NewsDetailInfo;
import com.xuanxing.tc.game.bean.NewsInfo;
import com.xuanxing.tc.game.utils.SendEvent;
import com.xuanxing.tc.game.utils.XUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * 资讯详情
 * <p>
 * Created by sandlovechao on 2017/10/29.
 */

public class NewsDetailsActivity extends BaseActivity implements OnClickListener, Take {

    @BindView(R.id.wv_news)
    WebView wvNews;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.txt_comment_more)
    TextView txtCommentMore;
    @BindView(R.id.rv_information)
    RecyclerView rvInformation;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.txt_comment_num)
    TextView txtCommentNum;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    @BindView(R.id.txt_collection)
    TextView txtCollection;
    @BindView(R.id.lin_comment_none)
    LinearLayout linCommentNone;
    @BindView(R.id.txt_comment_list_num)
    TextView txtCommentListNum;
    @BindView(R.id.lin_more_comment)
    LinearLayout linMoreComment;
    @BindView(R.id.bt_release)
    Button btRelease;
    @BindView(R.id.lin_icon)
    LinearLayout linIcon;
    @BindView(R.id.scrollView_nes)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.lin_information)
    LinearLayout linInformation;
    @BindView(R.id.lin_comment)
    LinearLayout linComment;

    private CommentAdapter commentAdapter;
    private RecommendAdapter recommendAdapter;

    private MyWebView myWebView;

    private List<CommentList> commentList = new ArrayList<>();
    private List<CommentList> allCommentList = new ArrayList<>();
    private List<NewsInfo> newsInfoList = new ArrayList<>();

    private String newsId;       //咨询ID
    private String categoryCode; //游戏分类
    private int newsType;        //资讯类型
    private int isCollect = 0; // 0取消收藏 1收藏
    private NewsDetailInfo mNewsDetailInfo;

    private boolean isFou = false;

    private boolean isAddComment = false;

    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.title_bg_e83646));
    }

    @Override
    public View getTitleView() {
        return new TitleBuilder(this)
                .setTitleBgRes(R.color.title_bg_e83646)
                .setTitleText("详情")
                .setTitleTextColor(this, R.color.white)
                .setLeftImage(R.mipmap.fanhui)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setRightImage(R.mipmap.fenxiang)
                .setRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 分享
                    }
                }).build();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ivCollection.setOnClickListener(this);
        btRelease.setOnClickListener(this);
        etComment.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    isFou = true;
                    btRelease.setVisibility(View.VISIBLE);
                    linIcon.setVisibility(View.GONE);
                } else {
                    isFou = false;
                    btRelease.setVisibility(View.GONE);
                    linIcon.setVisibility(View.VISIBLE);
                }
            }
        });
        etComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                etComment.setFocusable(true);
                etComment.setFocusableInTouchMode(true);
                etComment.requestFocus();
                etComment.findFocus();
                XUtils.openKeybord(etComment, NewsDetailsActivity.this);
            }
        });

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    btRelease.setTextColor(NewsDetailsActivity.this.getResources().getColor(R.color.tx_e83545));
                    btRelease.setEnabled(true);
                } else {
                    btRelease.setTextColor(NewsDetailsActivity.this.getResources().getColor(R.color.gray));
                    btRelease.setEnabled(false);
                }
            }
        });

        mNestedScrollView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            }
        });
        mNestedScrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isFou) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                        isFou = false;
                        etComment.setFocusable(false);
                        btRelease.setVisibility(View.GONE);
                        linIcon.setVisibility(View.VISIBLE);
                        XUtils.closeKeybord(etComment, NewsDetailsActivity.this);
                        return true;
                    }
                }
                return false;
            }
        });
        myWebView = new MyWebView(this, wvNews, this);
        myWebView.webSetting();
        commentAdapter = new CommentAdapter(this, commentList);
        recommendAdapter = new RecommendAdapter(newsInfoList);
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(commentAdapter);
        rvInformation.setLayoutManager(new LinearLayoutManager(this));
        rvInformation.setAdapter(recommendAdapter);
        rvInformation.setNestedScrollingEnabled(false);
        rvComment.setNestedScrollingEnabled(false);
    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        newsId = intent.getStringExtra("newsId");
        categoryCode = intent.getStringExtra("categoryCode");
        newsType = intent.getIntExtra("newsType", 0);
        getNewsDetail();
    }

    @Override
    public void onClick(View view) {
        if (view == ivCollection) {
            addCollection();
        }
        if (view == btRelease) {
            addComment();
        }
    }

    /**
     * 获取资讯详情
     */
    private void getNewsDetail() {
        Observable<BaseBeanClass<NewsDetailInfo>> newsDetail = mXuanXingApi.getNewsDetail(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), newsId, categoryCode, newsType).compose(RxUtil.<BaseBeanClass<NewsDetailInfo>>rxSchedulerHelper());
        mRxManager.add(newsDetail.subscribe(new Action1<BaseBeanClass<NewsDetailInfo>>() {
            @Override
            public void call(BaseBeanClass<NewsDetailInfo> baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    myWebView.loadUrl("http://120.27.18.127:10086/gamehelp_admin/h5/gameArticleDetails.html");
                    if (baseBean.getData().getCommentList().size() > 0) {
                        txtCommentListNum.setText("" + baseBean.getData().getCommentList().size());
                        linCommentNone.setVisibility(View.GONE);
                        rvComment.setVisibility(View.VISIBLE);
                        if (baseBean.getData().getCommentList().size() < 3) {
                            linMoreComment.setVisibility(View.GONE);
                        }
                        if (baseBean.getData().getCommentList().size() > 3) {
                            commentList.clear();
                            for (int i = 0; i < 3; i++) {
                                commentList.add(baseBean.getData().getCommentList().get(i));
                            }
                        } else {
                            commentList = baseBean.getData().getCommentList();
                        }
                        allCommentList = baseBean.getData().getCommentList();
                        txtCommentNum.setVisibility(View.VISIBLE);
                        txtCommentNum.setText("" + baseBean.getData().getCommentList().size());
                    } else {
                        txtCollection.setVisibility(View.GONE);
                        txtCommentNum.setVisibility(View.GONE);
                        linMoreComment.setVisibility(View.GONE);
                        linCommentNone.setVisibility(View.VISIBLE);
                        rvComment.setVisibility(View.GONE);
                    }
                    mNewsDetailInfo = baseBean.getData();
                    isCollect = mNewsDetailInfo.getIsCollect();
                    if (isCollect == 1) {
                        txtCollection.setText("" + baseBean.getData().getCollectNum());
                        txtCollection.setVisibility(View.VISIBLE);
                        ivCollection.setImageResource(R.mipmap.shoucang02_p);
                    } else if (isCollect == 0) {
                        txtCollection.setVisibility(View.GONE);
                        ivCollection.setImageResource(R.mipmap.shoucang02);
                    }
                    newsInfoList = baseBean.getData().getRelateGameNewsList();

                    if (isAddComment) {
                        commentAdapter.setNewData(commentList);
                    }

                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

    /**
     * 添加评论
     */
    private void addComment() {
        String content = etComment.getText().toString().trim();
        Observable<BaseBean> addComment = mXuanXingApi.addComment(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), newsId, newsType, content, "", "").compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(addComment.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    XUtils.closeKeybord(etComment, NewsDetailsActivity.this);
                    etComment.setText("");
                    etComment.setFocusable(false);
                    btRelease.setVisibility(View.GONE);
                    linIcon.setVisibility(View.VISIBLE);
                    getNewsDetail();
                }
            }
        }, this));
    }

    /**
     * 添加收藏
     */
    private void addCollection() {
        Observable<BaseBean> collection = mXuanXingApi.mIsCollect(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), isCollect == 0 ? 1 : 0, newsId).compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(collection.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                if (baseBean.getCode().equals("0000")) {
                    final Map<String, String> map = new LinkedHashMap();
                    if (isCollect == 1) {
                        isCollect = 0;
                        ivCollection.setImageResource(R.mipmap.shoucang02);
                        ToastUtils.showToast(NewsDetailsActivity.this, "取消收藏");
                        map.put("collectionNum", "" + (Integer.parseInt(MyApplication.loginInfo.getCollectNum()) - 1));
                        //事件发送 通知我的界面更新收藏
                        EventBus.getDefault().post(new SendEvent("collectionNum", "" + (Integer.parseInt(MyApplication.loginInfo.getCollectNum()) - 1)));
                    } else {
                        isCollect = 1;
                        ivCollection.setImageResource(R.mipmap.shoucang02_p);
                        ToastUtils.showToast(NewsDetailsActivity.this, "收藏成功");
                        map.put("collectionNum", "" + (Integer.parseInt(MyApplication.loginInfo.getCollectNum()) + 1));
                        //事件发送 通知我的界面更新收藏
                        EventBus.getDefault().post(new SendEvent("collectionNum", "" + (Integer.parseInt(MyApplication.loginInfo.getCollectNum()) + 1)));
                    }
                    XUtils.modUserInfo(NewsDetailsActivity.this, map);
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
                }
            }
        }, this));
    }

    @Override
    public void take(ValueCallback<Uri[]> filePathCallback, ValueCallback<Uri> uploadMsg) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void getUrl(String url) {

    }

    /**
     * 详情H5加载完成
     */
    @Override
    public void onPageFinished() {
        linComment.setVisibility(View.VISIBLE);
        linInformation.setVisibility(View.VISIBLE);
        commentAdapter.setNewData(commentList);
        recommendAdapter.setNewData(newsInfoList);
    }
}
