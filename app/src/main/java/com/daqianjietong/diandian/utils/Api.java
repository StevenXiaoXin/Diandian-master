package com.daqianjietong.diandian.utils;

import android.app.Activity;

import com.daqianjietong.diandian.model.BaseHttpEntity;
import com.daqianjietong.diandian.model.CarPortEntity;
import com.daqianjietong.diandian.model.FavoreEntity;
import com.daqianjietong.diandian.model.MyFavoreEntity;
import com.daqianjietong.diandian.model.PartEntity;
import com.daqianjietong.diandian.model.PayListEntity;
import com.daqianjietong.diandian.model.PhotoEntity;
import com.daqianjietong.diandian.model.PictureEntity;
import com.daqianjietong.diandian.model.ReserveEntity;
import com.daqianjietong.diandian.model.UserInfoBean;
import com.daqianjietong.diandian.model.UserNameEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by liuzhuang on 2017/5/1.
 */

public class Api {
    /**
     * api访问方法（post/get）
     */

    public enum  API_METHOD{
        POST,GET
    }
    public  static  String HOST = "http://www.daqianjietong.com/dianapi.php";
    public  static  String AGREEMENT = "/Register/agreement";
    private static final String LOGIN="/Login/login";
    private static final String GETCODE="/Register/getCode";
    private static final String USERREGISTER="/Register/userRegister";
    private static final String FORGETPASS="/User/forgetPass";
    private static final String GETPICTURES="/User/getPictures";
    private static final String INDEX="/User/index";
    private static final String UPUSERNAME="/User/upUsername";
    private static final String ADDCAR="/User/addCar";
    private static final String SETUSERPHOTO="/User/setUserPhoto";
    private static final String LOGOUT="/Login/logout";
    private static final String UPPASSWORD="/User/upPassword";
    private static final String PARKINGINDEX="/Parking/index";
    private static final String PARKLIST="/Parking/parkList";
    private static final String PARKINGCOUPON="/User/parkingCoupon";
    private static final String RECEIVECOUPON="/User/receiveCoupon";
    private static final String MYCOUPON="/User/myCoupon";
    private static final String RESERVEPARK="/Parking/reservePark";
    private static final String PAYLIST="/Parking/payList";
    private static final String UNSETRESERVE="/Parking/unsetReserve";
    private static final String ORDERLIST="/User/orderList";

    /**
     * 是否debug调试（切换测试环境和生产环境）
     */
    private static final boolean Debug= true;


    private static  Api api;

    static {
        if(Debug){
            HOST = "http://test.daqianjietong.com/dianapi.php";
        }
    }

    public static Api getInstance(){
        if(api == null)
            api = new Api();

        return api;
    }

    /**
     * 示例  登陆接口调用；
     * @param username
     * @param password
     * @param listenter
     */
  public  void login(String username, String password, HttpUtil.URLListenter<UserInfoBean> listenter){
      Map<String,String> params = new HashMap<>();
      params.put("txt_phone",username);
      params.put("txt_password",password);
      HttpUtil httpUtil=new HttpUtil();
      httpUtil.doRequest(API_METHOD.GET,HOST+LOGIN,params,listenter,new TypeToken<UserInfoBean>(){}.getType(),false);
  }

    /**
     * 获取短信接口
     * @param phone
     * @param txt_type 1注册 2忘记密码 3 更换手机号
     * @param listenter
     */
    public  void getCode(String phone, String txt_type, HttpUtil.URLListenter<String> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_phone",phone);
        params.put("txt_type",txt_type);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+GETCODE,params,listenter,new TypeToken<String>(){}.getType(),false);
    }

    /**
     * 注册接口
     * @param phone
     * @param password
     * @param code
     * @param listenter
     */
    public  void userRegister(String phone, String password,String code, HttpUtil.URLListenter<UserInfoBean> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_phone",phone);
        params.put("txt_password",password);
        params.put("txt_code",code);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+USERREGISTER,params,listenter,new TypeToken<UserInfoBean>(){}.getType(),false);
    }

    /**
     * 忘记密码接口
     * @param phone
     * @param password
     * @param code
     * @param listenter
     */
    public  void forgetPass(String phone, String password,String code, HttpUtil.URLListenter<String> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_phone",phone);
        params.put("new_pass",password);
        params.put("txt_code",code);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+FORGETPASS,params,listenter,new TypeToken<String>(){}.getType(),false);
    }
    /**
     * 获取banner图接口
     * @param listenter
     */
    public  void getPictures(HttpUtil.URLListenter<List<PictureEntity>> listenter){
        Map<String,String> params = new HashMap<>();
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+GETPICTURES,params,listenter,new TypeToken<List<PictureEntity>>(){}.getType(),true);
    }

    /**
     * 个人信息接口
     * @param listenter
     */
    public  void index(String txt_userid,HttpUtil.URLListenter<UserInfoBean> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_userid",txt_userid);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+INDEX,params,listenter,new TypeToken<UserInfoBean>(){}.getType(),true);
    }

    /**
     * 修改用户昵称接口
     * @param listenter
     */
    public  void upUsername(String txt_userid,String txt_username,HttpUtil.URLListenter<UserNameEntity> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_userid",txt_userid);
        params.put("txt_username",txt_username);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+UPUSERNAME,params,listenter,new TypeToken<UserNameEntity>(){}.getType(),true);
    }

    /**
     * 用户绑定/修改车辆接口
     * @param listenter
     */
    public  void addCar(String txt_userid,String txt_carnum,HttpUtil.URLListenter<String> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_userid",txt_userid);
        params.put("txt_carnum",txt_carnum);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+ADDCAR,params,listenter,new TypeToken<String>(){}.getType(),true);
    }

    /**
     * 设置用户头像接口
     * @param listenter
     */
    public  void setUserPhoto(String txt_userid, File file,HttpUtil.URLListenter<PhotoEntity> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_userid",txt_userid);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+SETUSERPHOTO,params,listenter,new TypeToken<PhotoEntity>(){}.getType(),true,file);
    }

    /**
     * 退出登录接口
     * @param listenter
     */
    public  void logout(String txt_userid, HttpUtil.URLListenter<String> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_userid",txt_userid);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+LOGOUT,params,listenter,new TypeToken<String>(){}.getType(),true);
    }

    /**
     * 修改用户密码接口
     * @param oldPwd
     * @param newPwd
     * @param listenter
     */
    public  void upPassword(String oldPwd, String newPwd, HttpUtil.URLListenter<String> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_userid",SpUtil.getUid());
        params.put("old_pass",oldPwd);
        params.put("new_pass",newPwd);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+UPPASSWORD,params,listenter,new TypeToken<String>(){}.getType(),true);
    }

    /**
     * 停车场列表接口
     * @param p
     * @param txt_lat
     * @param txt_lng
     * @param txt_parkname
     * @param listenter
     */
    public  void parkingIndex(int p, String txt_lat, String txt_lng,String txt_parkname,HttpUtil.URLListenter<List<PartEntity>> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("p", String.valueOf(p));
        params.put("txt_lat",txt_lat);
        params.put("txt_lng",txt_lng);
        params.put("txt_parkname",txt_parkname);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+PARKINGINDEX,params,listenter,new TypeToken<List<PartEntity>>(){}.getType(),true);
    }


    /**
     * 停车位列表接口
     * @param p
     * @param txt_parkid
     * @param txt_parknum
     * @param listenter
     */
    public  void parklist(int p, String txt_parkid,String txt_parknum,HttpUtil.URLListenter<List<CarPortEntity>> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("p", String.valueOf(p));
        params.put("txt_parkid",txt_parkid);
        params.put("txt_parknum",txt_parknum);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+PARKLIST,params,listenter,new TypeToken<List<CarPortEntity>>(){}.getType(),true);
    }

    /**
     * 获取全部优惠券接口
     * @param txt_userid
     * @param listenter
     */
    public  void parkingCoupon(String txt_userid,HttpUtil.URLListenter<List<FavoreEntity>> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_userid",txt_userid);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+PARKINGCOUPON,params,listenter,new TypeToken<List<FavoreEntity>>(){}.getType(),true);
    }

    /**
     * 获取全部优惠券接口
     * @param txt_userid
     * @param txt_couponid
     * @param txt_type
     * @param listenter
     */
    public  void receiveCoupon(String txt_userid,String txt_couponid,String txt_type,HttpUtil.URLListenter<String> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_userid",txt_userid);
        params.put("txt_couponid",txt_couponid);
        params.put("txt_type",txt_type);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+RECEIVECOUPON,params,listenter,new TypeToken<String>(){}.getType(),true);
    }

    /**
     * 已领取的优惠券接口
     * @param txt_userid
     * @param listenter
     */
    public  void myCoupon(String txt_userid,HttpUtil.URLListenter<List<MyFavoreEntity>> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_userid",txt_userid);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+MYCOUPON,params,listenter,new TypeToken<List<MyFavoreEntity>>(){}.getType(),true);
    }
    /**
     * 预约、租车提交订单接口
     * @param txt_userid
     * @param txt_parkid
     * @param start_time
     * @param end_time
     * @param txt_status
     * @param listenter
     */
    public  void reservePark(String txt_userid,String txt_parkid,String start_time,String end_time,String txt_status,HttpUtil.URLListenter<ReserveEntity> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_userid",txt_userid);
        params.put("txt_parkid",txt_parkid);
        params.put("start_time",start_time);
        params.put("end_time",end_time);
        params.put("txt_status",txt_status);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+RESERVEPARK,params,listenter,new TypeToken<ReserveEntity>(){}.getType(),true);
    }

    /**
     * 未支付订单接口
     * @param txt_userid
     * @param listenter
     */
    public  void paylist(String txt_userid,HttpUtil.URLListenter<List<PayListEntity>> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_userid",txt_userid);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+PAYLIST,params,listenter,new TypeToken<List<PayListEntity>>(){}.getType(),true);
    }

    /**
     * 未支付订单接口
     * @param txt_reserveid
     * @param listenter
     */
    public  void unsetReserve(String txt_reserveid,HttpUtil.URLListenter<String> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("txt_reserveid",txt_reserveid);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+UNSETRESERVE,params,listenter,new TypeToken<String>(){}.getType(),true);
    }

    /**
     * 停车场列表接口
     * @param p
     * @param txt_userid
     * @param txt_status
     * @param listenter
     */
    public  void orderList(int p, String txt_userid, String txt_status,HttpUtil.URLListenter<List<PayListEntity>> listenter){
        Map<String,String> params = new HashMap<>();
        params.put("p", String.valueOf(p));
        params.put("txt_userid",txt_userid);
        params.put("txt_status",txt_status);
        HttpUtil httpUtil=new HttpUtil();
        httpUtil.doRequest(API_METHOD.POST,HOST+ORDERLIST,params,listenter,new TypeToken<List<PayListEntity>>(){}.getType(),true);
    }
















    /**
     * 示例1  测试百度接口调用；（默认返回String类型的数据）
     * @param listenter  回调监听传入需要解析数据类型
     */
    public  void test( HttpUtil.URLListenter<String> listenter){
        /**
         * setMethod()设置post get方法
         * setParams(Map params)设置请求参数
         * setUrl(string url)设置请求url地址
         * setClassType(Class<\?> clazz)设置解析类实体类型 传如数据如 UserInfoBean.class;
         * setTypeToken(new TypeToken<T></>(){}.gettype())
         * 传入解析的数据类型，在未调用setClassType方法时起作用（针对列表数据类型解析）
         * 用法：将方法中T替换成想要解析的类或者List<T></>传入需要解析类
         * seturlLisenter()设置回掉监听.
         *
         */

        HttpUtil httpUtil = new HttpUtil<String>();
        httpUtil.setUrl(HOST).setMethod(API_METHOD.GET).seturllisenter(listenter).start();
    }
    /**
     * 示例2  测试百度接口调用；（设置返回解析后的list数据）
     * @param listenter  回调监听传入需要解析数据类型
     */
    public  void testList(HttpUtil.URLListenter<ArrayList<String>>listenter){
        /**
         * setMethod()设置post get方法
         * setParams(Map params)设置请求参数
         * setUrl(string url)设置请求url地址
         * setClassType(Class<\?> clazz)设置解析类实体类型 传如数据如 UserInfoBean.class;
         * setTypeToken(new TypeToken<T></>(){}.gettype())
         * 传入解析的数据类型，在未调用setClassType方法时起作用（针对列表数据类型解析）
         * 用法：将方法中T替换成想要解析的类或者List<T></>传入需要解析类
         * seturlLisenter()设置回掉监听.
         * 获取list数据时示例如下：
         */
        HttpUtil httpUtil = new HttpUtil<String>();
        httpUtil.setUrl(HOST).setMethod(API_METHOD.GET).setTypetoken(new TypeToken<ArrayList<UserInfoBean>>(){}.getType()).seturllisenter(listenter).start();
    }

}
