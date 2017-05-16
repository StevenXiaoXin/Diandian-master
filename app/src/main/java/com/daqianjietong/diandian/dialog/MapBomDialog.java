package com.daqianjietong.diandian.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.model.PartEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/14.
 */

public class MapBomDialog extends Dialog {

    @BindView(R.id.map_bom_title)
    TextView mapBomTitle;
    @BindView(R.id.map_bom_freenum)
    TextView mapBomFreenum;
    @BindView(R.id.map_bom_distance)
    TextView mapBomDistance;
    private View view = null;
    private Context context = null;
    int height;
    private Handler handler;
    private PartEntity partEntity;

    public MapBomDialog(@NonNull Context context, PartEntity partEntity, Handler handler) {
        super(context, R.style.dialog2);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.partEntity = partEntity;
        this.handler = handler;
        view = View.inflate(context, R.layout.map_bom, null);
        setContentView(view);
        ButterKnife.bind(this,view);
        mapBomTitle.setText(partEntity.p_parkname);
        mapBomFreenum.setText(partEntity.p_balance_number);
        mapBomDistance.setText(partEntity.p_distance+"km");

        init();

    }

    @OnClick(R.id.map_bom_gps)
    public void onViewClicked() {
        Message message=new Message();
        message.what=101;
        message.obj=partEntity;
        handler.sendMessage(message);
        dismiss();
    }

    private void init() {
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        // dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow
                .setBackgroundDrawableResource(R.drawable.background_dialog);
        height = lp.height;
        show();
        dialogAnimation(this, view, getWindowHeight(), height, false);
    }

    private int getWindowHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }

    private void dialogAnimation(final Dialog d, View v, int from, int to,
                                 boolean needDismiss) {

        Animation anim = new TranslateAnimation(0, 0, from, to);
        anim.setFillAfter(true);
        anim.setDuration(500);
        if (needDismiss) {
            anim.setAnimationListener(new Animation.AnimationListener() {

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    d.dismiss();
                }
            });

        }
        v.startAnimation(anim);
    }
}
