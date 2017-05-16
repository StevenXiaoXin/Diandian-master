package com.daqianjietong.diandian.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.utils.SpUtil;

import org.xutils.x;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6.
 */

public class PersonCenterActivity extends BaseActivity {
    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.person_icon)
    ImageView personIcon;
    @BindView(R.id.person_name)
    TextView personName;
    @BindView(R.id.person_phone)
    TextView personPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personn_center;
    }

    @Override
    public void initView() {
        titleTitle.setText("个人中心");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setInfo();
    }

    private void setInfo(){
        personName.setText(SpUtil.getUsername());
        x.image().bind(personIcon,SpUtil.getPhoto());
        personPhone.setText(SpUtil.getPhone());
    }

    @OnClick({R.id.title_back, R.id.person_info, R.id.person_order, R.id.person_favore, R.id.person_updatepwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.person_info:
                Intent intent=new Intent(PersonCenterActivity.this,PersonInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.person_order:
                Intent intent2=new Intent(PersonCenterActivity.this,MyOrderActivity.class);
                startActivity(intent2);
                break;
            case R.id.person_favore:
                Intent intent3=new Intent(PersonCenterActivity.this,FavoreActivity.class);
                startActivity(intent3);
                break;
            case R.id.person_updatepwd:
                Intent intent4=new Intent(PersonCenterActivity.this,UpdatePwdActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
