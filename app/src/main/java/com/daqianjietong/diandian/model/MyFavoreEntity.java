package com.daqianjietong.diandian.model;

/**
 * Created by Administrator on 2017/5/13.
 */

public class MyFavoreEntity {

    /**
     * uc_id : 4
     * uc_couponid : 1
     * uc_money : 100.00
     * uc_remarks : 满100减10元
     * uc_counum : N0428664
     * uc_starttime : 492766446
     * uc_endtime : 1493481600
     * uc_type : 1
     */

    public static final String TYPE_NEW="1";
    public static final String TYPE_FULLREDUCE="2";
    public static final String TYPE_SALE="3";
    public static final String TYPE_COMMON="4";

    public String uc_id;
    public String uc_couponid;//优惠券id
    public String uc_money;//金额
    public String uc_remarks;//描述
    public String uc_counum;//券码
    public String uc_starttime;
    public String uc_endtime;
    public String uc_type;//（1新客户 2充值 3 停车场 4消费比例）


}
