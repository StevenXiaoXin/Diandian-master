package com.daqianjietong.diandian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.daqianjietong.diandian.utils.StrHexStr;
import com.daqianjietong.diandian.utils.ToastUtil;
import com.daqianjietong.diandian.utils.ToosUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6.
 */

public class ForgetPwdActivity extends BaseActivity {


    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.forgetpwd_getcode)
    TextView forgetpwdGetcode;
    @BindView(R.id.forgetpwd_phone)
    EditText forgetpwdPhone;
    @BindView(R.id.forgetpwd_code)
    EditText forgetpwdCode;
    @BindView(R.id.forgetpwd_password)
    EditText forgetpwdPassword;
    @BindView(R.id.forgetpwd_repassword)
    EditText forgetpwdRepassword;

    private TimeCount time;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgetpwd;
    }

    @Override
    public void initView() {
        titleTitle.setText("忘记密码");
        time = new TimeCount(60000, 1000);
    }


    @OnClick({R.id.title_back, R.id.forgetpwd_getcode, R.id.forgetpwd_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.forgetpwd_getcode:
                getCode();
                break;
            case R.id.forgetpwd_btn:
                forgetPass();
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
            if (forgetpwdGetcode==null){
                return;
            }
            forgetpwdGetcode.setText("获取验证码");
            forgetpwdGetcode.setClickable(true);
            forgetpwdGetcode.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            if (forgetpwdGetcode==null){
                return;
            }
            forgetpwdGetcode.setClickable(false);
            forgetpwdGetcode.setEnabled(false);
            forgetpwdGetcode.setText(millisUntilFinished / 1000 + "S重新获取");
        }
    }

    private void getCode(){
        if (ToosUtils.isTextEmpty(forgetpwdPhone)){
            ToastUtil.show("手机号不能为空！");
            return;
        }
        showDialog();
        Api.getInstance().getCode(ToosUtils.getTextContent(forgetpwdPhone),"2", new HttpUtil.URLListenter<String>() {
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


    private void forgetPass(){
        if (ToosUtils.isTextEmpty(forgetpwdPhone)){
            ToastUtil.show("手机号不能为空！");
            return;
        }
        if (ToosUtils.isTextEmpty(forgetpwdCode)){
            ToastUtil.show("验证码不能为空！");
            return;
        }
        if (ToosUtils.isTextEmpty(forgetpwdPassword)){
            ToastUtil.show("密码不能为空！");
            return;
        }
        if (ToosUtils.isTextEmpty(forgetpwdRepassword)){
            ToastUtil.show("确认密码不能为空！");
            return;
        }
        if (!ToosUtils.getTextContent(forgetpwdPassword).equals(ToosUtils.getTextContent(forgetpwdRepassword))){
            ToastUtil.show("两次输入的密码不一致！");
            return;
        }
        showDialog();
        Api.getInstance().forgetPass(ToosUtils.getTextContent(forgetpwdPhone),ToosUtils.getTextContent(forgetpwdPassword),ToosUtils.getTextContent(forgetpwdCode), new HttpUtil.URLListenter<String>() {
            @Override
            public void onsucess(String str) throws Exception {
                dissDialog();
                ToastUtil.show("修改成功，请重新登录！");
                Intent intent=new Intent(ForgetPwdActivity.this,LoginActivty.class);
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
