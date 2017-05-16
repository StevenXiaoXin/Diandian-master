package com.daqianjietong.diandian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.adapter.GetFavoreAdapter;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.model.FavoreEntity;
import com.daqianjietong.diandian.model.UserInfoBean;
import com.daqianjietong.diandian.utils.Api;
import com.daqianjietong.diandian.utils.HttpUtil;
import com.daqianjietong.diandian.utils.SpUtil;
import com.daqianjietong.diandian.utils.ToastUtil;
import com.daqianjietong.diandian.utils.ToosUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/7.
 */

public class GetFavoreActivity extends BaseActivity {


    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.title_get)
    ImageView titleGet;
    @BindView(R.id.getfavore_list)
    ListView getfavoreList;

    private GetFavoreAdapter adapter;
    private List<FavoreEntity> entities=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_getfavore;
    }

    @Override
    public void initView() {
        titleTitle.setText("停车券");
        titleGet.setVisibility(View.VISIBLE);
        adapter=new GetFavoreAdapter(this,entities);
        getfavoreList.setAdapter(adapter);
        parkingCoupon();
        adapter.setOnDrawClickLister(new GetFavoreAdapter.OnDrawClickLister() {
            @Override
            public void onClick(int position) {
                receiveCoupon(position);
            }
        });
    }


    @OnClick({R.id.title_back, R.id.title_get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_get:
                Intent intent=new Intent(GetFavoreActivity.this,FavoreActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void parkingCoupon(){
        showDialog();
        Api.getInstance().parkingCoupon(SpUtil.getUid(), new HttpUtil.URLListenter<List<FavoreEntity>>() {
            @Override
            public void onsucess(List<FavoreEntity> favoreEntities) throws Exception {
                dissDialog();
                if (favoreEntities==null){
                    return;
                }
                for (FavoreEntity favoreEntity:favoreEntities){
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
    private void receiveCoupon(final int position){
        showDialog();
        Api.getInstance().receiveCoupon(SpUtil.getUid(),entities.get(position).c_id,entities.get(position).c_type, new HttpUtil.URLListenter<String>() {
            @Override
            public void onsucess(String string) throws Exception {
                dissDialog();
                entities.get(position).c_status="1";
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
