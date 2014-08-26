package kh.spaceclub.spaceballoon.activity;

import kh.spaceclub.spaceballoon.R;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public abstract class AbstractBaseActivity  extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	protected DrawerLayout getDrawerLayout() {
		if (mDrawerLayout != null)
			return mDrawerLayout;
		mDrawerLayout = getDrawerLayoutCore();
		return mDrawerLayout;
	}
	protected abstract DrawerLayout getDrawerLayoutCore();
	
	
	private ListView mDrawerList;
	protected ListView getDrawerList() {
		if (mDrawerList != null)
			return mDrawerList;
		mDrawerList = getDrawerListCore();
		return mDrawerList;
	}
	protected abstract ListView getDrawerListCore();
	
	private ActionBarDrawerToggle mDrawerToggle;
	protected ActionBarDrawerToggle getDrawerToggle() {
		if (mDrawerToggle != null)
			return mDrawerToggle;
		mDrawerToggle = getDrawerToggleCore();
		return mDrawerToggle;
	}
	
	protected ActionBarDrawerToggle getDrawerToggleCore() {
		return new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.open, R.string.close) {
			
			@Override
			public void onDrawerClosed(View drawerView) {
			}

			@Override
			public void onDrawerOpened(View drawerView) {
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
			}

			@Override
			public void onDrawerStateChanged(int newState) {
			}
		};
	}
	
	protected void initActionBar() {
		ActionBar ab = getSupportActionBar();

		// アプリアイコンのクリックを有効化
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		
		// ナビゲーションドロワーの一覧を生成
		String[] list = { "Top", "Take Photo", "Display Photo" };
		
		getDrawerLayout().setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		// ナビゲーションドロワーのリスナー設定
		getDrawerLayout().setDrawerListener(getDrawerToggle());

		// アダプター設定
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		getDrawerList().setAdapter(adapter);
		
		// クリック時の挙動を設定
		getDrawerList().setOnItemClickListener(getOnListItemClickListener());
	}
	
	private AdapterView.OnItemClickListener mOnItemClickListener;
	private AdapterView.OnItemClickListener getOnListItemClickListener() {
		if (mOnItemClickListener != null)
			return mOnItemClickListener;
		mOnItemClickListener = getOnListItemClickListenerCore();
		return mOnItemClickListener;
	}
	protected AdapterView.OnItemClickListener getOnListItemClickListenerCore() {
		return new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				// DrawerLayoutを閉じる
				mDrawerLayout.closeDrawer(mDrawerList);
				Class<?> clas;
				switch (i) {
					case 0:
					default:
						clas = MainActivity.class;
						break;
					case 1:
						clas = CameraActivity.class;
						break;
					case 2:
						clas = CameraActivity.class;
						break;
				}
				Intent intent=new Intent(getApplicationContext(), clas);
		        startActivity(intent);
			}
		};
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// アニメーションを実行させる
		super.onPostCreate(savedInstanceState);
		// DrawerToggleの状態を同期する
		getDrawerToggle().syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// アニメーションを実行させる
		super.onConfigurationChanged(newConfig);
		// DrawerToggleの状態を同期する
		getDrawerToggle().onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// アプリアイコンのタップ時にナビゲーションドロワーのオープン・クローズの処理
		if (getDrawerToggle().onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
