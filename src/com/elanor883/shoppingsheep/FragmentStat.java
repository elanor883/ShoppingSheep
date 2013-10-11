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


public class Fragment3 extends SherlockFragment {

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
		/*	if (MainActivity.fr3Imp == true) {
				MainActivity.fr3Imp = false;
				refreshCurrentFragment();
			}*/

			if (MainActivity.mymenu != null) {
				Menu menu = MainActivity.mymenu;
				//onPrepareOptionsMenu(mymenu);
				Log.d("mymenu fr1", "called");
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
				item12.setVisible(true);

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

			}
			
			Log.d("fr3", "visibility" + Fragment2.update);
			
			if(Fragment2.update == true)
			{
				Fragment2.update = false;
				Log.d("fr3", "visibility");
				groupByDay();
				
			}
			setBkg();
			
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}

			else {
				Log.d("fr1", "kva anyjat enek a szarnak");
			}
			

		}

		else {
			Log.d("fr1vis", "fos");
		}

	}

	@Override
	public void onStart() {
		super.onStart();
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
			Log.d("oncreate", "ujra meghivodot");
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
		}
		else if (group == 2) {
			groupByMonth();
		}

		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Toast.makeText(getBaseContext(), mListItems.get(position),
				// 1000).show();
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

				Log.d("detail1", "" + Fragment3.isDetailActive);
				showDetails(position);
			}

		});

		// Click event for single list row
		/*
		 * lv.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * public void onItemClick(AdapterView<?> parent, View view, int
		 * position, long id) {
		 * 
		 * } });
		 */

		if (savedInstanceState != null) {
			int currentTab = savedInstanceState.getInt("CurrentTab", pos);

			Log.d("current", "" + currentTab);
			if (isLandscape() && currentTab != -1) {
				selected = dateList.get(currentTab);
				Log.d("frag3", selected);
				view.setSelected(true);
				adapter.setSelectedIndex(currentTab);
				Log.d("itemclick", "" + selected + " " + group);
				showDetails(currentTab);
			}
			/* Set currently selected tab */

			/*
			 * boolean isDark = savedInstanceState.getBoolean("DarkBkg", true);
			 * if (isDark) { view.setBackgroundColor(Color.WHITE); } else {
			 * view.setBackgroundColor(Color.RED); }
			 */

		}

		/*
		 * else{
		 * 
		 * view.setBackgroundColor(Color.RED); }
		 */
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d("visszaallit", "" + pos);
		outState.putInt("CurrentTab", pos);
		outState.putString("Selected", selected);
		// outState.putBoolean("DarkBkg", darkbkg);
		Log.d("vissza", "vissza");
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

			Fragment4 aFrag = new Fragment4();
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

			Fragment4 aFrag = new Fragment4();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.detail32, aFrag);
			// ft.addToBackStack(null);
			ft.commit();

		}
		/*
		 * else {
		 * 
		 * Intent intent = new Intent(); intent.setClass(this,
		 * DetailsActivity.class); intent.putExtra("index", index);
		 * startActivity(intent); }
		 */

	}

	public void onResume() {
		super.onResume();

	}

	public void setPos(int ind) {
		// TODO Auto-generated method stub
		pos = ind;
	}

	/*
	 * 
	 * public void onPrepareOptionsMenu(Menu menu) {
	 * 
	 * /* if(){ menu.findItem(R.id.settings_btn).setVisible(true);
	 * 
	 * } else{
	 */
	// menu.findItem(R.id.settings_btn).setVisible(false);
	// }

	// }
	/*
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    // TODO Add your menu entries here
		inflater = getSherlockActivity().getSupportMenuInflater();
		inflater.inflate(R.menu.fr3_menu, menu);
	    super.onCreateOptionsMenu(menu, inflater);
	}
	*/

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

			Log.d("fr3b", "settings");
		//	settingsMenu();

			return false;

		case R.id.back_btn:

			Log.d("fragment3", "baaaack to the futureeee");
			refreshMainFragment();
			return true;

		case R.id.order_daily:
			groupByDay();
			group = 0;
			Log.d("groupbyday", "bullshit");
			if (!isLandscape()) {
				refreshMainFragment();
			}
			return true;
		case R.id.order_weekly:
			groupByWeek();
			Log.d("groupbywek", "bullshit");
			group = 1;
			if (!isLandscape()) {
				refreshMainFragment();
			}
			return true;
		case R.id.order_monthly:
			groupByMonth();
			Log.d("groupbymonth", "bullshit");
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
			/*
			 * Log.d("fr32222", "detail-settings"); FragmentTab3b2 aFrag = new
			 * FragmentTab3b2(); FragmentTransaction ft =
			 * getFragmentManager().beginTransaction();
			 * ft.replace(R.id.detail32, aFrag); // ft.addToBackStack(null);
			 * ft.commit();
			 */

		}

		else {
			Log.d("fr32222", "detail-settings");
			Fragment3 fr3 = new Fragment3();
			// ((ShopListViewAdapter)(FragmentTab3b.lv.getAdapter())).notifyDataSetChanged();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.detail32, fr3);

		}
	}

	public void settingsMenu() {
		
		
		if (MainActivity.dark_bkg == true) {
			
			MainActivity.dark_bkg = false;
			setBkg();
			//view.setBackgroundColor(Color.parseColor("#f1f1f2"));
			Log.d("fr3", "settings - change to white" + MainActivity.dark_bkg);

		} else {
			
			MainActivity.dark_bkg = true;
			setBkg();
			//view.setBackgroundColor(Color.BLACK);
			
			Log.d("fr3", "settings - change to black" + MainActivity.dark_bkg);

		}

		if (isDetailActive) {
			Log.d("fr32222", "detail-settings");
			Fragment4 aFrag = new Fragment4();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.detail32, aFrag);
			// ft.addToBackStack(null);
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
		Fragment3.pos = -1;
		Fragment3 fr3 = new Fragment3();
		// ((ShopListViewAdapter)(FragmentTab3b.lv.getAdapter())).notifyDataSetChanged();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.detail32, fr3);

		Fragment3.isDetailActive = false;
		// fr3.setPos(-1);
		Log.d("visszaallit", "" + Fragment3.pos);
		// ft.addToBackStack(null);
		ft.commit();

		Log.d("detail2", "" + Fragment3.isDetailActive);
	}

	public void groupByWeek() {
		DatabaseHandler db = new DatabaseHandler(getSherlockActivity());
		dateList = new ArrayList<String>();
		listitems = new ArrayList<HashMap<String, String>>();

		List<ShopList> shopitems = db.getSumCostPerWeek();

		for (ShopList cn : shopitems) {
			String log = "Category: " + cn.getTypeName() + " ,Price: "
					+ cn.getPrice();
			// Writing Contacts to log
			HashMap<String, String> map = new HashMap<String, String>();
			/*
			 * String date = cn.getDate(); int year =
			 * Integer.parseInt(date.substring(0, 4)); int month =
			 * Integer.parseInt(date.substring(5, 7)); int day =
			 * Integer.parseInt(date.substring(8, 10));
			 */int week = cn.getWeek();
			// Log.d("datum", "" + year + " " + month + " " + day);
			map.put(KEY_TITLE, "" + cn.getWeek() + ". week");
			// map.put(KEY_SUBTITLE, ""+cn.getWeek() + ". week");
			map.put(KEY_CORNER, "" + cn.getPrice() + " �");
			dateList.add("" + week);
			listitems.add(map);

			Log.d("Name: ", log);
		}
		ArrayList<String> cat = db.getCategories();
		for (String c : cat) {
			Log.d("cat", c);
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
			// Writing Contacts to log
			HashMap<String, String> map = new HashMap<String, String>();
			/*
			 * String date = cn.getDate(); int year =
			 * Integer.parseInt(date.substring(0, 4)); int month =
			 * Integer.parseInt(date.substring(5, 7)); int day =
			 * Integer.parseInt(date.substring(8, 10));
			 */int month = cn.getMonth();
			// Log.d("datum", "" + year + " " + month + " " + day);
			 String monthname = new DateFormatSymbols().getMonths()[month-1];
			map.put(KEY_TITLE, "" + monthname);
			// map.put(KEY_SUBTITLE, ""+cn.getWeek() + ". week");
			map.put(KEY_CORNER, "" + cn.getPrice() + " �");
			dateList.add("" + month);
			listitems.add(map);

			Log.d("Name: ", log);
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

			Log.d("datum", "" + year + " " + month + " " + day);
			map.put(KEY_TITLE, date);
			map.put(KEY_SUBTITLE, dayOfWeek(year, month, day));
			map.put(KEY_CORNER, "" + cn.getPrice() + " �");
			dateList.add(date);
			listitems.add(map);

			Log.d("Name: ", log);
		}
		ArrayList<String> cat = db.getCategories();
		for (String c : cat) {
			Log.d("cat", c);
		}

		db.close();

		adapter = new ShopListViewAdapter(getActivity(), listitems);
		lv.setAdapter(adapter);
	}
	
	public void setBkg()
	{
		if (MainActivity.dark_bkg == false && view != null) {
			// ((MainActivity) parent).activePage = 1;
			view.setBackgroundColor(Color.parseColor("#f1f1f2"));
		} else if (MainActivity.dark_bkg == true && view != null) {
			view.setBackgroundColor(Color.BLACK);
		}
	}
}
