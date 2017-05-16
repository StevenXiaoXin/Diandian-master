package com.daqianjietong.diandian.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.adapter.FavoreAdapter;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.model.FavoreEntity;
import com.daqianjietong.diandian.model.MyFavoreEntity;
import com.daqianjietong.diandian.utils.Api;
import com.daqianjietong.diandian.utils.HttpUtil;
import com.daqianjietong.diandian.utils.SpUtil;
import com.daqianjietong.diandian.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6.
 */

public class FavoreActivity extends BaseActivity {

    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.favore_list)
    ListView favoreList;

    private FavoreAdapter adapter;
    private List<MyFavoreEntity> entities=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_favore;
    }

    @Override
    public void initView() {
        titleTitle.setText("我的停车券");
        adapter=new FavoreAdapter(this,entities);
        favoreList.setAdapter(adapter);
        parkingCoupon();
    }


    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    private void parkingCoupon(){
        showDialog();
        Api.getInstance().myCoupon(SpUtil.getUid(), new HttpUtil.URLListenter<List<MyFavoreEntity>>() {
            @Override
            public void onsucess(List<MyFavoreEntity> favoreEntities) throws Exception {
                dissDialog();
                if (favoreEntities==null){
                    return;
                }
                for (MyFavoreEntity favoreEntity:favoreEntities){
                    entities.add(favoreEntity);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }
}
