package com.daqianjietong.diandian.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.utils.ToastUtil;
import com.daqianjietong.diandian.utils.ToosUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6.
 */

public class CustomeDialog extends Dialog implements View.OnClickListener {

    public static final int RET_OK = 70;
    @BindView(R.id.dialog_custome_edit)
    TextView dialogCustomeoEdit;
    private Context context;
    private Handler handler;
    private int flag;
    private int position;
    String content;
    public CustomeDialog(Context context,String content, Handler handler,int flag,int position) {
        super(context, R.style.dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.handler = handler;
        this.context = context;
        this.content = content;
        this.flag = flag;
        this.position = position;
        setContentView(R.layout.dialog_custome);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        show();
        initView();
    }

    private void initView() {
        dialogCustomeoEdit.setText(content);
    }

    @Override
    public void onClick(View v) {

    }

    @OnClick({R.id.dialog_custome_ok, R.id.dialog_custome_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_custome_cancel:
                dismiss();
                break;
            case R.id.dialog_custome_ok:
                Message message=new Message();
                message.what=RET_OK;
                message.arg1=flag;
                message.arg2=position;
                handler.sendMessage(message);
                dismiss();
                break;
        }
    }
}
