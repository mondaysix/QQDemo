package com.example.day06qqdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
public class BaseInforActivity extends BaseActivity {
    @Bind(R.id.iv_header)
    public ImageView iv_header;
    @Bind(R.id.btn_change_header)
    public Button btn_change_header;
    @Bind(R.id.et_nickname)
    public EditText et_nickname;
    @Bind(R.id.et_signture)
    public EditText et_signture;
    @Bind(R.id.btn_save_change)
    public Button btn_save_change;
    public Bitmap bitmap;
    public String strHeader;




    @Override
    protected int setContentId() {
        return R.layout.activity_baseinfor;
    }
    @Override
    protected void init() {
        EventBus.getDefault().register(this);

    }
    @OnClick({R.id.btn_change_header,R.id.btn_save_change})
    public void btnClick(View view){
        switch (view.getId()){
            case R.id.btn_change_header:
                //更换头像
                startActivity(new Intent(BaseInforActivity.this
                ,SelectHeaderActivity.class));
                break;
            case R.id.btn_save_change:
                //保存信息
                String nickname = et_nickname.getText().toString();
                String signature = et_signture.getText().toString();
                if(TextUtils.isEmpty(nickname)){
                    Toast.makeText(BaseInforActivity.this, "昵称不能为空！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(bitmap == null){
                    Toast.makeText(BaseInforActivity.this, "请选择一个头像！", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(signature)){
                    signature = "nothing is leaving";
                }

                //保存信息
                UserEntity userEntity = new UserEntity();
                userEntity.setNickName(nickname);
                userEntity.setSignature(signature);
                userEntity.setUserHeader(strHeader);


                boolean flag = XmppUtil.saveUserInfo(userEntity);
                if(flag){
                    //保存到共享参数
                    SharedUtil.putString(Constants.NICKNAME,userEntity.getNickName());
                    SharedUtil.putString(Constants.HEADER,userEntity.getUserHeader());
                    SharedUtil.putString(Constants.SIGNATURE,userEntity.getSignature());

                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(BaseInforActivity.this, "保存失败！估计哪里有问题", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void event(String str){
        try {
            strHeader = str;
            bitmap = BitmapFactory.decodeStream(getAssets().open("headers/"+str)) ;
            iv_header.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
