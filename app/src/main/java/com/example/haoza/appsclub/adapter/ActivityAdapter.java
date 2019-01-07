package com.example.haoza.appsclub.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.ClubActivity;
import com.example.haoza.appsclub.mInterface.ReplaceFragmentCallBack;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private List<ClubActivity> clubActivityList;
    private ReplaceFragmentCallBack replaceFragmentCallBack;

    public ActivityAdapter(List<ClubActivity> clubActivityList, ReplaceFragmentCallBack replaceFragmentCallBack) {
        this.clubActivityList = clubActivityList;
        this.replaceFragmentCallBack = replaceFragmentCallBack;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView c_activity_rec_item_tv_title;
/*        private final Button c_activity_rec_item_btn_ok;
        private final Button c_activity_rec_item_btn_quit;*/

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            c_activity_rec_item_tv_title = itemView.findViewById(R.id.c_activity_rec_item_tv_title);
/*            c_activity_rec_item_btn_ok = itemView.findViewById(R.id.c_activity_rec_item_btn_ok);
            c_activity_rec_item_btn_quit = itemView.findViewById(R.id.c_activity_rec_item_btn_quit);*/
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.c_activity_rec_item_layout,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final ClubActivity clubActivity=clubActivityList.get(i);
        viewHolder.c_activity_rec_item_tv_title.setText(clubActivity.getActivityTitle());
        viewHolder.c_activity_rec_item_tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentCallBack.replaceActivityInfo(clubActivity);
            }
        });
        /*viewHolder.c_activity_rec_item_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.c_activity_rec_item_btn_ok.setEnabled(false);
                viewHolder.c_activity_rec_item_btn_quit.setEnabled(true);
                //参加活动
            }
        });
        viewHolder.c_activity_rec_item_btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.c_activity_rec_item_btn_ok.setEnabled(true);
                viewHolder.c_activity_rec_item_btn_quit.setEnabled(false);
                //取消参加
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return clubActivityList.size();
    }

}
