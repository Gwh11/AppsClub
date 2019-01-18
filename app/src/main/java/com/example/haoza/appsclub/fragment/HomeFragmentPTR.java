package com.example.haoza.appsclub.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.adapter.HomePtrlistFragLayoutAdapter;
import com.example.haoza.appsclub.customObject.MyBmobArticle;
import com.example.haoza.appsclub.mInterface.ReplaceFragmentCallBack;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 首页
 *
 */
public class HomeFragmentPTR extends Fragment {

    private ReplaceFragmentCallBack replaceFragmentCallBack;
    private View view;
    private HomePtrlistFragLayoutAdapter homePtrlistFragLayoutAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        replaceFragmentCallBack = (ReplaceFragmentCallBack) context;
    }

    private PullToRefreshListView mPullToRefreshView;
    private ILoadingLayout loadingLayout;
    private ListView mMsgListView;

    private List<MyBmobArticle> bmobArticleList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_ptrlist_frag_layout,container,false);
        initView(view);
        queryData(0, STATE_REFRESH);
        return view;
    }

    private void initView(View view) {
        mPullToRefreshView = view.findViewById(R.id.PtoR_list);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mPullToRefreshView.getRefreshableViewWrapper().getLayoutParams();
//        layoutParams.height=Constants.dip2px(mActivity,200);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        layoutParams.height=wm.getDefaultDisplay().getHeight();
        mPullToRefreshView.getRefreshableViewWrapper().setLayoutParams(layoutParams);

        loadingLayout = mPullToRefreshView.getLoadingLayoutProxy();
        loadingLayout.setLastUpdatedLabel("");
        loadingLayout
                .setPullLabel(getString(R.string.pull_to_refresh_bottom_pull));
        loadingLayout
                .setRefreshingLabel(getString(R.string.pull_to_refresh_bottom_refreshing));
        loadingLayout
                .setReleaseLabel(getString(R.string.pull_to_refresh_bottom_release));
        // 滑动监听
        mPullToRefreshView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem == 0) {
                    loadingLayout.setLastUpdatedLabel("");
                    loadingLayout
                            .setPullLabel(getString(R.string.pull_to_refresh_top_pull));
                    loadingLayout
                            .setRefreshingLabel(getString(R.string.pull_to_refresh_top_refreshing));
                    loadingLayout
                            .setReleaseLabel(getString(R.string.pull_to_refresh_top_release));
                } else if (firstVisibleItem + visibleItemCount + 1 == totalItemCount) {
                    loadingLayout.setLastUpdatedLabel("");
                    loadingLayout
                            .setPullLabel(getString(R.string.pull_to_refresh_bottom_pull));
                    loadingLayout
                            .setRefreshingLabel(getString(R.string.pull_to_refresh_bottom_refreshing));
                    loadingLayout
                            .setReleaseLabel(getString(R.string.pull_to_refresh_bottom_release));
                }
            }
        });

        // 下拉刷新监听
        mPullToRefreshView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

                    @Override
                    public void onPullDownToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        // 下拉刷新(从第一页开始装载数据)
                        queryData(0, STATE_REFRESH);
                    }

                    @Override
                    public void onPullUpToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        // 上拉加载更多(加载下一页数据)
                        queryData(curPage, STATE_MORE);
                    }
                });

        mMsgListView = mPullToRefreshView.getRefreshableView();
        // 再设置adapter
        homePtrlistFragLayoutAdapter = new HomePtrlistFragLayoutAdapter(getContext(),bmobArticleList,replaceFragmentCallBack);
        mMsgListView.setAdapter(homePtrlistFragLayoutAdapter);
    }

    //分页查询
    private String lastTime = null;

    private static final int STATE_REFRESH = 0;// 下拉刷新
    private static final int STATE_MORE = 1;// 加载更多

    private int limit = 10; // 每页的数据是10条
    private int curPage = 0; // 当前页的编号，从0开始

    /**
     * 分页获取数据
     *
     * @param page
     *            页码
     * @param actionType
     *            ListView的操作类型（下拉刷新、上拉加载更多）
     */
    private void queryData(int page, final int actionType) {
        Log.i("bmob", "pageN:" + page + " limit:" + limit + " actionType:"
                + actionType);

        BmobQuery<MyBmobArticle> bmobArticleBmobQuery = new BmobQuery<>();
        // 按时间降序查询
        bmobArticleBmobQuery.order("-createdAt");
        // 如果是加载更多

        if (actionType == STATE_MORE) {
            // 处理时间查询
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sdf.parse(lastTime);
                Log.i("0414", date.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // 只查询小于等于最后一个item发表时间的数据
            bmobArticleBmobQuery.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
            // 跳过之前页数
            bmobArticleBmobQuery.setSkip(page+(10*(curPage-1)-1));
        } else {
            // 下拉刷新
            page = 0;
            bmobArticleBmobQuery.setSkip(page);
        }
        // 设置每页数据个数
        bmobArticleBmobQuery.setLimit(limit);
        // 查找数据
        bmobArticleBmobQuery.findObjects(new FindListener<MyBmobArticle>() {
            @Override
            public void done(List<MyBmobArticle> object, BmobException e) {
                if (e == null) {

                    if (object.size() > 0) {

                        if (actionType == STATE_REFRESH) {
                            // 当是下拉刷新操作时，将当前页的编号重置为0，并把bankCards清空，重新添加
                            curPage = 0;
                            bmobArticleList.clear();
                            // 获取最后时间
                            lastTime = object.get(object.size() - 1).getCreatedAt();
                        }


                        // 将本次查询的数据添加到bankCards中
                        for (MyBmobArticle myBmobArticle : object) {
                            bmobArticleList.add(myBmobArticle);
                        }
                        // 这里在每次加载完数据后，将当前页码+1，这样在上拉刷新的onPullUpToRefresh方法中就不需要操作curPage了
                        curPage++;
//					 showToast("第"+(page+1)+"页数据加载完成");
                    } else if (actionType == STATE_MORE) {
                        Snackbar.make(view, "没有更多数据了", Snackbar.LENGTH_LONG).show();

                    } else if (actionType == STATE_REFRESH) {
                        Snackbar.make(view, "没有数据", Snackbar.LENGTH_LONG).show();
                    }
                    //表示刷新事件结束
                    homePtrlistFragLayoutAdapter.notifyDataSetChanged();
                    mPullToRefreshView.onRefreshComplete();
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "查询失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }


}
