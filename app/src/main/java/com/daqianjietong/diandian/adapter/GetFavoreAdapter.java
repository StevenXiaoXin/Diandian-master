package com.daqianjietong.diandian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.model.FavoreEntity;
import com.daqianjietong.diandian.utils.TimeUtils;

import java.sql.Time;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/7.
 */

public class GetFavoreAdapter extends BaseAdapter {

    private Context context;
    private List<FavoreEntity> entities;
    private OnDrawClickLister onDrawClickLister;

    public GetFavoreAdapter(Context context, List<FavoreEntity> entities) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.item_getfavore, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        FavoreEntity favoreEntity=entities.get(position);
        holder.itemGetfavoreTitle.setText(favoreEntity.c_remarks);
        holder.itemGetfavoreContent.setText("有效期："+TimeUtils.getTime(Long.valueOf(favoreEntity.c_endtime)));
        if (FavoreEntity.TYPE_NEW.equals(favoreEntity.c_type)){
            holder.itemGetfavoreImage.setImageResource(R.mipmap.favore_register);
        }else if (FavoreEntity.TYPE_FULLREDUCE.equals(favoreEntity.c_type)){
            holder.itemGetfavoreImage.setImageResource(R.mipmap.favore_fullreduce);
        }else if (FavoreEntity.TYPE_SALE.equals(favoreEntity.c_type)){
            holder.itemGetfavoreImage.setImageResource(R.mipmap.favore_sale);
        }else if (FavoreEntity.TYPE_COMMON.equals(favoreEntity.c_type)){
            holder.itemGetfavoreImage.setImageResource(R.mipmap.favore);
        }
        if ("1".equals(favoreEntity.c_status)){
            holder.itemGetfavoreBtn.setEnabled(false);
            holder.itemGetfavoreBtn.setClickable(false);
        }else{
            holder.itemGetfavoreBtn.setEnabled(true);
            holder.itemGetfavoreBtn.setClickable(true);
        }

        holder.itemGetfavoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDrawClickLister!=null){
                    onDrawClickLister.onClick(position);
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_getfavore_image)
        ImageView itemGetfavoreImage;
        @BindView(R.id.item_getfavore_title)
        TextView itemGetfavoreTitle;
        @BindView(R.id.item_getfavore_content)
        TextView itemGetfavoreContent;
        @BindView(R.id.item_getfavore_btn)
        TextView itemGetfavoreBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnDrawClickLister{
        void onClick(int position);
    }

    public void setOnDrawClickLister(OnDrawClickLister onDrawClickLister) {
        this.onDrawClickLister = onDrawClickLister;
    }
}
