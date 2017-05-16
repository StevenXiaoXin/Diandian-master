package com.daqianjietong.diandian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.dialog.CustomeDialog;
import com.daqianjietong.diandian.dialog.PhotoDialog;
import com.daqianjietong.diandian.dialog.UpdateInfoDialog;
import com.daqianjietong.diandian.model.PhotoEntity;
import com.daqianjietong.diandian.model.UserInfoBean;
import com.daqianjietong.diandian.model.UserNameEntity;
import com.daqianjietong.diandian.utils.Api;
import com.daqianjietong.diandian.utils.GlideImageLoader;
import com.daqianjietong.diandian.utils.HttpUtil;
import com.daqianjietong.diandian.utils.SpUtil;
import com.daqianjietong.diandian.utils.ToastUtil;
import com.daqianjietong.diandian.utils.ToosUtils;
import com.daqianjietong.diandian.view.CircleImageView;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6.
 */

public class PersonInfoActivity extends BaseActivity {
    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.iv_personal_center)
    ImageView ivPersonalCenter;
    @BindView(R.id.personinfo_icon)
    CircleImageView personinfoIcon;
    @BindView(R.id.personinfo_name)
    TextView personinfoName;
    @BindView(R.id.personinfo_phone)
    TextView personinfoPhone;
    @BindView(R.id.personinfo_car)
    TextView personinfoCar;

    private ImagePicker imagePicker;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 40:
                    int flag=msg.arg1;
                    if (flag==1){
                        upUsername((String) msg.obj);
                    }else{
                        addCar((String) msg.obj);
                    }
                    break;
                case CustomeDialog.RET_OK:
                    logout();
                    break;
                case PhotoDialog.PHOTO_CAMERA:
                    Intent intent2 = new Intent(PersonInfoActivity.this, ImageGridActivity.class);
                    intent2.putExtra("flag", 11);
                    startActivityForResult(intent2, 100);
                    break;
                case PhotoDialog.PHOTO_PHOTO:
                    Intent intent = new Intent(PersonInfoActivity.this, ImageGridActivity.class);
                    startActivityForResult(intent, 100);
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initView() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        Integer radius = Integer.valueOf(70);
        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(radius * 2);
        imagePicker.setFocusHeight(radius * 2);
        imagePicker.setOutPutX(Integer.valueOf(300));
        imagePicker.setOutPutY(Integer.valueOf(300));
        imagePicker.setShowCamera(false);
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(false);

        titleTitle.setText("个人信息");
        index();
        setInfo();
    }

    void setInfo(){
        Log.e("------------",SpUtil.getUsername()+"&&&&");
        x.image().bind(personinfoIcon,SpUtil.getPhoto());
        personinfoName.setText(SpUtil.getUsername());
        personinfoPhone.setText(SpUtil.getPhone());
        personinfoCar.setText(SpUtil.getCarnum());
    }

    @OnClick({R.id.title_back, R.id.personinfo_iconlin, R.id.personinfo_namelin, R.id.personinfo_phonelin, R.id.personinfo_carlin, R.id.personinfo_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.personinfo_iconlin:
                PhotoDialog photoDialog = new PhotoDialog(PersonInfoActivity.this, handler);
                break;
            case R.id.personinfo_namelin:
                UpdateInfoDialog updateInfoDialog=new UpdateInfoDialog(PersonInfoActivity.this,handler,1);
                break;
            case R.id.personinfo_phonelin:
                break;
            case R.id.personinfo_carlin:
                UpdateInfoDialog updateInfoDialog2=new UpdateInfoDialog(PersonInfoActivity.this,handler,2);
                break;
            case R.id.personinfo_logout:
                CustomeDialog customeDialog=new CustomeDialog(PersonInfoActivity.this,"确认退出？",handler,0,0);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    File file = new File(images.get(0).path);
                    if (file != null) {
                        setUserPhoto(file);

                    }
                }
            }
        }
    }


    private void index(){
        showDialog();
        Api.getInstance().index(SpUtil.getUid(),new HttpUtil.URLListenter<UserInfoBean>() {
            @Override
            public void onsucess(UserInfoBean userInfoBean) throws Exception {
                dissDialog();
                Log.e("----",new Gson().toJson(userInfoBean));
                SpUtil.saveUserName(userInfoBean.getU_username());
                SpUtil.savePhone(userInfoBean.getU_phone());
                SpUtil.savePhoto(userInfoBean.getU_photo());
                setInfo();
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }

    private void upUsername(final String name){
        showDialog();
        Api.getInstance().upUsername(SpUtil.getUid(),name,new HttpUtil.URLListenter<UserNameEntity>() {
            @Override
            public void onsucess(UserNameEntity userNameEntity) throws Exception {
                dissDialog();
                SpUtil.saveUserName(name);
                ToastUtil.show("恭喜您，修改昵称成功！");
                setInfo();
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }

    private void addCar(final String carNum){
        showDialog();
        Api.getInstance().addCar(SpUtil.getUid(),carNum,new HttpUtil.URLListenter<String>() {
            @Override
            public void onsucess(String str) throws Exception {
                dissDialog();
                ToastUtil.show("恭喜您，修改车牌号成功！");
                SpUtil.saveCarnum(carNum);
                setInfo();
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }

    private void setUserPhoto(final File file){
        showDialog();
        Api.getInstance().setUserPhoto(SpUtil.getUid(),file,new HttpUtil.URLListenter<PhotoEntity>() {
            @Override
            public void onsucess(PhotoEntity entity) throws Exception {
                dissDialog();
                ToastUtil.show("恭喜您，修改头像成功！");
                Log.e("----",new Gson().toJson(entity));
                SpUtil.savePhoto(file.getAbsolutePath());
                setInfo();
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }

    private void logout(){
        showDialog();
        Api.getInstance().logout(SpUtil.getUid(),new HttpUtil.URLListenter<String>() {
            @Override
            public void onsucess(String str) throws Exception {
                dissDialog();
                ToastUtil.show("退出登录！");
                ToosUtils.goReLogin(PersonInfoActivity.this);
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }


}
