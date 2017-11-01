package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebView;
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

    private CommentAdapter commentAdapter;
    private RecommendAdapter recommendAdapter;

    private MyWebView myWebView;

    private List<String> list = new ArrayList<String>();
    private List<NewsInfo> newsInfoList = new ArrayList<>();

    private String newsId;       //咨询ID
    private String categoryCode; //游戏分类
    private int newsType;        //资讯类型
    private int isCollect = 0; // 0取消收藏 1收藏
    private NewsDetailInfo mNewsDetailInfo;


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
        myWebView = new MyWebView(this, wvNews, this);
        myWebView.webSetting();
        myWebView.loadUrl("http://120.27.18.127:10086/gamehelp_admin/h5/gameArticleDetails.html");
        commentAdapter = new CommentAdapter(this, list);
        recommendAdapter = new RecommendAdapter(newsInfoList);
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(commentAdapter);
        rvInformation.setLayoutManager(new LinearLayoutManager(this));
        rvInformation.setAdapter(recommendAdapter);
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
                    if (baseBean.getData().getCommentList().size() > 0) {
                        txtCommentListNum.setText(baseBean.getData().getCommentList().size());
                        linCommentNone.setVisibility(View.GONE);
                        rvComment.setVisibility(View.VISIBLE);
                        if (baseBean.getData().getCommentList().size() < 3) {
                            linMoreComment.setVisibility(View.GONE);
                        }
                        txtCommentNum.setText(baseBean.getData().getCommentList().size());
                    } else {
                        txtCollection.setVisibility(View.GONE);
                        txtCommentNum.setVisibility(View.GONE);
                        linMoreComment.setVisibility(View.GONE);
                        linCommentNone.setVisibility(View.VISIBLE);
                        rvComment.setVisibility(View.GONE);
                    }
                    mNewsDetailInfo = baseBean.getData();
                    isCollect = mNewsDetailInfo.getIsCollect();
                    newsInfoList = baseBean.getData().getRelateGameNewsList();
                } else {
                    toastMessage(baseBean.getCode(), baseBean.getMsg());
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
                        ToastUtils.showToast(NewsDetailsActivity.this, "取消收藏");
                        map.put("collectionNum", "" + (Integer.parseInt(MyApplication.loginInfo.getCollectNum()) - 1));
                        //事件发送 通知我的界面更新收藏
                        EventBus.getDefault().post(new SendEvent("collectionNum", "" + (Integer.parseInt(MyApplication.loginInfo.getCollectNum()) - 1)));
                    } else {
                        isCollect = 1;
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
        commentAdapter.setNewData(list);
        recommendAdapter.setNewData(newsInfoList);
    }
}
