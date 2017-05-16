package com.daqianjietong.diandian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.model.ReserveEntity;
import com.daqianjietong.diandian.utils.TimeUtils;

import java.sql.Time;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/7.
 */

public class PayActivity extends BaseActivity {
    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.pay_address)
    TextView payAddress;
    @BindView(R.id.pay_time)
    TextView payTime;
    @BindView(R.id.pay_duration)
    TextView payDuration;
    private ReserveEntity reserveEntity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initView() {
        titleTitle.setText("付款支付");
        reserveEntity= (ReserveEntity) getIntent().getSerializableExtra("reserve");
        payMoney.setText(reserveEntity.r_money);
        payAddress.setText(reserveEntity.r_parkname);
        payTime.setText(TimeUtils.getShowTime2(reserveEntity.r_starttime)+"至\n"+TimeUtils.getShowTime2(reserveEntity.r_endtime));
        payDuration.setText(TimeUtils.getStr(reserveEntity.r_endtime,reserveEntity.r_starttime));
    }


    @OnClick({R.id.title_back, R.id.pay_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.pay_ok:
                break;
        }
    }
}
