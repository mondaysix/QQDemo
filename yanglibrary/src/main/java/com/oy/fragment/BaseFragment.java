package com.oy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public abstract class BaseFragment<T> extends Fragment {
    T data;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //1---EventBus注册
//        EventBus.getDefault().register(this);
        View view = inflater.inflate(getLayoutId(),container,false);
        return view;
    }
    public abstract int getLayoutId();
    public void init(View view){};
    public void loadDatas(){};
    public void setListener(){};
//    @Subscribe(threadMode = ThreadMode.MainThread, priority = 100, sticky = true)
//    public void  onEventMainThread(T data){};
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
     //bindview绑定
        ButterKnife.bind(this, view);
//        onEventMainThread(data);
        init(view);
        loadDatas();
        setListener();

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null){
            getDatas(bundle);
        }

    }
    protected void getDatas(Bundle bundle){
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
