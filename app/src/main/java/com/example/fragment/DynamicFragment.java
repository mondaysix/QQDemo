package com.example.fragment;

import android.view.View;
import android.widget.EditText;

import com.example.day06qqdemo.R;
import com.oy.fragment.BaseFragment;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/10/18.
 */
public class DynamicFragment extends BaseFragment {
    @Bind(R.id.common_et_search)
    public EditText common_et_search;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main_dynamic;
    }

    @Override
    public void init(View view) {
    }
}
