package com.daqianjietong.diandian.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;

/**
 * Created by MuWenlei on 16/10/15.
 */
public abstract class BaseFragment  extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(this.getLayoutId(),null);
        ButterKnife.bind(this,rootView);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initView(rootView);
    }

    public void init(Bundle savedInstanceState){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public abstract int getLayoutId();


    public abstract void initView(View view);


}
