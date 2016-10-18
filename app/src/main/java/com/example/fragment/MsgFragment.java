package com.example.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adapter.MsgRecyclerViewAdapter;
import com.example.day06qqdemo.R;
import com.example.entity.UserEntity;
import com.oy.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/10/18.
 */
public class MsgFragment extends BaseFragment {
    @Bind(R.id.msg_recyclerview)
    public RecyclerView msg_recyclerview;
    public MsgRecyclerViewAdapter msgRecyclerViewAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_msg;
    }

    @Override
    public void init(View view) {
        msg_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        msgRecyclerViewAdapter = new MsgRecyclerViewAdapter(getActivity());
        msg_recyclerview.setAdapter(msgRecyclerViewAdapter);

    }

    @Override
    public void loadDatas() {
        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(new UserEntity("123"));
        userEntityList.add(new UserEntity("456"));
        msgRecyclerViewAdapter.setDatas(userEntityList);
    }
}
