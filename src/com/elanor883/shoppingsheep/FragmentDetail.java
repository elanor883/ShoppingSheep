package com.elanor883.shoppingsheep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class FragmentDetail extends SherlockFragment {

	static final String KEY_TITLE = "title";
	static final String KEY_CORNER = "corner";
	static final String KEY_IMG = "img";
	LabelCostListAdapter adapterList;

	List<ShopList> shoplist2;
	List<Pair<String, Integer>> monthlyCost;

	ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String>>();
	Button dd;
	int year;
	int month;
	int day;
	String cat;
	String date;
	int price;
	View view;
	static ListView lv;
	MenuItem item9;
	MenuItem item6;
	MenuItem item7;
	MenuItem item8;

	@Override
	public SherlockFragmentActivity getSherlockActivity() {
		return super.getSherlockActivity();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		if (isVisibleToUser) {

			setBkg();

			if (adapterList != null) {
				adapterList.notifyDataSetChanged();
			}

		}

	}

	private void setBkg() {
		// TODO Auto-generated method stub
		if (MainActivity.dark_bkg == false && view != null) {

			view.setBackgroundColor(Color.parseColor("#f1f1f2"));
		} else if (MainActivity.dark_bkg == true && view != null) {
			view.setBackgroundColor(Color.BLACK);
		}
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragmenttab32port, container, false);

		lv = (ListView) view.findViewById(R.id.listitem_lv_frag32);
		setHasOptionsMenu(false);

		setBkg();

		if (FragmentStat.group == 0) {
			groupByDayResult();
		} else if (FragmentStat.group == 1) {
			groupByWeekResult();
		}

		else if (FragmentStat.group == 2) {
			groupByMonthResult();
		}

		if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {

			lv.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					refreshMainFragment();

				}

			});
		}
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		setUserVisibleHint(true);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {

		super.onPrepareOptionsMenu(menu);
	}

	public void refreshMainFragment() {
		FragmentStat.pos = -1;
		FragmentStat fr3 = new FragmentStat();

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.detail32, fr3);

		FragmentStat.isDetailActive = false;
		ft.commit();

	}

	public void groupByDayResult() {
		DatabaseHandler db = new DatabaseHandler(getSherlockActivity());
		shoplist2 = db.getCostPerDayPerType();
		monthlyCost = new ArrayList<Pair<String, Integer>>();

		for (ShopList item : shoplist2) {
			String date = item.getDate();
			String type = item.getTypeName();
			if (date.equals(FragmentStat.selected)) {
				int price = item.getPrice();

				monthlyCost.add(new Pair<String, Integer>(type, price));
			}
		}

		adapterList = new LabelCostListAdapter(getActivity(), itemList);
		lv.setAdapter(adapterList);

		for (Pair<String, Integer> current : monthlyCost) {
			HashMap<String, String> map = new HashMap<String, String>();

			if (current.second != 0) {
				map.put(KEY_TITLE, current.first);

				map.put(KEY_CORNER, "" + current.second + " ˆ");
				map.put(KEY_IMG, db.getResId(current.first));
				itemList.add(map);
			}
		}
		// lv.setAdapter(adapter);
		db.close();
		adapterList.notifyDataSetChanged();
	}

	public void groupByWeekResult() {
		DatabaseHandler db = new DatabaseHandler(getSherlockActivity());
		shoplist2 = db.getCostPerWeekPerType();
		monthlyCost = new ArrayList<Pair<String, Integer>>();

		for (ShopList item : shoplist2) {
			String week = "" + item.getWeek();
			String type = item.getTypeName();
			if (week.equals(FragmentStat.selected)) {
				int price = item.getPrice();

				monthlyCost.add(new Pair<String, Integer>(type, price));
			}
		}

		adapterList = new LabelCostListAdapter(getActivity(), itemList);
		lv.setAdapter(adapterList);

		for (Pair<String, Integer> current : monthlyCost) {
			HashMap<String, String> map = new HashMap<String, String>();
			// color =
			if (current.second != 0) {
				map.put(KEY_TITLE, current.first);

				map.put(KEY_CORNER, "" + current.second + " ˆ");
				map.put(KEY_IMG, db.getResId(current.first));
				itemList.add(map);
			}
		}
		// lv.setAdapter(adapter);
		db.close();
		adapterList.notifyDataSetChanged();
	}

	public void groupByMonthResult() {
		DatabaseHandler db = new DatabaseHandler(getSherlockActivity());
		shoplist2 = db.getCostPerMonthPerType();
		monthlyCost = new ArrayList<Pair<String, Integer>>();

		for (ShopList item : shoplist2) {
			String month = "" + item.getMonth();
			String type = item.getTypeName();
			if (month.equals(FragmentStat.selected)) {
				int price = item.getPrice();

				monthlyCost.add(new Pair<String, Integer>(type, price));
			}
		}

		adapterList = new LabelCostListAdapter(getActivity(), itemList);
		lv.setAdapter(adapterList);

		for (Pair<String, Integer> current : monthlyCost) {
			HashMap<String, String> map = new HashMap<String, String>();

			if (current.second != 0) {
				map.put(KEY_TITLE, current.first);

				map.put(KEY_CORNER, "" + current.second + " ˆ");
				map.put(KEY_IMG, db.getResId(current.first));
				itemList.add(map);
			}
		}
		db.close();
		adapterList.notifyDataSetChanged();
	}
}