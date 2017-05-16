package com.daqianjietong.diandian.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Window;

import com.daqianjietong.diandian.R;


/**
 * 作者：Administrator on 2016/3/23 14:30
 * 描述：
 */
public class ProDialog extends Dialog {

    private Context context;

    public ProDialog(Context context) {
        super(context, R.style.progress_dialog);
        this.context=context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
//        setCancelable(true);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
//        show();
        initView();
    }

    private void initView() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK){
            dismiss();
        }
        return false;
    }

}
