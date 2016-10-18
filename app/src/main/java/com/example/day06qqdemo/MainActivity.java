package com.example.day06qqdemo;

import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragment.ContactFragment;
import com.example.fragment.DynamicFragment;
import com.example.fragment.MsgFragment;
import com.example.util.xmpp.Constants;
import com.oy.activity.BaseActivity;
import com.oy.util.SharedUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    //底部imageview
    @Bind(R.id.iv_msg)
    public ImageView iv_msg;
    @Bind(R.id.iv_contact)
    public ImageView iv_contact;
    @Bind(R.id.iv_setup)
    public ImageView iv_setup;
    @Bind(R.id.main_image_header)
    public ImageView main_image_header;
    //toolbar相关设置
    @Bind(R.id.main_tv_title)
    public TextView main_tv_title;
    @Bind(R.id.main_tv_more)
    public TextView main_tv_more;
    @Override
    protected int setContentId() {
        return R.layout.activity_main;
    }
    @Override
    protected void init() {
        String header = SharedUtil.getString(Constants.HEADER);
        try {
            main_image_header.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("headers/"+header)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置进入的动画
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
        iv_msg.performClick();//默认点击
    }

    /**
     * back键退出---
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
//            finishAfterTransition();
            finish();
        }
        return true;
    }

    @OnClick({R.id.iv_msg,R.id.iv_contact,R.id.iv_setup})
    public void btnClick(View view){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        switch (view.getId()){
            case R.id.iv_msg:
                iv_msg.setImageResource(R.drawable.skin_tab_icon_conversation_selected);
                iv_contact.setImageResource(R.drawable.skin_tab_icon_contact_normal);
                iv_setup.setImageResource(R.drawable.skin_tab_icon_setup_normal);
                main_tv_title.setText("消息");
                main_tv_more.setText("+");
                fragmentManager.beginTransaction().replace(R.id.main_fragment,new MsgFragment()).commit();
                break;
            case R.id.iv_contact:
                iv_contact.setImageResource(R.drawable.skin_tab_icon_contact_selected);
                iv_msg.setImageResource(R.drawable.skin_tab_icon_conversation_normal);
                iv_setup.setImageResource(R.drawable.skin_tab_icon_setup_normal);

                main_tv_title.setText("联系人");
                main_tv_more.setText("添加");
                fragmentManager.beginTransaction().replace(R.id.main_fragment,new ContactFragment()).commit();
                break;
            case R.id.iv_setup:
                iv_setup.setImageResource(R.drawable.skin_tab_icon_setup_selected);
                iv_contact.setImageResource(R.drawable.skin_tab_icon_contact_normal);
                iv_msg.setImageResource(R.drawable.skin_tab_icon_conversation_normal);

                main_tv_title.setText("动态");
                main_tv_more.setText("更多");
                fragmentManager.beginTransaction().replace(R.id.main_fragment,new DynamicFragment()).commit();
                break;
        }
    }
}