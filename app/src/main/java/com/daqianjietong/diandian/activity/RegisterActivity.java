package com.daqianjietong.diandian.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6.
 */

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.register_getcode)
    TextView registerGetcode;
    @BindView(R.id.register_phone)
    EditText registerPhone;
    @BindView(R.id.register_code)
    EditText registerCode;
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.register_repassword)
    EditText registerRepassword;
    @BindView(R.id.register_checkbox)
    CheckBox registerCheckbox;
    @BindView(R.id.register_protocol)
    TextView registerProtocol;
    private TimeCount time;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        titleTitle.setText("注册账号");
        registerProtocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
        time = new TimeCount(60000, 1000);
    }


    @OnClick({R.id.title_back, R.id.register_getcode, R.id.register_protocol, R.id.register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.register_getcode:
                getCode();
                break;
            case R.id.register_protocol:
                Intent intent=new Intent(RegisterActivity.this,ProtocolActivity.class);
                startActivity(intent);
                break;
            case R.id.register_btn:
                userRegister();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        time.cancel();
        super.onDestroy();
    }


    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            if (registerGetcode==null){
                return;
            }
            registerGetcode.setText("获取验证码");
            registerGetcode.setClickable(true);
            registerGetcode.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            if (registerGetcode==null){
                return;
            }
            registerGetcode.setClickable(false);
            registerGetcode.setEnabled(false);
            registerGetcode.setText(millisUntilFinished / 1000 + "S重新获取");
        }
    }

    private void getCode(){
        if (ToosUtils.isTextEmpty(registerPhone)){
            ToastUtil.show("手机号不能为空！");
            return;
        }
        showDialog();
        Api.getInstance().getCode(ToosUtils.getTextContent(registerPhone),"1", new HttpUtil.URLListenter<String>() {
            @Override
            public void onsucess(String str) throws Exception {
                dissDialog();
                ToastUtil.show("验证码已发送至您的手机！");
                time.start();
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }


    private void userRegister(){
        if (ToosUtils.isTextEmpty(registerPhone)){
            ToastUtil.show("手机号不能为空！");
            return;
        }
        if (ToosUtils.isTextEmpty(registerCode)){
            ToastUtil.show("验证码不能为空！");
            return;
        }
        if (ToosUtils.isTextEmpty(registerPassword)){
            ToastUtil.show("密码不能为空！");
            return;
        }
        if (ToosUtils.isTextEmpty(registerRepassword)){
            ToastUtil.show("确认密码不能为空！");
            return;
        }
        if (!ToosUtils.getTextContent(registerPassword).equals(ToosUtils.getTextContent(registerRepassword))){
            ToastUtil.show("两次输入的密码不一致！");
            return;
        }
        showDialog();
        Api.getInstance().userRegister(ToosUtils.getTextContent(registerPhone),ToosUtils.getTextContent(registerPassword),ToosUtils.getTextContent(registerCode), new HttpUtil.URLListenter<UserInfoBean>() {
            @Override
            public void onsucess(UserInfoBean userInfoBean) throws Exception {
                dissDialog();
                ToastUtil.show("注册成功，请登录");
                Intent intent=new Intent(RegisterActivity.this,LoginActivty.class);
                startActivity(intent);
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
