package com.example.day06qqdemo;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entity.RegisterUser;
import com.example.util.xmpp.Constants;
import com.example.util.xmpp.XmppUtil;
import com.oy.activity.BaseActivity;
import com.oy.util.SharedUtil;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/10/17.
 */
public class RegisterActivity extends BaseActivity {
    @Bind(R.id.btn_new_user_back)
    public Button btn_new_user_back;
    @Bind(R.id.btn_new_user_register)
    public Button btn_new_user_register;
    @Bind(R.id.et_register_username)
    public EditText et_register_username;
    @Bind(R.id.et_register_password)
    public EditText et_register_paddword;
    @Override
    protected int setContentId() {

        return R.layout.activity_register;
    }
    @OnClick({R.id.btn_new_user_back,R.id.btn_new_user_register})
    public void btnClick(View view){
        switch (view.getId()){
            case R.id.btn_new_user_back:
                //返回上一页面
                finish();
                break;
            case R.id.btn_new_user_register:
                //注册确认
                String username = et_register_username.getText().toString();
                String password = et_register_paddword.getText().toString();
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                //保存到共享参数
                SharedUtil.putString(Constants.USRENAME,username);
                SharedUtil.putString(Constants.PASSWORD,password);

                //注册
                int i = XmppUtil.registerUser(username, password);
                switch (i){
                    case 1:
                        //注册成功
                        Toast.makeText(RegisterActivity.this,"注册成功，记住密码哟",Toast.LENGTH_SHORT).show();
                        //调用EventBus传递参数

                        RegisterUser registerUser = new RegisterUser(username,password);
                        EventBus.getDefault().post(registerUser);
                        finish();
                        break;
                    case 2:
                        //用户名已经存在
                        Toast.makeText(RegisterActivity.this,"该用户名已经存在",Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                    case 3:
                        //注册失败
                        Toast.makeText(RegisterActivity.this,"原因不明",Toast.LENGTH_SHORT).show();
                        break;

                }
                break;
        }
    }
}
