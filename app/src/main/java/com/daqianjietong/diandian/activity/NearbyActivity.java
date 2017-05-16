package com.daqianjietong.diandian.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.model.NaviLatLng;
import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.dialog.MapBomDialog;
import com.daqianjietong.diandian.model.PartEntity;
import com.daqianjietong.diandian.utils.Api;
import com.daqianjietong.diandian.utils.HttpUtil;
import com.daqianjietong.diandian.utils.ToastUtil;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6.
 */

public class NearbyActivity extends BaseActivity {
    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.nearby_map)
    MapView nearbyMap;
    AMap aMap;
    Location myLocation;
    private boolean isFirst=true;

    private MapBomDialog mapBomDialog;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            PartEntity partEntity= (PartEntity) msg.obj;
            Intent intent=new Intent(NearbyActivity.this,GPSNaviAcyivity.class);
            intent.putExtra("part",partEntity);
            intent.putExtra("myNaviLatLng",new NaviLatLng(myLocation.getLatitude(),myLocation.getLongitude()));
            startActivity(intent);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_nearby;
    }

    @Override
    public void initView() {
        titleTitle.setText("附近车位");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nearbyMap.onCreate(savedInstanceState);// 此方法必须重写
        aMap = nearbyMap.getMap();
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (location!=null){
                    myLocation=location;
                    if (isFirst){
                        parkingIndex(1,"");
                    }
                    isFirst=false;
                    Log.e("---------",location.toString());
                }

            }
        });
        aMap.getUiSettings().setMyLocationButtonEnabled(true);

        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                PartEntity partEntity= (PartEntity) marker.getObject();
                if (mapBomDialog != null && mapBomDialog.isShowing()) {
                    mapBomDialog.dismiss();
                    mapBomDialog = null;
                }
                Log.e("---------",new Gson().toJson(partEntity)+"--------------");;
                aMap.animateCamera(CameraUpdateFactory.changeLatLng(new LatLng(Double.valueOf(partEntity.p_lat),Double.valueOf(partEntity.p_lng))));
                mapBomDialog = new MapBomDialog(NearbyActivity.this, partEntity, handler);
                Log.e("----------------",partEntity.p_address);
                return false;
            }
        });

    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nearbyMap.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        nearbyMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        nearbyMap.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        nearbyMap.onSaveInstanceState(outState);
    }

    private void parkingIndex(final int pageNo, String name){
        Api.getInstance().parkingIndex(pageNo,String.valueOf(myLocation.getLatitude()),String.valueOf(myLocation.getLongitude()),name,new HttpUtil.URLListenter<List<PartEntity>>() {
            @Override
            public void onsucess(List<PartEntity> entities) throws Exception {
                dissDialog();
                if (entities!=null){
                    addMark(entities);
                }
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }

    private void addMark(List<PartEntity> entities){
        for (int i=0;i<entities.size();i++){
            PartEntity partEntity=entities.get(i);
            LatLng latLng = new LatLng(Double.valueOf(partEntity.p_lat),Double.valueOf(partEntity.p_lng));
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLng);
            aMap.addMarker(markerOption).setObject(partEntity);
        }
    }

}
