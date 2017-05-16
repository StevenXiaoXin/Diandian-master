package com.daqianjietong.diandian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.utils.Api;
import com.daqianjietong.diandian.utils.HttpUtil;
import com.daqianjietong.diandian.utils.ToastUtil;
import com.daqianjietong.diandian.utils.ToosUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/7.
 */

public class UpdatePwdActivity extends BaseActivity {
    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.updatepwd_oldpwd)
    EditText updatepwdOldpwd;
    @BindView(R.id.updatepwd_newpwd)
    EditText updatepwdNewpwd;
    @BindView(R.id.updatepwd_repwd)
    EditText updatepwdRepwd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_updatepwd;
    }

    @Override
    public void initView() {
        titleTitle.setText("修改密码");

    }

    @OnClick({R.id.title_back, R.id.updatepwd_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.updatepwd_btn:
                forgetPass();
                break;
        }
    }

    private void forgetPass(){
        if (ToosUtils.isTextEmpty(updatepwdOldpwd)){
            ToastUtil.show("旧密码不能为空！");
            return;
        }
        if (ToosUtils.isTextEmpty(updatepwdNewpwd)){
            ToastUtil.show("验证码不能为空！");
            return;
        }
        if (!ToosUtils.getTextContent(updatepwdNewpwd).equals(ToosUtils.getTextContent(updatepwdRepwd))){
            ToastUtil.show("两次输入的密码不一致！");
            return;
        }
        showDialog();
        Api.getInstance().upPassword(ToosUtils.getTextContent(updatepwdOldpwd),ToosUtils.getTextContent(updatepwdNewpwd), new HttpUtil.URLListenter<String>() {
            @Override
            public void onsucess(String str) throws Exception {
                dissDialog();
                ToastUtil.show("修改成功!");
                finish();
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }


}
