package com.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.day06qqdemo.R;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<String> headersList;
    private SelectImageListener selectImageListener;
    public RecyclerViewAdapter(Context context){
        this.context = context;
        headersList = new ArrayList<>();
    }
    public void setDatas(String[] strings){
        headersList = Arrays.asList(strings);
        this.notifyDataSetChanged();
    }
    public String getDatas(int position){
        return headersList.get(position);
    }
    public void setImageListener(SelectImageListener selectImageListener){
        this.selectImageListener = selectImageListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_select_heasers_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String s = headersList.get(position);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open("headers/" + s));

            holder.iv_select_item.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return headersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView iv_select_item;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_select_item = (ImageView) itemView.findViewById(R.id.iv_select_item);
            iv_select_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            selectImageListener.selectClick(v,getAdapterPosition());
        }
    }
    public interface SelectImageListener{
        void selectClick(View view,int position);
    }
}
