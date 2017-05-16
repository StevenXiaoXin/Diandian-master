package com.daqianjietong.diandian.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.dialog.CustomeDialog;
import com.daqianjietong.diandian.model.PayListEntity;
import com.daqianjietong.diandian.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/7.
 */

public class PayListAdapter extends BaseAdapter {

    private Context context;
    private List<PayListEntity> entities;
    private Handler handler;

    public PayListAdapter(Context context,List<PayListEntity> entities, Handler handler) {
        this.context = context;
        this.entities=entities;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.item_paylist, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        PayListEntity payListEntity=entities.get(position);
        holder.itemPaylistName.setText(payListEntity.r_parkname);
        holder.itemPlaylistTime.setText(TimeUtils.getShowTime3(payListEntity.r_starttime)+" 至 "+TimeUtils.getShowTime3(payListEntity.r_endtime));
        holder.itemPaylistPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(1);
            }
        });

        holder.itemPaylistCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomeDialog customeDialog=new CustomeDialog(context,"确定取消该订单？",handler,-100,position);
            }
        });

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.item_paylist_name)
        TextView itemPaylistName;
        @BindView(R.id.item_playlist_time)
        TextView itemPlaylistTime;
        @BindView(R.id.item_paylist_cancel)
        TextView itemPaylistCancel;
        @BindView(R.id.item_paylist_pay)
        TextView itemPaylistPay;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
