package com.elanor883.shoppingsheep;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class FragmentStat extends SherlockFragment {

	static final String KEY_TITLE = "title";
	static final String KEY_SUBTITLE = "sub";
	static final String KEY_CORNER = "corner";
	static String selected;
	ListView lv;
	static int pos;
	boolean darkbkg = true;
	LinearLayout l1, l2;
	MenuItem item9;
	MenuItem item6;
	MenuItem item7;
	MenuItem item8;

	LayoutInflater mInflater;
	ViewGroup mContainer;
	Bundle mSavedInstanceState;
	View view;
	SherlockFragmentActivity parent;
	ShopListViewAdapter adapter;
	FrameLayout fr32;
	static boolean isDetailActive = false;
	ArrayList<HashMap<String, String>> listitems;
	static int group = 0;
	List<String> dateList;
	Menu mymenu;

	@Override
	public SherlockFragmentActivity getSherlockActivity() {
		return super.getSherlockActivity();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		if (isVisibleToUser) {

			if (FragmentCategories.update == true) {
				FragmentCategories.update = false;
				groupByDay();

			}
			setBkg();

			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}

		}

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);

	}

	public boolean isLandscape() {
		return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragmenttab32, container, false);
		fr32 = (FrameLayout) view.findViewById(R.id.detail32);

		setBkg();
		setHasOptionsMenu(true);

		mInflater = inflater;
		mContainer = container;
		mSavedInstanceState = savedInstanceState;

		pos = -1;

		lv = (ListView) view.findViewById(R.id.listView1);
		l1 = (LinearLayout) view.findViewById(R.id.mainlay);
		l2 = (LinearLayout) view.findViewById(R.id.sublay);

		if (!isLandscape()) {

			LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(0,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			p1.weight = 1;

			l1.setLayoutParams(p1);

			LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(0,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			p2.weight = 0;
			l2.setLayoutParams(p2);
		}

		if (group == 0)

		{
			groupByDay();
		}

		else if (group == 1) {
			groupByWeek();
		} else if (group == 2) {
			groupByMonth();
		}

		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Log.d("frag3", "selected");
				selected = dateList.get(position);
				view.setSelected(true);
				pos = position;
				adapter.setSelectedIndex(position);

				if (isLandscape()) {
					isDetailActive = false;
				} else {
					item9.setVisible(true);
					item6.setVisible(false);
					item8.setVisible(false);
					item7.setVisible(false);
					isDetailActive = true;
				}

				showDetails(position);
			}

		});

		if (savedInstanceState != null) {
			int currentTab = savedInstanceState.getInt("CurrentTab", pos);

			if (isLandscape() && currentTab != -1) {
				selected = dateList.get(currentTab);
				view.setSelected(true);
				adapter.setSelectedIndex(currentTab);

				showDetails(currentTab);
			}

		}

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("CurrentTab", pos);
		outState.putString("Selected", selected);

		setUserVisibleHint(true);
	}

	public String dayOfWeek(int year, int month, int day) {
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		String weekday = null;
		int i = calendar.get(Calendar.DAY_OF_WEEK);

		if (i == 2) {
			weekday = "Monday";
		} else if (i == 3) {
			weekday = "Tuesday";
		} else if (i == 4) {
			weekday = "Wednesday";
		} else if (i == 5) {
			weekday = "Thursday";
		} else if (i == 6) {
			weekday = "Friday";
		} else if (i == 7) {
			weekday = "Saturday";
		} else if (i == 1) {
			weekday = "Sunday";
		}

		return weekday;

	}

	public void showDetails(int index) {

		if (isLandscape()) {

			LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(0,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			p1.weight = 1;

			l1.setLayoutParams(p1);

			LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(0,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			p2.weight = 2;
			l2.setLayoutParams(p2);

			FragmentDetail aFrag = new FragmentDetail();
			getFragmentManager().beginTransaction()
					.replace(R.id.detail32, aFrag).commit();

		} else {

			LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(0,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			p1.weight = 0;

			l1.setLayoutParams(p1);

			LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(0,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			p2.weight = 1;
			l2.setLayoutParams(p2);

			FragmentDetail aFrag = new FragmentDetail();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.detail32, aFrag);
			// ft.addToBackStack(null);
			ft.commit();

		}

	}

	public void setPos(int ind) {
		// TODO Auto-generated method stub
		pos = ind;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {

		mymenu = menu;
		MenuItem item = menu.findItem(R.id.imp_btn);
		MenuItem item2 = menu.findItem(R.id.exp_btn);
		MenuItem item3 = menu.findItem(R.id.last_10);
		MenuItem item4 = menu.findItem(R.id.last_20);
		MenuItem item5 = menu.findItem(R.id.all_items);
		item6 = menu.findItem(R.id.order_daily);
		item7 = menu.findItem(R.id.order_weekly);
		item8 = menu.findItem(R.id.order_monthly);
		item9 = menu.findItem(R.id.back_btn);
		MenuItem item10 = menu.findItem(R.id.settings_btn1);
		MenuItem item11 = menu.findItem(R.id.dummy);
		MenuItem item12 = menu.findItem(R.id.settings_btn3);

		item.setVisible(false);
		item2.setVisible(false);
		item3.setVisible(false);
		item4.setVisible(false);
		item5.setVisible(false);
		item10.setVisible(false);
		item11.setVisible(false);
		item12.setVisible(false);

		if (!isDetailActive) {

			item6.setVisible(true);
			item7.setVisible(true);
			item8.setVisible(true);
			item9.setVisible(false);
		}

		else {
			item6.setVisible(false);
			item7.setVisible(false);
			item8.setVisible(false);
			item9.setVisible(true);
		}

		if (isLandscape()) {
			item6.setVisible(true);
			item7.setVisible(true);
			item8.setVisible(true);
			item9.setVisible(false);
		}
		super.onPrepareOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		DatabaseHandler db;
		switch (item.getItemId()) {

		case R.id.settings_btn3:

			return false;

		case R.id.back_btn:

			refreshMainFragment();
			return true;

		case R.id.order_daily:
			groupByDay();
			group = 0;

			if (!isLandscape()) {
				refreshMainFragment();
			}
			return true;
		case R.id.order_weekly:
			groupByWeek();

			group = 1;
			if (!isLandscape()) {
				refreshMainFragment();
			}
			return true;
		case R.id.order_monthly:
			groupByMonth();

			group = 2;
			if (!isLandscape()) {
				refreshMainFragment();
			}
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void refreshCurrentFragment() {
		if (isDetailActive) {

		}

		else {
			FragmentStat fr3 = new FragmentStat();

			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.detail32, fr3);

		}
	}

	public void settingsMenu() {

		if (MainActivity.dark_bkg == true) {

			MainActivity.dark_bkg = false;
			setBkg();

		} else {

			MainActivity.dark_bkg = true;
			setBkg();

		}

		if (isDetailActive) {

			FragmentDetail aFrag = new FragmentDetail();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.detail32, aFrag);

			ft.commit();
		}
		adapter.notifyDataSetChanged();

	}

	public void importMenu() {
		DatabaseHandler db;
		db = new DatabaseHandler(getActivity());
		try {
			db.importDB("/sdcard/shoppingManager");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		MainActivity.imported = true;
		refreshCurrentFragment();
	}

	public void refreshMainFragment() {
		FragmentStat.pos = -1;
		FragmentStat fr3 = new FragmentStat();

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.detail32, fr3);

		FragmentStat.isDetailActive = false;

		ft.commit();

	}

	public void groupByWeek() {
		DatabaseHandler db = new DatabaseHandler(getSherlockActivity());
		dateList = new ArrayList<String>();
		listitems = new ArrayList<HashMap<String, String>>();

		List<ShopList> shopitems = db.getSumCostPerWeek();

		for (ShopList cn : shopitems) {
			String log = "Category: " + cn.getTypeName() + " ,Price: "
					+ cn.getPrice();

			HashMap<String, String> map = new HashMap<String, String>();
			int week = cn.getWeek();

			map.put(KEY_TITLE, "" + cn.getWeek() + ". week");

			map.put(KEY_CORNER, "" + cn.getPrice() + " ˆ");
			dateList.add("" + week);
			listitems.add(map);

		}

		db.close();

		adapter = new ShopListViewAdapter(getActivity(), listitems);
		lv.setAdapter(adapter);

	}

	public void groupByMonth() {
		DatabaseHandler db = new DatabaseHandler(getSherlockActivity());
		dateList = new ArrayList<String>();
		listitems = new ArrayList<HashMap<String, String>>();

		List<ShopList> shopitems = db.getSumCostPerMonth();

		for (ShopList cn : shopitems) {
			String log = "Category: " + cn.getTypeName() + " ,Price: "
					+ cn.getPrice();

			HashMap<String, String> map = new HashMap<String, String>();

			int month = cn.getMonth();

			String monthname = new DateFormatSymbols().getMonths()[month - 1];
			map.put(KEY_TITLE, "" + monthname);

			map.put(KEY_CORNER, "" + cn.getPrice() + " ˆ");
			dateList.add("" + month);
			listitems.add(map);

		}

		db.close();

		adapter = new ShopListViewAdapter(getActivity(), listitems);
		lv.setAdapter(adapter);

	}

	public void groupByDay() {
		dateList = new ArrayList<String>();

		DatabaseHandler db = new DatabaseHandler(getSherlockActivity());

		listitems = new ArrayList<HashMap<String, String>>();

		List<ShopList> shopitems = db.getSumCostPerDay();

		for (ShopList cn : shopitems) {
			String log = "Category: " + cn.getTypeName() + " ,Price: "
					+ cn.getPrice();
			// Writing Contacts to log
			HashMap<String, String> map = new HashMap<String, String>();
			String date = cn.getDate();
			int year = Integer.parseInt(date.substring(0, 4));
			int month = Integer.parseInt(date.substring(5, 7));
			int day = Integer.parseInt(date.substring(8, 10));

			map.put(KEY_TITLE, date);
			map.put(KEY_SUBTITLE, dayOfWeek(year, month, day));
			map.put(KEY_CORNER, "" + cn.getPrice() + " ˆ");
			dateList.add(date);
			listitems.add(map);

		}

		db.close();

		adapter = new ShopListViewAdapter(getActivity(), listitems);
		lv.setAdapter(adapter);
	}

	public void setBkg() {
		if (MainActivity.dark_bkg == false && view != null) {

			view.setBackgroundColor(Color.parseColor("#f1f1f2"));
		} else if (MainActivity.dark_bkg == true && view != null) {
			view.setBackgroundColor(Color.BLACK);
		}
	}
}