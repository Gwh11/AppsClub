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
import com.example.haoza.appsclub.customObject.ClubNotification;

public class CNotificationRecItemLayoutAdapter extends BaseAdapter {

    private List<ClubNotification> objects;

    private Context context;
    private LayoutInflater layoutInflater;

    public CNotificationRecItemLayoutAdapter(Context context,List<ClubNotification> clubNotificationList) {
        this.context = context;
        this.objects=clubNotificationList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ClubNotification getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.c_notification_rec_item_layout, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ClubNotification)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(ClubNotification object, ViewHolder holder) {
        //TODO implement
        holder.cNotificationRecItemTv.setText(object.getNotificationInfo());
    }

    protected class ViewHolder {
        private TextView cNotificationRecItemTv;

        public ViewHolder(View view) {
            cNotificationRecItemTv = (TextView) view.findViewById(R.id.c_notification_rec_item_tv);
        }
    }
}
