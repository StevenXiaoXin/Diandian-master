package com.daqianjietong.diandian.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.activity.fragment.MyOrderFragment;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.model.TabEntity;
import com.daqianjietong.diandian.view.CustomViewPager;
import com.daqianjietong.diandian.view.ViewPagerScroller;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/6.
 */

public class MyOrderActivity extends BaseActivity {
    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.iv_personal_center)
    ImageView ivPersonalCenter;
    @BindView(R.id.myorder_tablayout)
    CommonTabLayout myorderTablayout;
    @BindView(R.id.myorder_vp)
    CustomViewPager myorderVp;

    private MyOrderFragment curOrderFragment;
    private MyOrderFragment chisOrderFragment;
    private MyOrdertFragmentPagerAdapter mPagerAdapter;

    private String[] mTitles = {"当前订单", "历史订单"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_myoder;
    }

    @Override
    public void initView() {
        titleTitle.setText("我的订单");
        curOrderFragment= MyOrderFragment.newInstance("cur");
        chisOrderFragment=MyOrderFragment.newInstance("his");
        mPagerAdapter = new MyOrdertFragmentPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(curOrderFragment,"cur");
        mPagerAdapter.addFragment(chisOrderFragment,"cur");

        //设置viwepager切换时 滑动时长
        ViewPagerScroller pagerScroller = new ViewPagerScroller(this);
        pagerScroller.setScrollDuration(1500);//设置时间，时间越长，速度越慢
        pagerScroller.initViewPagerScroll(myorderVp);
        myorderVp.setAdapter(mPagerAdapter);
        myorderVp.setOffscreenPageLimit(2);
        myorderVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                myorderTablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        myorderTablayout.setTabData(mTabEntities);
        myorderTablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                myorderVp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }


    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }


    private class MyOrdertFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments = new LinkedList<>();
        private List<String> mTitles = new LinkedList<>();

        public MyOrdertFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
