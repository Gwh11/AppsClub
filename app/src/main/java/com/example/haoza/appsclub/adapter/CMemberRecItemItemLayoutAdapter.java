package com.example.haoza.appsclub.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.User;

public class CMemberRecItemItemLayoutAdapter extends BaseAdapter {

    private List<User> userList;

    private Context context;
    private LayoutInflater layoutInflater;

    public CMemberRecItemItemLayoutAdapter(Context context,List<User> userList) {
        this.context = context;
        this.userList=userList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.c_member_rec_item_item_layout, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((User)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(User object, ViewHolder holder) {
        //TODO implement
        holder.cMemberRecItemItemTvMC.setText(object.getMajor()+object.getUserClass());
        holder.cMemberRecItemItemTvName.setText(object.getUsername());
    }

    protected class ViewHolder {
        private TextView cMemberRecItemItemTvMC;
    private TextView cMemberRecItemItemTvName;

        public ViewHolder(View view) {
            cMemberRecItemItemTvMC = (TextView) view.findViewById(R.id.c_member_rec_item_item_tv_M_C);
            cMemberRecItemItemTvName = (TextView) view.findViewById(R.id.c_member_rec_item_item_tv_Name);
        }
    }
}
