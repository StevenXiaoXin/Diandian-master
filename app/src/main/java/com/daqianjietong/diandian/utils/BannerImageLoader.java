package com.daqianjietong.diandian.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.daqianjietong.diandian.R;
import com.youth.banner.loader.ImageLoader;

import org.xutils.x;

/**
 * Created by liuzhuang on 2017/5/1.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        x.image().bind(imageView,(String)path);
//        imageView.setBackgroundResource(R.mipmap.ic_launcher);
    }
}
