package com.daqianjietong.diandian.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.model.PayListEntity;
import com.daqianjietong.diandian.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/6.
 */

public class MyOrderAdapter extends BaseAdapter {

    private Context context;
    private String type;
    private List<PayListEntity> entities;
    private Handler handler;

    public MyOrderAdapter(Context context, List<PayListEntity> entities, String type, Handler handler) {
        this.context = context;
        this.entities=entities;
        this.type = type;
        this.handler=handler;
    }

    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public Object getItem(int position) {
        return entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.item_myorder, null);
            holder =new  ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        if ("cur".endsWith(type)){
            holder.itemMyorderContinue.setVisibility(View.VISIBLE);
        }else{
            holder.itemMyorderContinue.setVisibility(View.GONE);
        }
        PayListEntity payListEntity=entities.get(position);
        holder.itemMyorderName.setText(payListEntity.r_parkname);
        holder.itemMyorderTime.setText(TimeUtils.getShowTime3(payListEntity.r_starttime)+" 至 "+TimeUtils.getShowTime3(payListEntity.r_endtime));

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_myorder_name)
        TextView itemMyorderName;
        @BindView(R.id.item_myorder_time)
        TextView itemMyorderTime;
        @BindView(R.id.item_myorder_continue)
        TextView itemMyorderContinue;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
