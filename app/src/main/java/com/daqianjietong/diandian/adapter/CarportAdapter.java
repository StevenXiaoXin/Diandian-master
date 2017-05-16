package com.daqianjietong.diandian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.model.CarPortEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/6.
 */

public class CarportAdapter extends BaseAdapter {

    private Context context;
    private List<CarPortEntity> entities;
    public OnAppointClickLister onAppointClickLister;
    private int pageFlag=1;//1代表约车 2代表租车

    public CarportAdapter(Context context, List<CarPortEntity> entities,int pageFlag) {
        this.context = context;
        this.entities = entities;
        this.pageFlag=pageFlag;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.item_carport, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        CarPortEntity carPortEntity=entities.get(position);
        holder.itemCarportName.setText(carPortEntity.ps_parknum);
        holder.itemCarportAddress.setText(carPortEntity.ps_address);
        if (pageFlag==1){
            holder.itemCarportAppoint.setText("立即预约");
        }else{
            holder.itemCarportAppoint.setText("立即租赁");
        }
        if (CarPortEntity.STATUS_NOUSE.equals(carPortEntity.ps_status)){
            holder.itemCarportAppoint.setEnabled(true);
            holder.itemCarportStatus.setText("车位已空");
        }else if(CarPortEntity.STATUS_USEED.equals(carPortEntity.ps_status)){
            holder.itemCarportAppoint.setEnabled(false);
            holder.itemCarportStatus.setText("车位已占");
        }else if(CarPortEntity.STATUS_APPOINT.equals(carPortEntity.ps_status)){
            holder.itemCarportAppoint.setEnabled(false);
            holder.itemCarportStatus.setText("车位已预约");
        }else if(CarPortEntity.STATUS_RENT.equals(carPortEntity.ps_status)){
            holder.itemCarportAppoint.setEnabled(false);
            holder.itemCarportStatus.setText("车位已租");
        }else{
            holder.itemCarportAppoint.setEnabled(false);
            holder.itemCarportStatus.setText("车位已占");
        }


        holder.itemCarportAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAppointClickLister!=null){
                    onAppointClickLister.onClick(position);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_carport_name)
        TextView itemCarportName;
        @BindView(R.id.item_carport_address)
        TextView itemCarportAddress;
        @BindView(R.id.item_carport_status)
        TextView itemCarportStatus;
        @BindView(R.id.item_carport_appoint)
        TextView itemCarportAppoint;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }




   public interface OnAppointClickLister{
         void onClick(int position);
    }

    public void setOnAppointClickLister(OnAppointClickLister onAppointClickLister){
        this.onAppointClickLister=onAppointClickLister;
    }

}

