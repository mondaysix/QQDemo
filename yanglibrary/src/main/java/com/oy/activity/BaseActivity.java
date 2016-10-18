package com.oy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public abstract class BaseActivity<T> extends AppCompatActivity {
//    T data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(setContentId());
//        //1---EventBus注册
//        EventBus.getDefault().register(this);

        //注册activity
        ButterKnife.bind(this);
        init();
        loadData();
//        onEventMainThread(data);
        onItemListener();
    }
    /**
     * 布局id
     * @return
     */
    protected abstract int setContentId();
    protected  void loadData(){};
    protected  void init(){};
    protected  void onItemListener(){};
//    @Subscribe(threadMode = ThreadMode.MainThread, priority = 100, sticky = true)
//    public void  onEventMainThread(T data){};
    /**
     * activity的跳转
     * @param intent
     * @param srcid
     * @param desid
     */
    protected void startActivityForAnimation(Intent intent,int srcid,int desid){
        startActivity(intent);
        overridePendingTransition(srcid,desid);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
