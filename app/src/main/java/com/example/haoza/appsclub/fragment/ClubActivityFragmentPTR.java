package com.example.haoza.appsclub.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.adapter.ActivityAdapter;
import com.example.haoza.appsclub.adapter.CActivityPtrlistFragLayoutAdapter;
import com.example.haoza.appsclub.adapter.HomePtrlistFragLayoutAdapter;
import com.example.haoza.appsclub.customObject.ClubActivity;
import com.example.haoza.appsclub.customObject.MyBmobArticle;
import com.example.haoza.appsclub.customObject.User;
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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 活动详情
 */
public class ClubActivityFragmentPTR extends Fragment {
    private User user=BmobUser.getCurrentUser(User.class);
    private ReplaceFragmentCallBack replaceFragmentCallBack;
    private List<ClubActivity> clubActivityList=new ArrayList<>();
    private View view;
    private CActivityPtrlistFragLayoutAdapter cActivityPtrlistFragLayoutAdapter;
    private FloatingActionButton c_activity_ptrlist_flgBtn;
    private AlertDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        replaceFragmentCallBack = (ReplaceFragmentCallBack) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.c_activity_ptrlist_frag_layout,null);

        init(view);
        queryData(0, STATE_REFRESH);
        return view;
    }

    private PullToRefreshListView c_activity_ptrlist_view;
    private ILoadingLayout loadingLayout;
    private ListView mMsgListView;


    @SuppressLint("RestrictedApi")
    private void init(View view) {
        c_activity_ptrlist_flgBtn = view.findViewById(R.id.c_activity_ptrlist_FlgBtn);
        if(!user.getPost().equals("成员")){
            c_activity_ptrlist_flgBtn.setVisibility(View.VISIBLE);
            c_activity_ptrlist_flgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //添加通知
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    final View dialog_view = LayoutInflater.from(getActivity()).inflate(R.layout.c_activity_dialog_layout, null);
                    builder.setView(dialog_view);

                    builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("发布", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                             String dialog_title = ((EditText) dialog_view.findViewById(R.id.c_activity_dialog_title)).getText().toString();
                             String dialog_content = ((EditText) dialog_view.findViewById(R.id.c_activity_dialog_content)).getText().toString();

                            if(dialog_title.isEmpty()&&dialog_content.isEmpty()){
                                Toast.makeText(getActivity(), "标题或内容不能为空", Toast.LENGTH_SHORT).show();
                            }else {
                                ClubActivity clubActivity=new ClubActivity();
                                clubActivity.setActivityTitle(dialog_title);
                                clubActivity.setActivityInfo(dialog_content);
                                clubActivity.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            queryData(0, STATE_REFRESH);
                                            Toast.makeText(getActivity(), "发布成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.e("BMOB", e.toString());
                                            Toast.makeText(getActivity(), "发布失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                dialog.dismiss();
                            }

                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }
            });
        }

        c_activity_ptrlist_view = view.findViewById(R.id.c_activity_ptrlist_view);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) c_activity_ptrlist_view.getRefreshableViewWrapper().getLayoutParams();
//        layoutParams.height=Constants.dip2px(mActivity,200);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        layoutParams.height=wm.getDefaultDisplay().getHeight();
        c_activity_ptrlist_view.getRefreshableViewWrapper().setLayoutParams(layoutParams);

        loadingLayout = c_activity_ptrlist_view.getLoadingLayoutProxy();
        loadingLayout.setLastUpdatedLabel("");
        loadingLayout
                .setPullLabel(getString(R.string.pull_to_refresh_bottom_pull));
        loadingLayout
                .setRefreshingLabel(getString(R.string.pull_to_refresh_bottom_refreshing));
        loadingLayout
                .setReleaseLabel(getString(R.string.pull_to_refresh_bottom_release));
        // 滑动监听
        c_activity_ptrlist_view.setOnScrollListener(new AbsListView.OnScrollListener() {

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
        c_activity_ptrlist_view
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

        mMsgListView = c_activity_ptrlist_view.getRefreshableView();
        // 再设置adapter
        cActivityPtrlistFragLayoutAdapter = new CActivityPtrlistFragLayoutAdapter(getContext(),clubActivityList,replaceFragmentCallBack);
        mMsgListView.setAdapter(cActivityPtrlistFragLayoutAdapter);
    }

    /**
     * 查询
     */
/*    private void MQuery() {

        BmobQuery<ClubActivity> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<ClubActivity>() {
            @Override
            public void done(List<ClubActivity> object, BmobException e) {
                if (e == null) {
                    clubActivityList=object;
                    ActivityAdapter activityAdapter=new ActivityAdapter(clubActivityList,replaceFragmentCallBack);
                    c_activity_rec_view.setAdapter(activityAdapter);
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }*/

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

        BmobQuery<ClubActivity> clubActivityBmobQuery = new BmobQuery<>();
        // 按时间降序查询
        clubActivityBmobQuery.order("-createdAt");
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
            clubActivityBmobQuery.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
            // 跳过之前页数
            clubActivityBmobQuery.setSkip(page+(10*(curPage-1)-1));
        } else {
            // 下拉刷新
            page = 0;
            clubActivityBmobQuery.setSkip(page);
        }
        // 设置每页数据个数
        clubActivityBmobQuery.setLimit(limit);
        // 查找数据
        clubActivityBmobQuery.findObjects(new FindListener<ClubActivity>() {
            @Override
            public void done(List<ClubActivity> object, BmobException e) {
                if (e == null) {
                    /*bmobArticleList=object;
                    PostAdapter postAdapter =new PostAdapter(getContext(),bmobArticleList,replaceFragmentCallBack);
                    recycle_view.setAdapter(postAdapter);*/

                    if (object.size() > 0) {

                        if (actionType == STATE_REFRESH) {
                            // 当是下拉刷新操作时，将当前页的编号重置为0，并把bankCards清空，重新添加
                            curPage = 0;
                            clubActivityList.clear();
                            // 获取最后时间
                            lastTime = object.get(object.size() - 1).getCreatedAt();
                        }


                        // 将本次查询的数据添加到bankCards中
                        for (ClubActivity clubActivity : object) {
                            clubActivityList.add(clubActivity);
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
                    cActivityPtrlistFragLayoutAdapter.notifyDataSetChanged();
                    c_activity_ptrlist_view.onRefreshComplete();
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "查询失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }
}
