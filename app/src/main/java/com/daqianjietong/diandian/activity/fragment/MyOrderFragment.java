package com.daqianjietong.diandian.activity.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.adapter.MyOrderAdapter;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.base.BaseFragment;
import com.daqianjietong.diandian.model.PartEntity;
import com.daqianjietong.diandian.model.PayListEntity;
import com.daqianjietong.diandian.utils.Api;
import com.daqianjietong.diandian.utils.HttpUtil;
import com.daqianjietong.diandian.utils.SpUtil;
import com.daqianjietong.diandian.utils.ToastUtil;
import com.daqianjietong.diandian.utils.ToosUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyOrderFragment extends BaseFragment {


    @BindView(R.id.fmyorder_list)
    PullToRefreshListView fmyorderList;

    private MyOrderAdapter adapter;
    private String type;
    private List<PayListEntity> payListEntities=new ArrayList<>();
    private boolean proFlag = true;
    private int pageIndex = 1;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.fragment_myorder;
    }

    public MyOrderFragment() {

    }

    public static MyOrderFragment newInstance(String type) {
        MyOrderFragment fragment = new MyOrderFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString("type");
        }
    }

    @Override
    public void initView(View view) {
        adapter=new MyOrderAdapter(getActivity(),payListEntities,type,handler);
        fmyorderList.setAdapter(adapter);
        fmyorderList.setMode(PullToRefreshBase.Mode.BOTH);
        fmyorderList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                closePro();
                if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_START)) {
                    orderList(1);
                } else if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                    orderList(pageIndex + 1);
                }

            }

        });
        orderList(1);
    }

    private void openPro(){
        proFlag=true;
    }

    private void closePro(){
        proFlag=false;
    }

    private void orderList(final int pageNo){
        if (proFlag){
            ((BaseActivity)getActivity()).showDialog();
        }
        String state="1";
        if ("cur".equals(type)){
            state="1";
        }else{
            state="2";
        }
        Api.getInstance().orderList(pageNo, SpUtil.getUid(),state,new HttpUtil.URLListenter<List<PayListEntity>>() {
            @Override
            public void onsucess(List<PayListEntity> entities) throws Exception {
                ((BaseActivity)getActivity()).dissDialog();
                fmyorderList.onRefreshComplete();
                if (pageNo == 1) {
                    payListEntities.clear();
                    adapter.notifyDataSetChanged();
                }
                pageIndex=pageNo;
                if (entities!=null){
                    for (PayListEntity partEntity:entities){
                        payListEntities.add(partEntity);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onfaild(String error) {
                ((BaseActivity)getActivity()).dissDialog();
                ToastUtil.show(error);
                fmyorderList.onRefreshComplete();
            }
        });
    }

}
