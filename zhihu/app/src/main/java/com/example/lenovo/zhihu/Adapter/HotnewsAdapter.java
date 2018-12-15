package com.example.lenovo.zhihu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.zhihu.Bean.HotNews;
import com.example.lenovo.zhihu.ColumnActivity;
import com.example.lenovo.zhihu.HotNewsActivity;
import com.example.lenovo.zhihu.R;
import com.example.lenovo.zhihu.WebviewActivity;

import java.util.List;

/**
 * Created by lenovo on 2018/12/4.
 */

    public class HotnewsAdapter extends RecyclerView.Adapter<HotnewsAdapter.ViewHolder> {
        private List<HotNews> list;
        private Context mcontext;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);   }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView hotNews_title;
        ImageView hotNews_thumbnail;
        Context context;
        View HotnewsView;
        public ViewHolder(View itemView) {
            super(itemView);
            HotnewsView=itemView;
            hotNews_thumbnail=(ImageView) itemView.findViewById(R.id.hotNews_thumbnail);
            hotNews_title=(TextView) itemView.findViewById(R.id.hotNews_title);
        }
    }
    public HotnewsAdapter(List<HotNews>mList,Context context){
        this.list=mList;this.mcontext=context;
    }
    @Override
    public   HotnewsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hotnewsitem,viewGroup,false);
         final  ViewHolder holder=new ViewHolder(view);
        holder .context= viewGroup.getContext() ;
        /*int position;
        HotNews hotNews=list.get(position);
        // 可注册点击事件
        */
        return holder;
    }

    @Override
    public void onBindViewHolder(HotnewsAdapter.ViewHolder holder, int position) {
        HotNews hotNews=list.get(position);
        holder.hotNews_title.setText(hotNews.getTitle());
       // holder.hotNews_url.setText(hotNews.getUrl());
       // holder.hotNews_id.setText(hotNews.getNews_id());
        Glide.with(holder.context).load(hotNews.getThumbnail()).override(600,200).into(holder.hotNews_thumbnail);
        //通过接口名调用方法
        mOnItemClickListener.onItemClick(holder.HotnewsView, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    }
