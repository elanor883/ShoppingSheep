package com.elanor883.shoppingsheep;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 
public class LabelListAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

 
    public LabelListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
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
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row_2, null);
 
        TextView title = (TextView)vi.findViewById(R.id.category); // title
        Button btn = (Button)vi.findViewById(R.id.list_button);
        //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        HashMap<String, String> mylist = new HashMap<String, String>();
        mylist = data.get(position);
 
        // Setting all values in listview
        title.setText(mylist.get(FragmentCategories.KEY_TYPE));
     //   btn.setText(mylist.get(FragmentTab2.KEY_T));
     //   Resources res = vi.getResources();
     //   Drawable drawable = res.getDrawable(R.drawable.btn1bg);
        btn.setBackgroundResource(Integer.parseInt((mylist.get(FragmentCategories.KEY_COLOR))));
       // int id = vi.getResources().getIdentifier(mylist.get(FragmentTab2.KEY_COLOR), null, null);
    // Log.d("res id", ""+id);
        // imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        

		if (MainActivity.dark_bkg  == true) {
			vi.setBackgroundColor(Color.BLACK);
			//duration.setTextColor(Color.parseColor("#30b4e0"));
			title.setTextColor(Color.WHITE);
			//artist.setTextColor(Color.WHITE);

		} else {
			vi.setBackgroundColor(Color.parseColor("#f1f1f2"));
			//duration.setTextColor(Color.parseColor("#30b4e0"));
			title.setTextColor(Color.BLACK);
			//artist.setTextColor(Color.BLACK);
		}
        return vi;
    }
}