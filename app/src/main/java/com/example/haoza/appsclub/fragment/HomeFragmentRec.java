package com.example.haoza.appsclub.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.adapter.LoadMoreWrapper;
import com.example.haoza.appsclub.adapter.PostAdapter;
import com.example.haoza.appsclub.customObject.MyBmobArticle;
import com.example.haoza.appsclub.mInterface.EndlessRecyclerOnScrollListener;
import com.example.haoza.appsclub.mInterface.ReplaceFragmentCallBack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 首页
 */
public class HomeFragmentRec extends Fragment {

    private ReplaceFragmentCallBack replaceFragmentCallBack;
    private View view;
    private SwipeRefreshLayout home_swipe_refresh;
    private PostAdapter postAdapter;
    private LoadMoreWrapper loadMoreWrapper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        replaceFragmentCallBack = (ReplaceFragmentCallBack) context;
    }

    private RecyclerView recycle_view;
    private List<MyBmobArticle> bmobArticleList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_rec_frag_layout, null);
        initPosts();
        initView(view);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recycle_view.setLayoutManager(layoutManager);

        return view;
    }
    //初始化数据
    private void initPosts() {
        queryArticle();
    }

    /**
     * 初始 查询图文消息
     */
    private void queryArticle() {
        BmobQuery<MyBmobArticle> bmobArticleBmobQuery = new BmobQuery<>();
        // 按时间降序查询
        bmobArticleBmobQuery.order("-createdAt");
        // 设置每页数据个数
        bmobArticleBmobQuery.setLimit(10);
        bmobArticleBmobQuery.findObjects(new FindListener<MyBmobArticle>() {
            @Override
            public void done(List<MyBmobArticle> object, BmobException e) {
                if (e == null) {

                    curPage = 0;
                    bmobArticleList.clear();
                    lastTime = object.get(object.size() - 1).getCreatedAt();
                    bmobArticleList=object;
                    curPage++;

                    postAdapter = new PostAdapter(getContext(),bmobArticleList,replaceFragmentCallBack);
                    loadMoreWrapper = new LoadMoreWrapper(postAdapter);
                    recycle_view.setAdapter(loadMoreWrapper);
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "查询失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initView(View view) {
        home_swipe_refresh = (SwipeRefreshLayout) view.findViewById(R.id.home_swipe_refresh);
        recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);

        home_swipe_refresh.setColorSchemeResources(R.color.colorPrimary);
        home_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 下拉刷新(从第一页开始装载数据)
                queryData(0, STATE_REFRESH);
            }
        });

        recycle_view.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                // 上拉加载更多(加载下一页数据)
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                queryData(curPage, STATE_MORE);
            }
        });
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
                    /*bmobArticleList=object;
                    PostAdapter postAdapter =new PostAdapter(getContext(),bmobArticleList,replaceFragmentCallBack);
                    recycle_view.setAdapter(postAdapter);*/

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
                        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                    } else if (actionType == STATE_REFRESH) {
                        Snackbar.make(view, "没有数据", Snackbar.LENGTH_LONG).show();
                    }
                    //表示刷新事件结束
                    loadMoreWrapper.notifyDataSetChanged();
                    home_swipe_refresh.setRefreshing(false);
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    home_swipe_refresh.setRefreshing(false);
                    Snackbar.make(view, "查询失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }


}
