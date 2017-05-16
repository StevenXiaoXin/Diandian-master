package com.daqianjietong.diandian.base;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.daqianjietong.diandian.dialog.ProDialog;

import org.xutils.x;

import butterknife.ButterKnife;

/**
 * @author Mu
 * @date 2015-6-16
 * @description 基本Activity
 */
public abstract class BaseActivity extends AppCompatActivity {
	private static final int notifiId = 11;
	public static final String BATG = "BaseActivity";
	InputMethodManager imm;

	private ProDialog proDialog;

	//	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		super.onCreate(savedInstanceState);
		this.setContentView(this.getLayoutId());
		ButterKnife.bind(this);
		if(!isTaskRoot()){
			Intent intent = getIntent();
			String action = intent.getAction();
			if(intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)){
				finish();
				return;
			}
		}
		init(savedInstanceState);
		this.initView();

	}

	public void init(Bundle savedInstanceState){

	};

	public abstract int getLayoutId();

	public abstract void initView();


	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (imm != null && getCurrentFocus() != null
				&& getCurrentFocus().getWindowToken() != null) {
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}


	public void showDialog(){
		if (proDialog==null){
			proDialog=new ProDialog(this);
		}
		if (proDialog.isShowing()==false){
			proDialog.show();
		}
	}

	public void dissDialog(){
		if (proDialog!=null && proDialog.isShowing()){
			proDialog.dismiss();
		}
	}


}
