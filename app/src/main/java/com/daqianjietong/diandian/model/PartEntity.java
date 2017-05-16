package com.daqianjietong.diandian.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/13.
 */

public class PartEntity implements Serializable {

    public String p_id;
    public String p_parkname;
    public String p_address;
    public String p_lat;
    public String p_lng;
    public String p_number;
    public double p_distance;
    public String p_balance_number;
    public int p_yh;//1 有优惠  2 无优惠


    public boolean hasFrav(){
        if (p_yh==1){
            return true;
        }else{
            return false;
        }
    }

}
