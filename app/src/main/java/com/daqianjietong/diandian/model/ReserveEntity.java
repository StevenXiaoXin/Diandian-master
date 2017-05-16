package com.daqianjietong.diandian.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/14.
 */

public class ReserveEntity implements Serializable{
    public String reserveid;
    public String r_parknum;//'停车位编号'
    public String r_parkname;//停车场名称
    public String r_starttime;
    public String r_endtime;
    public String r_money;
}
