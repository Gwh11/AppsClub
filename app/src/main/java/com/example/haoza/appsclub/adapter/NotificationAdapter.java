package com.example.haoza.appsclub.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.ClubNotification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<ClubNotification> clubNotificationList;

    public NotificationAdapter(List<ClubNotification> clubNotificationList) {
        this.clubNotificationList = clubNotificationList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView c_notification_rec_item_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            c_notification_rec_item_tv = itemView.findViewById(R.id.c_notification_rec_item_tv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.c_notification_rec_item_layout,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ClubNotification clubNotification=clubNotificationList.get(i);
        viewHolder.c_notification_rec_item_tv.setText(clubNotification.getNotificationInfo());
    }

    @Override
    public int getItemCount() {
        return clubNotificationList.size();
    }
}
