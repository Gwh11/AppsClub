package com.example.haoza.appsclub.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.Department;
import com.example.haoza.appsclub.customObject.User;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private User user=BmobUser.getCurrentUser(User.class);
    private List<Department> departmentList;
    private View view;

    public InfoAdapter(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView c_info_rec_item_tv_department;
        private final TextView c_info_rec_item_tv_Info;
        private final FloatingActionButton c_info_rec_item_tv_flgBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            c_info_rec_item_tv_department = itemView.findViewById(R.id.c_info_rec_item_tv_Department);
            c_info_rec_item_tv_Info = itemView.findViewById(R.id.c_info_rec_item_tv_Info);
            c_info_rec_item_tv_flgBtn = itemView.findViewById(R.id.c_info_rec_item_tv_FlgBtn);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.c_info_rec_item_layout,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Department department=departmentList.get(i);
        viewHolder.c_info_rec_item_tv_department.setText(department.getDepartmentName());
        viewHolder.c_info_rec_item_tv_Info.setText(department.getDepartmentInfo());
        if(user.getDepartmentId()==null){
            viewHolder.c_info_rec_item_tv_flgBtn.setVisibility(View.VISIBLE);
        }
        viewHolder.c_info_rec_item_tv_flgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User mUser=new User();
                mUser.setDepartmentId(department);
                mUser.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Snackbar.make(view, "加入该部门", Snackbar.LENGTH_LONG).show();
                            viewHolder.c_info_rec_item_tv_flgBtn.setVisibility(View.GONE);
                        } else {
                            Log.e("BMOB", e.toString());
                            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return departmentList.size();
    }
}
