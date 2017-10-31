package com.xuanxing.tc.game.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.psylife.wrmvplibrary.utils.StatusBarUtil;
import com.psylife.wrmvplibrary.utils.TitleBuilder;
import com.psylife.wrmvplibrary.utils.helper.RxUtil;
import com.psylife.wrmvplibrary.webview.MyWebView;
import com.psylife.wrmvplibrary.webview.MyWebView.Take;
import com.xuanxing.tc.game.MyApplication;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.adapter.CommentAdapter;
import com.xuanxing.tc.game.adapter.NewsAdapter;
import com.xuanxing.tc.game.base.BaseActivity;
import com.xuanxing.tc.game.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * 资讯详情
 * <p>
 * Created by sandlovechao on 2017/10/29.
 */

public class NewsDetailsActivity extends BaseActivity {

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

    private CommentAdapter commentAdapter;
    private NewsAdapter newsAdapter;

    private MyWebView myWebView;

    private List<String> list = new ArrayList<String>();

    private String newsId;       //咨询ID
    private String categoryCode; //游戏分类
    private int newsType;        //资讯类型


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
        myWebView = new MyWebView(this, wvNews);
        myWebView.webSetting();
        myWebView.loadUrl("http://120.27.18.127:10086/gamehelp_admin/h5/gameArticleDetails.html");

        list.add("1");
        list.add("1");
        list.add("1");
        commentAdapter = new CommentAdapter(this, list);
        newsAdapter = new NewsAdapter(this, list);
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(commentAdapter);
        rvInformation.setLayoutManager(new LinearLayoutManager(this));
        rvInformation.setAdapter(newsAdapter);
    }

    @Override
    public void initdata() {
        Intent intent = this.getIntent();
        newsId = intent.getStringExtra("newsId");
        categoryCode = intent.getStringExtra("categoryCode");
        newsType = intent.getIntExtra("newsType", 0);
        getNewsDetail();
    }

    private void getNewsDetail() {
        Observable<BaseBean> newsDetail = mXuanXingApi.getNewsDetail(MyApplication.loginInfo.getMemberInfo().getMemberId(),
                MyApplication.loginInfo.getP_token(), newsId, categoryCode, newsType).compose(RxUtil.<BaseBean>rxSchedulerHelper());
        mRxManager.add(newsDetail.subscribe(new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {

            }
        }, this));

    }

}
