package com.daqianjietong.diandian.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/13.
 * "ps_id": "1",
 * "ps_parknum": "D00001",
 * "ps_address":'燕顺路纳丹堡左侧停车场',
 * "ps_status": "1"//状态 1未用 2已用 3已预约 4出租
 */

public class CarPortEntity implements Serializable{

    public static  final String STATUS_NOUSE="1";
    public static  final String STATUS_USEED="2";
    public static  final String STATUS_APPOINT="3";
    public static  final String STATUS_RENT="4";


    public String ps_id;
    public String ps_parknum;
    public String ps_address;
    public String ps_status;


}
