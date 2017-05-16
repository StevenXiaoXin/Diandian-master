package com.daqianjietong.diandian.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.model.PictureEntity;
import com.daqianjietong.diandian.model.UserInfoBean;
import com.daqianjietong.diandian.utils.Api;
import com.daqianjietong.diandian.utils.BannerImageLoader;
import com.daqianjietong.diandian.utils.HttpUtil;
import com.daqianjietong.diandian.utils.SpUtil;
import com.daqianjietong.diandian.utils.TimeUtils;
import com.daqianjietong.diandian.utils.ToastUtil;
import com.daqianjietong.diandian.utils.ToosUtils;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImageDataSource;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.feezu.liuli.timeselector.TimeSelector;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
//import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.iv_personal_center)
    ImageView ivPersonalCenter;
    @BindView(R.id.banner)
    com.youth.banner.Banner banner;

    @BindView(R.id.rl_tab)
    RelativeLayout rlTab;
    @BindView(R.id.iv_arrows_up)
    ImageView ivArrowsUp;
    @BindView(R.id.iv_arrows_down)
    ImageView ivArrowsDown;
    @BindView(R.id.lv_main)
    ScrollView lvMain;


    Handler handler=new Handler();
    private List<String> imageUrl = new ArrayList<>();

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocation myMapLocation;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        titleBack.setVisibility(View.GONE);
        titleTitle.setText("首\u3000页");
        ivPersonalCenter.setVisibility(View.VISIBLE);
        getPictures();
        initBanner(imageUrl);
        initLocation();
        if (ToosUtils.isStringEmpty(SpUtil.getToken())){
            Intent intent=new Intent(this,LoginActivty.class);
            startActivity(intent);
            return;
        }


    }



   void  initLocation(){
       mLocationClient = new AMapLocationClient(getApplicationContext());
       mLocationClient.setLocationListener(mLocationListener);
       mLocationOption = new AMapLocationClientOption();
       mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
       mLocationOption.setOnceLocation(true);
       mLocationClient.setLocationOption(mLocationOption);
       mLocationClient.startLocation();
   }


    @OnClick({R.id.title_back, R.id.iv_personal_center, R.id.btn_order, R.id.btn_nearby, R.id.btn_rent, R.id.btn_card, R.id.btn_pay, R.id.iv_arrows_up, R.id.iv_arrows_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_personal_center:
                Intent intent=new Intent(MainActivity.this,PersonCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_order:
                if (myMapLocation==null){
                    ToastUtil.show("正在获取定位信息，请稍等!");
                    return;
                }
                Intent intent1=new Intent(MainActivity.this,PartListActivity.class);
                intent1.putExtra("pageFlag",1);
                intent1.putExtra("local",myMapLocation);
                startActivity(intent1);
                break;
            case R.id.btn_nearby:
                Intent intent2=new Intent(MainActivity.this,NearbyActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_rent:
                if (myMapLocation==null){
                    ToastUtil.show("正在获取定位信息，请稍等!");
                    return;
                }
                Intent intent5=new Intent(MainActivity.this,PartListActivity.class);
                intent5.putExtra("pageFlag",2);
                intent5.putExtra("local",myMapLocation);
                startActivity(intent5);
                break;
            case R.id.btn_card:
                Intent intent3=new Intent(MainActivity.this,GetFavoreActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_pay:
                Intent intent4=new Intent(MainActivity.this,PayListActivity.class);
                startActivity(intent4);
                break;
            case R.id.iv_arrows_up:
                rlTab.setVisibility(View.VISIBLE);
                ivArrowsUp.setVisibility(View.GONE);
                ivArrowsDown.setVisibility(View.VISIBLE);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lvMain.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                break;
            case R.id.iv_arrows_down:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lvMain.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                rlTab.setVisibility(View.GONE);
                ivArrowsDown.setVisibility(View.GONE);
                ivArrowsUp.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initBanner(List<String> imagesUrl){
        List<String> titles=new ArrayList<>();
        for (int i=0;i<imagesUrl.size();i++){
            titles.add("");
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new BannerImageLoader());
        //设置图片集合
        banner.setImages(imagesUrl);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient!=null){
            mLocationClient.onDestroy();
        }
    }

    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    Log.e("----",new Gson().toJson(aMapLocation.getAddress())+"---------------");
                    myMapLocation=aMapLocation;
                    mLocationClient.stopLocation();

                }else {
                    Log.e("AmapError","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };


    private void getPictures(){
        Api.getInstance().getPictures(new HttpUtil.URLListenter<List<PictureEntity>>() {
            @Override
            public void onsucess(List<PictureEntity> pictureEntities) throws Exception {
                if (pictureEntities!=null){
                    for (PictureEntity pictureEntity:pictureEntities){
                        imageUrl.add(pictureEntity.ad_picture);
                    }
                    initBanner(imageUrl);
                }
            }

            @Override
            public void onfaild(String error) {

            }
        });
    }

}
