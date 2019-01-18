package com.example.haoza.appsclub.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.ClubActivity;
import com.example.haoza.appsclub.mInterface.ReplaceFragmentCallBack;

public class CActivityPtrlistFragLayoutAdapter extends BaseAdapter {

    private List<ClubActivity> clubActivityList;
    private ReplaceFragmentCallBack replaceFragmentCallBack;

    private Context context;
    private LayoutInflater layoutInflater;

    public CActivityPtrlistFragLayoutAdapter(Context context,List<ClubActivity> clubActivityList, ReplaceFragmentCallBack replaceFragmentCallBack) {
        this.context = context;
        this.clubActivityList = clubActivityList;
        this.replaceFragmentCallBack = replaceFragmentCallBack;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return clubActivityList.size();
    }

    @Override
    public ClubActivity getItem(int position) {
        return clubActivityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.c_activity_rec_item_layout, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ClubActivity)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final ClubActivity object, final ViewHolder holder) {
        //TODO implement
        holder.c_activity_rec_item_tv_title.setText(object.getActivityTitle());
        holder.c_activity_rec_item_tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentCallBack.replaceActivityInfo(object);
            }
        });

    }

    protected class ViewHolder {
        TextView c_activity_rec_item_tv_title;

        public ViewHolder(View view) {
            c_activity_rec_item_tv_title = view.findViewById(R.id.c_activity_rec_item_tv_title);
        }
    }
}
