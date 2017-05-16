package com.daqianjietong.diandian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.adapter.CarportAdapter;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.model.CarPortEntity;
import com.daqianjietong.diandian.model.PartEntity;
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
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6.
 */

public class CarportActivity extends BaseActivity {
    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.carport_list)
    PullToRefreshListView carportList;

    private String parkId;
    private CarportAdapter adapter;

    private boolean proFlag = true;
    private int pageIndex = 1;
    private int pageFlag=1;//1代表约车 2代表租车
    private List<CarPortEntity> carPortEntities=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_carport;
    }

    @Override
    public void initView() {
        parkId=getIntent().getStringExtra("parkId");
        pageFlag=getIntent().getIntExtra("pageFlag",1);
        if (pageFlag==1){
            titleTitle.setText("预约车位");
        }else{
            titleTitle.setText("租赁车位");
        }
        adapter=new CarportAdapter(this,carPortEntities,pageFlag);
        carportList.setAdapter(adapter);

        adapter.setOnAppointClickLister(new CarportAdapter.OnAppointClickLister() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(CarportActivity.this,TimeSelectActivity.class);
                intent.putExtra("pageFlag",pageFlag);
                intent.putExtra("parkId",carPortEntities.get(position).ps_id);
                startActivity(intent);
            }
        });
        carportList.setMode(PullToRefreshBase.Mode.BOTH);
        carportList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                closePro();
                if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_START)) {
                    parkingIndex(1,"");
                } else if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                    parkingIndex(pageIndex + 1,"");
                }

            }

        });
        parkingIndex(1, "");

    }


    private void openPro(){
        proFlag=true;
    }

    private void closePro(){
        proFlag=false;
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }


    private void parkingIndex(final int pageNo,String name){
        if (proFlag){
            showDialog();
        }
        Api.getInstance().parklist(pageNo,parkId,name,new HttpUtil.URLListenter<List<CarPortEntity>>() {
            @Override
            public void onsucess(List<CarPortEntity> entities) throws Exception {
                dissDialog();
                carportList.onRefreshComplete();
                if (pageNo == 1) {
                    carPortEntities.clear();
                    adapter.notifyDataSetChanged();
                }
                pageIndex=pageNo;
                if (entities!=null){
                    for (CarPortEntity carPortEntity:entities){
                        carPortEntities.add(carPortEntity);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
                carportList.onRefreshComplete();
            }
        });
    }


}
