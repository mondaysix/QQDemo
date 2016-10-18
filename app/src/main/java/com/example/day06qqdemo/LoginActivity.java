package com.example.day06qqdemo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.RegisterUser;
import com.example.entity.UserEntity;
import com.example.util.xmpp.Constants;
import com.example.util.xmpp.XmppUtil;
import com.oy.activity.BaseActivity;
import com.oy.util.SharedUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by Administrator on 2016/10/17.
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.btn_new_user)
    public Button btn_new_user;
    @Bind(R.id.login_ll)
    public LinearLayout login_ll;
    @Bind(R.id.btn_login)
    public Button btn_login;
    @Bind(R.id.et_login_username)
    public EditText et_login_username;
    @Bind(R.id.et_login_password)
    public EditText et_login_password;
    @Bind(R.id.forget_tv)
    public TextView forget_tv;
    @Bind(R.id.iv_login)
    public ImageView iv_login;
    @Override
    protected int setContentId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        //获得共享参数中的值
        String username = SharedUtil.getString(Constants.USRENAME);
        String password = SharedUtil.getString(Constants.PASSWORD);
        String header = SharedUtil.getString(Constants.HEADER);
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            et_login_username.setText(username);
            et_login_password.setText(password);
        }
       if (!TextUtils.isEmpty(header)){
           try {
               iv_login.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("headers/"+header)));
           } catch (IOException e) {
               e.printStackTrace();
           }

       }
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_login_layout);
        login_ll.setAnimation(animation);
        //设置退出的动画
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setExitTransition(fade);
        //动画执行结束finish
        getWindow().getExitTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                finish();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        //设置下划线
        forget_tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        forget_tv.getPaint().setAntiAlias(true);
        //注册EventBus
        EventBus.getDefault().register(this);

    }

    @OnClick({R.id.btn_new_user,R.id.btn_login})
    public void btnClick(View view){
        switch (view.getId()){
            case R.id.btn_new_user:
                //注册
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.btn_login:
                //登陆
                String username = et_login_username.getText().toString();
                String password = et_login_password.getText().toString();
                if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password) ){
                    Toast.makeText(LoginActivity.this,"用户名或者密码不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                boolean flag = XmppUtil.login(username, password);
                if (flag){

                    SharedUtil.putString(Constants.USRENAME,username);
                    SharedUtil.putString(Constants.PASSWORD,password);

                    //获取用户基本信息
                    UserEntity userInfo = XmppUtil.getUserInfo();
                    if (userInfo==null){
                        //跳转到基本信息设置页面
                        startActivity(new Intent(this,BaseInforActivity.class));
                        finish();
                    }
                    else {
                        //登陆成功
                        SharedUtil.putString(Constants.NICKNAME,userInfo.getNickName());
                        SharedUtil.putString(Constants.HEADER,userInfo.getUserHeader());
                        SharedUtil.putString(Constants.SIGNATURE,userInfo.getSignature());
                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this
                                , iv_login, "headerImage");

                        startActivity(new Intent(this,MainActivity.class),activityOptions.toBundle());
                    }

                }else {
                    Toast.makeText(LoginActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void event(RegisterUser registerUser){
        if (registerUser!=null){
            et_login_username.setText(registerUser.getUsername());
            et_login_password.setText(registerUser.getPassword());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
