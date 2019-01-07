package com.example.haoza.appsclub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.User;

import java.util.ArrayList;
import java.util.List;

public class MemberEXLVAdapter extends BaseExpandableListAdapter {
    private List<String> departmentNameList=new ArrayList<>();
    Context context;
    List<List<User>> userlist_List;

    public MemberEXLVAdapter(Context context, List<List<User>> userlist_List) {
        this.context = context;
        this.userlist_List = userlist_List;
        departmentNameList.add("技术部");
        departmentNameList.add("秘书部");
        departmentNameList.add("外联部");
        departmentNameList.add("宣传部");
        departmentNameList.add("组织部");
    }

    @Override
    public int getGroupCount() {
        return departmentNameList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return userlist_List.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return userlist_List.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return userlist_List.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.c_member_eblv_group, null);
        TextView tv_departName=view.findViewById(R.id.tv_departName);
        tv_departName.setText(departmentNameList.get(groupPosition));
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.c_member_rec_item_item_layout, null);
        TextView c_member_rec_item_item_tv_M_C=view.findViewById(R.id.c_member_rec_item_item_tv_M_C);
        TextView c_member_rec_item_item_tv_Name=view.findViewById(R.id.c_member_rec_item_item_tv_Name);
        c_member_rec_item_item_tv_M_C.setText(userlist_List.get(groupPosition).get(childPosition).getMajor()+userlist_List.get(groupPosition).get(childPosition).getUserClass());
        c_member_rec_item_item_tv_Name.setText(userlist_List.get(groupPosition).get(childPosition).getUsername());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
