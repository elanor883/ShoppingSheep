package com.elanor883.shoppingsheep;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.view.GravityCompat;

public class MainActivity extends SherlockFragmentActivity {

	// Declare Variables
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	MenuListAdapter mMenuAdapter;
	String[] title;
	String[] subtitle;
	int[] icon;
	Fragment fragment1 = new FragmentShopping();
	Fragment fragment2 = new FragmentCategories();
	Fragment fragment3 = new FragmentStat();
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	static boolean dark_bkg = false;
	static boolean imported = false;
	int activePage = 1;
	ShopListViewAdapter mAdapterList;
	static boolean fr1Imp = false;
	static boolean fr2Imp = false;
	static boolean fr3Imp = false;
	Bundle mSavedInstanceState;
	static Menu mymenu;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		// Get the view from drawer_main.xml
		setContentView(R.layout.drawer_main);

		if (bundle != null) {
			dark_bkg = bundle.getBoolean("darkBkg");
		}

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		dark_bkg = sharedPreferences.getBoolean("is_dark_bkg",
				false);

		// Get the Title
		mTitle = mDrawerTitle = getTitle();

		// Generate title
		title = new String[] { "Shop", "Stats", "Categories" };


		// Generate icon
		icon = new int[] { R.drawable.side_shopping, R.drawable.side_stat,
				R.drawable.side_categ };

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.listview_drawer);

		mMenuAdapter = new MenuListAdapter(MainActivity.this, title, icon);

		mDrawerList.setAdapter(mMenuAdapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// Enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_HOME);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.sheep_logo, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				getSupportActionBar().setTitle(mDrawerTitle);

				super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (bundle == null) {
			selectItem(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putBoolean("darkBkg", dark_bkg);

	}

	public void savePreferences(String key, boolean value) {

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		Editor editor = sharedPreferences.edit();

		editor.putBoolean(key, value);

		editor.commit();

	}

	@Override
	public void onPause() {
		super.onPause();
		savePreferences("is_dark_bkg", dark_bkg);

	}

	@Override
	public void onStop() {
		super.onStop();
		savePreferences("is_dark_bkg", dark_bkg);
		Log.d("stop", "bigyo"+dark_bkg);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		dark_bkg = savedInstanceState.getBoolean("darkBkg");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}

		return super.onOptionsItemSelected(item);
	}


	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		switch (position) {
		case 0:
			ft.replace(R.id.content_frame, fragment1);
			break;
		case 1:
			ft.replace(R.id.content_frame, fragment3);
			break;
		case 2:
			ft.replace(R.id.content_frame, fragment2);
			break;
		}
		ft.commit();
		mDrawerList.setItemChecked(position, true);


		setTitle(title[position]);
		// Close drawer
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}
}