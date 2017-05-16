package com.daqianjietong.diandian.model;

/**
 * Created by Administrator on 2017/5/13.
 */

public class FavoreEntity {

    /**
     * c_id : 1
     * c_money : 100.00
     * c_type : 1
     * c_starttime : 1492766446
     * c_endtime : 1493481600
     * c_createtime : 1492766446
     * c_status : 2
     */

    public static final String TYPE_NEW="1";
    public static final String TYPE_FULLREDUCE="2";
    public static final String TYPE_SALE="3";
    public static final String TYPE_COMMON="4";

    public String c_id;
    public String c_money;
    public String c_type;//类型1新客户 2满减券 3 折扣券 4通用券
    public String c_starttime;
    public String c_remarks;
    public String c_endtime;
    public String c_createtime;
    public String c_status;//1已领取 2 未领取


}
