package com.example.haoza.appsclub.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.MyBmobArticle;
import com.example.haoza.appsclub.mInterface.ReplaceFragmentCallBack;

public class HomePtrlistFragLayoutAdapter extends BaseAdapter {

    private Context context;
    private List<MyBmobArticle> bmobArticleList;
    private ReplaceFragmentCallBack replaceFragmentCallBack;

    private LayoutInflater layoutInflater;

    public HomePtrlistFragLayoutAdapter(Context context, List<MyBmobArticle> bmobArticleList, ReplaceFragmentCallBack replaceFragmentCallBack) {
        this.context = context;
        this.bmobArticleList = bmobArticleList;
        this.replaceFragmentCallBack = replaceFragmentCallBack;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bmobArticleList.size();
    }

    @Override
    public MyBmobArticle getItem(int position) {
        return bmobArticleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.home_rec_item_layout, parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentCallBack.replaceFragment(getItem(position));
            }
        });
        initializeViews((MyBmobArticle)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(MyBmobArticle object, ViewHolder holder) {
        //TODO implement
        Glide.with(context).load(object.getTitleImage().getUrl()).into(holder.infoImage);
        holder.infoText.setText(object.getmTitle());
    }

    protected class ViewHolder {
        View infoView;
        ImageView infoImage;
        TextView infoText;

        public ViewHolder(View view) {
            infoView=view;
            infoImage=view.findViewById(R.id.info_img);
            infoText=view.findViewById(R.id.info_tv);
        }
    }
}
