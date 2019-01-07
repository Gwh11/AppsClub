package com.example.haoza.appsclub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.MyBmobArticle;
import com.example.haoza.appsclub.mInterface.ReplaceFragmentCallBack;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<MyBmobArticle> bmobArticleList;
    private ReplaceFragmentCallBack replaceFragmentCallBack;
    public PostAdapter(Context context,List<MyBmobArticle> bmobArticleList, ReplaceFragmentCallBack replaceFragmentCallBack) {
        this.context=context;
        this.bmobArticleList = bmobArticleList;
        this.replaceFragmentCallBack=replaceFragmentCallBack;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View infoView;
        ImageView infoImage;
        TextView infoText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            infoView=itemView;
            infoImage=itemView.findViewById(R.id.info_img);
            infoText=itemView.findViewById(R.id.info_tv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_rec_item_layout,viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.infoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBmobArticle bmobArticle=bmobArticleList.get(holder.getAdapterPosition());
                replaceFragmentCallBack.replaceFragment(bmobArticle);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MyBmobArticle bmobArticle = bmobArticleList.get(i);
        Glide.with(context).load(bmobArticle.getTitleImage().getUrl()).into(viewHolder.infoImage);
        viewHolder.infoText.setText(bmobArticle.getTitle());
    }

    @Override
    public int getItemCount() {
        return bmobArticleList.size();
    }

}
