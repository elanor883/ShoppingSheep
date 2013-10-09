package com.elanor883.shoppingsheep;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopListViewAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	TextView title, duration, artist;
	private int selectedIndex;

	public ShopListViewAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		selectedIndex = -1;
		// imageLoader=new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public void setSelectedIndex(int ind) {
		selectedIndex = ind;
		notifyDataSetChanged();
	}

	public String getItemDate(int position) {
		if (title != null) {
			return (String) title.getText();
		}
		return null;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.list_row, null);

			/*
			 * if(FragmentTab1.ch == false){ vi =
			 * inflater.inflate(R.layout.list_row, null); }
			 * 
			 * else{ vi = inflater.inflate(R.layout.list_row, null); }
			 */
		}
		title = (TextView) vi.findViewById(R.id.title); // title
		artist = (TextView) vi.findViewById(R.id.artist); // artist name
		duration = (TextView) vi.findViewById(R.id.duration); // duration
		// ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); //
		// thumb image

		HashMap<String, String> song = new HashMap<String, String>();
		song = data.get(position);

		// Setting all values in listview
		title.setText(song.get(Fragment3.KEY_TITLE));
		artist.setText(song.get(Fragment3.KEY_SUBTITLE));
		duration.setText(song.get(Fragment3.KEY_CORNER));

		if (MainActivity.dark_bkg == true) {
			vi.setBackgroundColor(Color.BLACK);
			duration.setTextColor(Color.parseColor("#30b4e0"));
			title.setTextColor(Color.WHITE);
			artist.setTextColor(Color.WHITE);

		} else {
			vi.setBackgroundColor(Color.parseColor("#f1f1f2"));
			duration.setTextColor(Color.parseColor("#30b4e0"));
			title.setTextColor(Color.BLACK);
			artist.setTextColor(Color.BLACK);
		}

		if (selectedIndex != -1 && position == selectedIndex) {
			vi.setBackgroundColor(Color.parseColor("#30b4e0"));
			duration.setTextColor(Color.WHITE);
		} else {
			//vi.setBackgroundColor(Color.parseColor("#f1f1f2"));
			duration.setTextColor(Color.parseColor("#30b4e0"));
		}

		
		// holder.tv.setText("" + (position + 1) + " " +
		// testList.get(position).getTestText());

		// imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL),
		// thumb_image);
		return vi;
	}
}