package com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.day06qqdemo.R;
import com.example.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleImage.CircleImageView;

/**
 * Created by Administrator on 2016/10/18.
 */
public class MsgRecyclerViewAdapter extends RecyclerView.Adapter<MsgRecyclerViewAdapter.MyViewHolder> {
    public List<UserEntity> userEntities;
    public Context context;
    public MsgRecyclerViewAdapter(Context context){
        this.context= context;
        userEntities = new ArrayList<>();
    }
    public void setDatas(List<UserEntity> userEntities){
        this.userEntities = userEntities;
        this.notifyDataSetChanged();
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //参数3：attachToRoot：是否将root附加到布局文件的根视图上
        //参数2：root会协助linearlayout的根节点生成布局参数
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main_msg_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.nickName.setText(userEntities.get(position).getNickName());
    }

    @Override
    public int getItemCount() {
        return userEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView imageView;
        public TextView nickName;
        public MyViewHolder(View itemView) {
            super(itemView);
            nickName = (TextView) itemView.findViewById(R.id.tv_nickname);

        }
    }
}
