package com.example.haoza.appsclub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.User;

import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
    private List<String> departmentNameList=new ArrayList<>();
    private Context context;
    private List<List<User>> userlist_List;

    public MemberAdapter(List<List<User>> userlist_List) {
        this.userlist_List = userlist_List;
        departmentNameList.add("技术部");
        departmentNameList.add("秘书部");
        departmentNameList.add("外联部");
        departmentNameList.add("宣传部");
        departmentNameList.add("组织部");
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView c_member_rec_item_tv_departmentName;
        private final ListView c_member_rec_item_lv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            c_member_rec_item_tv_departmentName = itemView.findViewById(R.id.c_member_rec_item_tv_DepartmentName);
            c_member_rec_item_lv = itemView.findViewById(R.id.c_member_rec_item_lv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context=viewGroup.getContext();
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.c_member_rec_item_layout,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.c_member_rec_item_tv_departmentName.setText(departmentNameList.get(i));
        viewHolder.c_member_rec_item_lv.setAdapter(new CMemberRecItemItemLayoutAdapter(context,userlist_List.get(i)));
    }

    @Override
    public int getItemCount() {
        return userlist_List.size();
    }


}
