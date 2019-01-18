package com.example.haoza.appsclub.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.adapter.InfoAdapter;
import com.example.haoza.appsclub.customObject.Department;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Apps社团详情
 *
 */
public class ClubInfoFragment extends Fragment {
    private RecyclerView c_info_rec_view;
    private List<Department> departmentList;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.c_info_rec_frag_layout, null);
        initClubInfo();
        initView(view);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        c_info_rec_view.setLayoutManager(layoutManager);

        return view;
    }

    private void initClubInfo() {
        DepartmentQuery();
    }

    /**
     * 查询
     * Department
     */
    private void DepartmentQuery() {

        BmobQuery<Department> departmentBmobQuery = new BmobQuery<Department>();
        departmentBmobQuery.findObjects(new FindListener<Department>() {
            @Override
            public void done(List<Department> object, BmobException e) {
                if (e == null) {
                    departmentList=object;
                    InfoAdapter infoAdapter=new InfoAdapter(departmentList);
                    c_info_rec_view.setAdapter(infoAdapter);
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    private void initView(View view) {
        c_info_rec_view = (RecyclerView) view.findViewById(R.id.c_info_rec_view);
    }
}
