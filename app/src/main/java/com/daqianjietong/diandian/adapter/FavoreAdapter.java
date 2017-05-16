package com.daqianjietong.diandian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.model.FavoreEntity;
import com.daqianjietong.diandian.model.MyFavoreEntity;
import com.daqianjietong.diandian.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/6.
 */

public class FavoreAdapter extends BaseAdapter {

    private Context context;
    private List<MyFavoreEntity> entities;

    public FavoreAdapter(Context context, List<MyFavoreEntity> entities) {
        this.context = context;
        this.entities = entities;
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
            convertView = View.inflate(context, R.layout.item_favore, null);
            holder =new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        MyFavoreEntity favoreEntity=entities.get(position);
        holder.itemFavoreTitle.setText(favoreEntity.uc_remarks);
        holder.itemFavoreContent.setText(favoreEntity.uc_counum+"\u3000有效期："+ TimeUtils.getTime(Long.valueOf(favoreEntity.uc_endtime)));
        if (MyFavoreEntity.TYPE_NEW.equals(favoreEntity.uc_type)){
            holder.itemFavoreImage.setImageResource(R.mipmap.favore_register);
        }else if (MyFavoreEntity.TYPE_FULLREDUCE.equals(favoreEntity.uc_type)){
            holder.itemFavoreImage.setImageResource(R.mipmap.favore_fullreduce);
        }else if (MyFavoreEntity.TYPE_SALE.equals(favoreEntity.uc_type)){
            holder.itemFavoreImage.setImageResource(R.mipmap.favore_sale);
        }else if (MyFavoreEntity.TYPE_COMMON.equals(favoreEntity.uc_type)){
            holder.itemFavoreImage.setImageResource(R.mipmap.favore);
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.item_favore_image)
        ImageView itemFavoreImage;
        @BindView(R.id.item_favore_title)
        TextView itemFavoreTitle;
        @BindView(R.id.item_favore_content)
        TextView itemFavoreContent;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
