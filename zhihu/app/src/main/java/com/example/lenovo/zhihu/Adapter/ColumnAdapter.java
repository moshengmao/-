package com.example.lenovo.zhihu.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.zhihu.Bean.Column;
import com.example.lenovo.zhihu.R;

import java.util.List;

/**
 * Created by lenovo on 2018/12/12.
 */

public class ColumnAdapter extends RecyclerView.Adapter<ColumnAdapter.ViewHolder>{
    private List<Column>columnList;
    private Context mcontext;

    public ColumnAdapter( List<Column>colList){this.columnList=colList;}

    static class ViewHolder extends RecyclerView.ViewHolder{
               TextView columndescription;
               ImageView thumbnail;
               TextView columnName;
               View ColumnView;
               Context context;
        public ViewHolder(View itemView){
            super(itemView);
            ColumnView=itemView;
            columnName=itemView.findViewById(R.id.tv2);
           columndescription=itemView.findViewById(R.id.tv1);
            thumbnail=itemView.findViewById(R.id.iv);
        }
    }
    @Override
    public ColumnAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.column_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder .context=parent .getContext() ;
        //可注册点击事件
        return holder;
    }
    @Override
    public void onBindViewHolder(ColumnAdapter.ViewHolder holder, int position) {
           Column column=columnList.get(position);
          holder.columndescription.setText(column.getDescription());
           holder.columnName.setText(column.getName());
        Glide.with(holder.context).load(column.getThumbnail()).override(600,200).crossFade().into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return columnList.size();
    }
}
