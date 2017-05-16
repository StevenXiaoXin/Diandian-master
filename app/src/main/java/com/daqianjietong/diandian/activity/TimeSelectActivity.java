package com.daqianjietong.diandian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.model.CarPortEntity;
import com.daqianjietong.diandian.model.ReserveEntity;
import com.daqianjietong.diandian.utils.Api;
import com.daqianjietong.diandian.utils.HttpUtil;
import com.daqianjietong.diandian.utils.SpUtil;
import com.daqianjietong.diandian.utils.StrHexStr;
import com.daqianjietong.diandian.utils.TimeUtils;
import com.daqianjietong.diandian.utils.ToastUtil;

import org.feezu.liuli.timeselector.TimeSelector;

import java.sql.Time;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6.
 */

public class TimeSelectActivity extends BaseActivity {


    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.timeselect_start)
    TextView timeselectStart;
    @BindView(R.id.timeselect_end)
    TextView timeselectEnd;

    private String startTime;
    private String stopTime;
    private int pageFlag;//1代表约车 2代表租车
    private String parkId;//停车位id

    @Override
    public int getLayoutId() {
        return R.layout.activity_timeselect;
    }

    @Override
    public void initView() {
        pageFlag=getIntent().getIntExtra("pageFlag",1);
        parkId=getIntent().getStringExtra("parkId");
        titleTitle.setText("时段选择");
        startTime= TimeUtils.getCurTime();
        stopTime= TimeUtils.getCurNextHourTime();
        timeselectStart.setText(TimeUtils.getShowTime(startTime));
        timeselectEnd.setText(TimeUtils.getShowTime(stopTime));

    }


    @OnClick({R.id.title_back, R.id.timeselect_startlin, R.id.timeselect_endlin, R.id.timeselect_appoint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.timeselect_startlin:
                TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        startTime=time;
                        timeselectStart.setText(TimeUtils.getShowTime(startTime));
                    }
                }, TimeUtils.getCurTime(), TimeUtils.getMaxTime());

                timeSelector.setScrollUnit(TimeSelector.SCROLLTYPE.YEAR,TimeSelector.SCROLLTYPE.MONTH,TimeSelector.SCROLLTYPE.DAY,TimeSelector.SCROLLTYPE.HOUR, TimeSelector.SCROLLTYPE.MINUTE);
                timeSelector.show();
                break;
            case R.id.timeselect_endlin:
                TimeSelector timeSelector2 = new TimeSelector(this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        stopTime=time;
                        timeselectEnd.setText(TimeUtils.getShowTime(stopTime));
                    }
                }, TimeUtils.getCurTime(), TimeUtils.getMaxTime());

                timeSelector2.setScrollUnit(TimeSelector.SCROLLTYPE.YEAR,TimeSelector.SCROLLTYPE.MONTH,TimeSelector.SCROLLTYPE.DAY,TimeSelector.SCROLLTYPE.HOUR, TimeSelector.SCROLLTYPE.MINUTE);
                timeSelector2.show();
                break;
            case R.id.timeselect_appoint:
                if (stopTime.compareTo(startTime)<=0){
                    ToastUtil.show("开始时间不能大于结束时间！");
                    return;
                }
                reservePark(parkId);
                break;
        }
    }

    private void reservePark(String parkId){
        showDialog();
        Api.getInstance().reservePark(SpUtil.getUid(),parkId,String.valueOf(TimeUtils.getMillon(startTime)),String.valueOf(TimeUtils.getMillon(stopTime)), String.valueOf(pageFlag),new HttpUtil.URLListenter<ReserveEntity>() {
            @Override
            public void onsucess(ReserveEntity reserveEntity) throws Exception {
                dissDialog();
                Intent intent=new Intent(TimeSelectActivity.this,PayActivity.class);
                intent.putExtra("reserve",reserveEntity);
                startActivity(intent);
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }
}
