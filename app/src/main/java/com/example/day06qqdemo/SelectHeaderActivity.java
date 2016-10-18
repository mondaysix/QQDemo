package com.example.day06qqdemo;

import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adapter.RecyclerViewAdapter;
import com.oy.activity.BaseActivity;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/10/17.
 */
public class SelectHeaderActivity extends BaseActivity implements RecyclerViewAdapter.SelectImageListener {
    @Bind(R.id.iv_select)
    public ImageView iv_select;
    @Bind(R.id.recyclerView)
    public RecyclerView recyclerView;
    public RecyclerViewAdapter recyclerViewAdapter;

    public String datas;
    @Override
    protected int setContentId() {
        return R.layout.activity_select_headers;
    }

    @Override
    protected void init() {
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerViewAdapter.setImageListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void loadData() {
        try {
            String[] headerses = getAssets().list("headers");
            recyclerViewAdapter.setDatas(headerses);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void selectClick(View view, int position) {
         datas = recyclerViewAdapter.getDatas(position);
        try {
            iv_select.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("headers/"+datas)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.btn_select_header_ok)
    public void btnClick(View view){
        if (datas==null){
            Toast.makeText(SelectHeaderActivity.this,"没有选择头像",Toast.LENGTH_SHORT).show();

        }
        else {
            EventBus.getDefault().post(datas);
            finish();
        }
    }
}
