package com.xuanxing.tc.game.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.psylife.wrmvplibrary.utils.ToastUtils;
import com.xuanxing.tc.game.R;
import com.xuanxing.tc.game.bean.SearchHead;
import com.xuanxing.tc.game.bean.SearchHistory;
import com.xuanxing.tc.game.utils.XUtils;

import java.util.List;

/**
 * Created by admin on 2017/9/15.
 */

public class SearchAdapter extends BaseSectionQuickAdapter<SearchHead, BaseViewHolder> {

    private Context context;
    private List<SearchHead> data;
    private SearchOnclick mSearchOnclick;

    public SearchAdapter(Context context, @Nullable List<SearchHead> data) {
        super(R.layout.item_search_content, R.layout.item_search_head, data);
        this.context = context;
        this.data = data;
    }

    public void setOnclick(SearchOnclick mSearchOnclick){
        this.mSearchOnclick = mSearchOnclick;
    }

    /**
     * 头部数据
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convertHead(BaseViewHolder helper, SearchHead item) {

        if (item.getHeadType().equals("101")) { //历史记录
            helper.getView(R.id.iv_search_hear_del).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv_search_hear_del).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSearchOnclick.setOnclick(view, "", -1);
                }
            });
            helper.setText(R.id.txt_head_title, "历史记录")
                    .setImageResource(R.id.iv_search_hear_icon, R.mipmap.lishi);
        }

        if (item.getHeadType().equals("102")) { //热门搜索
            helper.getView(R.id.iv_search_hear_del).setVisibility(View.GONE);
            helper.setText(R.id.txt_head_title, "热门搜索")
                    .setImageResource(R.id.iv_search_hear_icon, R.mipmap.remen);
        }
    }

    /**
     * 内容数据
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convert(BaseViewHolder helper, final SearchHead item) {
//        if (item.getHeadType().equals("101")){ //历史记录
        helper.setText(R.id.txt_search_content, item.t.getHistorySearchContent());
        helper.getView(R.id.txt_search_content).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchOnclick.setOnclick(view,  item.t.getHistorySearchContent(), item.t.getType());
            }
        });
        if (helper.getLayoutPosition() == item.t.getNum()) {
            helper.setVisible(R.id.view_lin, false);
        }
//        }

//        if (item.getHeadType().equals("102")){ //热门搜索
//            helper.setText(R.id.txt_search_content, item.t.getHotSearchContent());
//            if (helper.getLayoutPosition() == data.size()){
//                helper.setVisible(R.id.view_lin, false);
//            }
//        }
    }

    public interface SearchOnclick{
        void setOnclick(View view, String searchKey, int type);
    }

}
