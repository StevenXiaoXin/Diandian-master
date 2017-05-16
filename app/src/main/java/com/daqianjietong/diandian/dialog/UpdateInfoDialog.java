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

public class UpdateInfoDialog extends Dialog implements View.OnClickListener {

    public static final int RET_OK = 40;
    @BindView(R.id.update_info_title)
    TextView updateInfoTitle;
    @BindView(R.id.update_info_edit)
    EditText updateInfoEdit;
    private Context context;
    private Handler handler;
    private int flag; //1代表修改昵称 2 代表修改车牌号


    public UpdateInfoDialog(Context context, Handler handler, int flag) {
        super(context, R.style.dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.handler = handler;
        this.context = context;
        this.flag = flag;
        setContentView(R.layout.dialog_update_info);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        show();
        initView();
    }

    private void initView() {
        if (flag==1){
            updateInfoTitle.setText("昵\u3000\u3000称");
            updateInfoEdit.setHint("输入您要编辑的昵称");
        }else{
            updateInfoTitle.setText("车\u2000牌\u2000号");
            updateInfoEdit.setHint("输入您的车牌号");
        }
    }

    @Override
    public void onClick(View v) {

    }

    @OnClick({R.id.update_info_cancel, R.id.update_info_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.update_info_cancel:
                dismiss();
                break;
            case R.id.update_info_ok:
                if (ToosUtils.isTextEmpty(updateInfoEdit)){
                    if (flag==1){
                        ToastUtil.show("昵称不能为空！");
                    }else{
                        ToastUtil.show("车牌号不能为空！");
                    }
                    return;
                }
                Message message=new Message();
                message.what=RET_OK;
                message.arg1=flag;
                message.obj=ToosUtils.getTextContent(updateInfoEdit);
                handler.sendMessage(message);
                dismiss();
                break;
        }
    }
}
