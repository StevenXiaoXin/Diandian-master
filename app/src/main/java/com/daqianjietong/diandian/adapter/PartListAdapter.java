package com.daqianjietong.diandian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.model.PartEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/6.
 */

public class PartListAdapter extends BaseAdapter {

    private Context context;
    private List<PartEntity> entities;
    private OnGpsClickLister onGpsClickLister;

    public PartListAdapter(Context context,List<PartEntity> entities) {
        this.context = context;
        this.entities=entities;
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
            convertView = View.inflate(context, R.layout.item_partlist, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        PartEntity entity=entities.get(position);
        holder.itemPartlistName.setText(entity.p_parkname);
        holder.itemPartlistAddress.setText(entity.p_address);
        holder.itemPartlistNum.setText("空余车位："+entity.p_number);
        if (entity.hasFrav()){
            holder.itemPartlistFavore.setVisibility(View.VISIBLE);
        }else{
            holder.itemPartlistFavore.setVisibility(View.GONE);
        }
        holder.itemPartlistGsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGpsClickLister!=null){
                    onGpsClickLister.onItemClick(position);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_partlist_name)
        TextView itemPartlistName;
        @BindView(R.id.item_partlist_num)
        TextView itemPartlistNum;
        @BindView(R.id.item_partlist_address)
        TextView itemPartlistAddress;
        @BindView(R.id.item_partlist_favore)
        TextView itemPartlistFavore;
        @BindView(R.id.item_partlist_gsp)
        ImageView itemPartlistGsp;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnGpsClickLister{
        public void onItemClick(int position);
    }

    public void setOnGpsClickLister(OnGpsClickLister onGpsClickLister) {
        this.onGpsClickLister = onGpsClickLister;
    }
}
