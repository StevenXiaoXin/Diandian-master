package com.daqianjietong.diandian.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.model.UserInfoBean;
import com.daqianjietong.diandian.utils.Api;
import com.daqianjietong.diandian.utils.HttpUtil;
import com.daqianjietong.diandian.utils.SpUtil;
import com.daqianjietong.diandian.utils.ToastUtil;
import com.daqianjietong.diandian.utils.ToosUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6.
 */

public class LoginActivty extends BaseActivity {


    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_register)
    TextView loginRegister;
    @BindView(R.id.login_forgetpwd)
    TextView loginForgetpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        loginRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
        loginForgetpwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
    }


    @OnClick({R.id.login_btn, R.id.login_register, R.id.login_forgetpwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                getLogin();
                break;
            case R.id.login_register:
                Intent intent=new Intent(LoginActivty.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_forgetpwd:
                Intent intent2=new Intent(LoginActivty.this,ForgetPwdActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private void getLogin(){
        if (ToosUtils.isTextEmpty(loginName)){
            ToastUtil.show("账号不能为空!");
            return;
        }
        if (ToosUtils.isTextEmpty(loginPassword)){
            ToastUtil.show("密码不能为空!");
            return;
        }
        showDialog();
        Api.getInstance().login(ToosUtils.getTextContent(loginName),ToosUtils.getTextContent(loginPassword), new HttpUtil.URLListenter<UserInfoBean>() {
            @Override
            public void onsucess(UserInfoBean userInfoBean) throws Exception {
                dissDialog();
                if (userInfoBean==null){
                    ToastUtil.show("登录失败！");
                }else{
                    SpUtil.saveUser(userInfoBean);
                    Intent intent=new Intent(LoginActivty.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }
}
