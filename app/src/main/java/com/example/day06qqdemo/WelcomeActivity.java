package com.example.day06qqdemo;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;

import com.example.util.xmpp.XmppConnectionUtil;
import com.oy.activity.BaseActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/10/17.
 */
public class WelcomeActivity extends BaseActivity {
    @Bind(R.id.welcome_iv)
    public ImageView welcome_iv;
    @Override
    protected int setContentId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init() {
        //连接服务器
        new Thread(){
            @Override
            public void run() {
                boolean flag = XmppConnectionUtil.openConnection();
                Log.d("msg", "run: "+flag);
            }
        }.start();
        welcome_iv.postDelayed(new Runnable() {
            @Override
            public void run() {
                //延迟2秒跳转到登陆页面
                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
