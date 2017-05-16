package com.daqianjietong.diandian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.navi.model.NaviLatLng;
import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.adapter.PartListAdapter;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.model.PartEntity;
import com.daqianjietong.diandian.model.PhotoEntity;
import com.daqianjietong.diandian.utils.Api;
import com.daqianjietong.diandian.utils.HttpUtil;
import com.daqianjietong.diandian.utils.SpUtil;
import com.daqianjietong.diandian.utils.ToastUtil;
import com.daqianjietong.diandian.utils.ToosUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6.
 */

public class PartListActivity extends BaseActivity {
    @BindView(R.id.partlist_search_text)
    EditText partlistSearchText;
    @BindView(R.id.partlist_search_info)
    TextView partlistSearchInfo;
    @BindView(R.id.partlist_search_list)
    PullToRefreshListView pullToRefreshListView;
    private PartListAdapter adapter;

    private boolean proFlag = true;
    private int pageIndex = 1;

    private int pageFlag=1;//1代表约车 2代表租车

    private AMapLocation myMapLocation;

    private List<PartEntity> partEntities=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_partlist;
    }

    @Override
    public void initView() {
        myMapLocation=getIntent().getParcelableExtra("local");
        pageFlag=getIntent().getIntExtra("pageFlag",1);
        adapter=new PartListAdapter(this,partEntities);
        pullToRefreshListView.setAdapter(adapter);
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(PartListActivity.this,CarportActivity.class);
                intent.putExtra("parkId",partEntities.get(position-1).p_id);
                intent.putExtra("pageFlag",pageFlag);
                startActivity(intent);
            }
        });

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                closePro();
                if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_START)) {
                    parkingIndex(1, ToosUtils.getTextContent(partlistSearchText));
                } else if (refreshView.getCurrentMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                    parkingIndex(pageIndex + 1, ToosUtils.getTextContent(partlistSearchText));
                }

            }

        });
        parkingIndex(1, ToosUtils.getTextContent(partlistSearchText));

        adapter.setOnGpsClickLister(new PartListAdapter.OnGpsClickLister() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(PartListActivity.this,GPSNaviAcyivity.class);
                intent.putExtra("part",partEntities.get(position));
                intent.putExtra("myNaviLatLng",new NaviLatLng(myMapLocation.getLatitude(),myMapLocation.getLongitude()));
                startActivity(intent);
            }
        });
    }

    private void openPro(){
        proFlag=true;
    }

    private void closePro(){
        proFlag=false;
    }



    @OnClick({R.id.title_back, R.id.partlist_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.partlist_search:
                pageIndex=1;
                partEntities.clear();
                adapter.notifyDataSetChanged();
                openPro();
                parkingIndex(pageIndex, ToosUtils.getTextContent(partlistSearchText));
                break;
        }
    }


    private void parkingIndex(final int pageNo, String name){
        if (myMapLocation==null){
            return;
        }
        if (proFlag){
            showDialog();
        }
        Api.getInstance().parkingIndex(pageNo,String.valueOf(myMapLocation.getLatitude()),String.valueOf(myMapLocation.getLongitude()),name,new HttpUtil.URLListenter<List<PartEntity>>() {
            @Override
            public void onsucess(List<PartEntity> entities) throws Exception {
                dissDialog();
                pullToRefreshListView.onRefreshComplete();
                if (pageNo == 1) {
                    partEntities.clear();
                    adapter.notifyDataSetChanged();
                }
                pageIndex=pageNo;
                if (entities!=null){
                    for (PartEntity partEntity:entities){
                        partEntities.add(partEntity);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
                pullToRefreshListView.onRefreshComplete();
            }
        });
    }
}
