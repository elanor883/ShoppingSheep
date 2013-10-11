package com.elanor883.shoppingsheep;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class FragmentShopping extends SherlockFragment {

	static final String KEY_CAT = "cat";
	static final String KEY_DATE = "date";
	static final String KEY_PRICE = "price";
	static final String KEY_TITLE = "title";
	static final String KEY_SUBTITLE = "sub";
	static final String KEY_CORNER = "corner";
	ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String>>();
	Button dd;
	int year;
	int month;
	int day;
	String cat;
	String date;
	int price;
	SherlockFragmentActivity parent;
	View mView;
	ShopListViewAdapter mAdapterList;
	ListView lv;
	ArrayList<Integer> idArray;
	int selectedListItem;
	List<ShopList> clist;
	Menu mymenu;

	private static final int MY_DATE_DIALOG_ID = 3;

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

			if (mymenu != null) {
				onPrepareOptionsMenu(mymenu);
				Log.d("mymenu fr1", "called");

			}

			Log.d("visiblefr1", "" + MainActivity.fr1Imp);
			parent = getSherlockActivity();
			if (isVisibleToUser) {
				if (MainActivity.fr1Imp == true) {
					MainActivity.fr1Imp = false;
					Log.d("fragment1", "visible import");
					refreshCurrentFragment();
				}

				/*
				 * if(FragmentTab2.update == true) { FragmentTab2.update =
				 * false; refreshCurrentFragment(); }
				 */

				setBkg();
				// itemList.clear();
				// mAdapterList.notifyDataSetChanged();

				if (mAdapterList != null) {
					mAdapterList.notifyDataSetChanged();
				}

				else {
					Log.d("fr1", "kva anyjat enek a szarnak");
				}

			}

		} else {
			Log.d("fr1vis", "fos");
		}

	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from fragmenttab2.xml
		mView = inflater.inflate(R.layout.fragmenttab1, container, false);
		registerForContextMenu(mView.findViewById(R.id.listitem_lv));
		setHasOptionsMenu(true);

		setBkg();

		dd = (Button) mView.findViewById(R.id.add_item_button);
		lv = (ListView) mView.findViewById(R.id.listitem_lv);

		idArray = new ArrayList<Integer>();
		DatabaseHandler db = new DatabaseHandler(getSherlockActivity());
		List<ShopList> clist = db.getAllShops();
		itemList.clear();
		for (ShopList s : clist) {
			HashMap<String, String> map = new HashMap<String, String>();
			// color =
			map.put(KEY_TITLE, s.getTypeName());
			map.put(KEY_SUBTITLE, s.getDate());
			map.put(KEY_CORNER, "" + s.getPrice() + " �");
			itemList.add(map);
			idArray.add(s.getId());
			Log.d("shop_id", "" + s.getId());
		}

		db.close();

		// ArrayList<HashMap<String, String>> labelList = new
		// ArrayList<HashMap<String, String>>();

		// final LabelListAdapter adapter = new LabelListAdapter(getActivity(),
		// labelList);

		// lv.setAdapter(adapter);

		mAdapterList = new ShopListViewAdapter(getActivity(), itemList);
		lv.setAdapter(mAdapterList);

		dd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				openItemDialog(false, "", "", "");
			}
		});

		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> adapterView,
					View view, int index, long arg3) { // item: "string line"
														// that user click
														// //View

				selectedListItem = index;
				Log.d("longclick", "" + idArray.get(selectedListItem));

				openMenuDialog();
				Log.d("Item has been clicked is :", " " + index);
				return true;
			}
		});

		return mView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}

	/*
	 * public boolean onMenuItemSelected(int featureId, MenuItem item) {
	 * DatabaseHandler db; switch (item.getItemId()) {
	 * 
	 * case R.id.imp_exp_btn: // write your code here Log.d("fr1", "imp");
	 * return true;
	 * 
	 * case R.id.settings_btn:
	 * 
	 * // dark_bkg = false; Log.d("fr1", "set"); return true;
	 * 
	 * default: break;
	 * 
	 * } return true; }
	 */

	/*
	 * 
	 * public boolean onOptionsItemSelected(MenuItem item) { DatabaseHandler db;
	 * switch (item.getItemId()) {
	 * 
	 * case R.id.settings_btn: // write your code here
	 * 
	 * // db = new DatabaseHandler(this); db.exportDB(); db.close();
	 * 
	 * Log.d("MainActivity", "import"); parent = getSherlockActivity();
	 * 
	 * if (parent instanceof MainActivity) { ((MainActivity) parent).dark_bkg =
	 * false;
	 * 
	 * }
	 * 
	 * refreshFragments();
	 * 
	 * return true;
	 * 
	 * 
	 * default: break;
	 * 
	 * } return super.onOptionsItemSelected(item); }
	 */

	public void editItem(String date, String type, String price) {

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		DatabaseHandler db;
		switch (item.getItemId()) {

		case R.id.settings_btn1:

			settingsMenu();
			return true;
		case R.id.exp_btn:

			db = new DatabaseHandler(getActivity());
			if (!db.exportDB()) {
				Toast.makeText(getSherlockActivity().getApplicationContext(),
						"Sorry, couldn't export the database.",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getSherlockActivity().getApplicationContext(),
						"Database exported! (/sdcard/shoppingManager)",
						Toast.LENGTH_LONG).show();
			}
			db.close();

			return true;
		case R.id.imp_btn:

			importMenu();

			return true;
		case R.id.last_10:

			refreshLastFewElements(10);

			return true;
		case R.id.last_20:

			refreshLastFewElements(20);

			return true;
		case R.id.all_items:

			refreshLastFewElements(0);

			return true;
		default:
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	public void settingsMenu() {
		if (MainActivity.dark_bkg == true) {

			MainActivity.dark_bkg = false;
			setBkg();
			// view.setBackgroundColor(Color.parseColor("#f1f1f2"));
			Log.d("fr3", "settings - change to white" + MainActivity.dark_bkg);

		} else {

			MainActivity.dark_bkg = true;
			setBkg();
			// view.setBackgroundColor(Color.BLACK);

			Log.d("fr3", "settings - change to black" + MainActivity.dark_bkg);
		}

		// itemList.clear();

		mAdapterList.notifyDataSetChanged();
		// MainActivity.savePreferences("is_dark_bkg", MainActivity.dark_bkg);
	}

	public void importMenu() {
		DatabaseHandler db;
		db = new DatabaseHandler(getActivity());
		try {
			String dbPath = Environment.getExternalStorageDirectory().getPath()
					+ "/shoppingManager";
			Log.d("path", dbPath);
			File file = new File(dbPath);
			if (file.exists()) {
				db.importDB(dbPath);
				MainActivity.fr2Imp = true;
				MainActivity.fr1Imp = true;
				Log.d("fragment1", "menu import");
				// MainActivity.fr3Imp = true;
				refreshCurrentFragment();
				Toast.makeText(getSherlockActivity().getApplicationContext(),
						"Database imported!", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getSherlockActivity().getApplicationContext(),
						"Doesn't exist exported database yet",
						Toast.LENGTH_LONG).show();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();

	}

	public void refreshCurrentFragment() {

		itemList.clear();
		mAdapterList.notifyDataSetChanged();
		Log.d("fr1-refresh", "refressss");
		FragmentShopping fragment = new FragmentShopping();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fragment1_container, fragment);
		// ft.addToBackStack(null);
		ft.commit();
	}

	/*
	 * @Override public void onCreateOptionsMenu(Menu menu, MenuInflater
	 * inflater) { // TODO Add your menu entries here
	 * super.onCreateOptionsMenu(menu, inflater); inflater.inflate(R.menu.main2,
	 * menu); }
	 */
	/*
	 * @Override public void onCreateOptionsMenu(Menu menu, MenuInflater
	 * inflater) { // TODO Add your menu entries here inflater =
	 * getSherlockActivity().getSupportMenuInflater();
	 * inflater.inflate(R.menu.main, menu); super.onCreateOptionsMenu(menu,
	 * inflater); }
	 */
	@Override
	public void onPrepareOptionsMenu(Menu menu) {

		mymenu = menu;
		MenuItem item = menu.findItem(R.id.imp_btn);
		MenuItem item2 = menu.findItem(R.id.exp_btn);
		MenuItem item3 = menu.findItem(R.id.last_10);
		MenuItem item4 = menu.findItem(R.id.last_20);
		MenuItem item5 = menu.findItem(R.id.all_items);
		MenuItem item6 = menu.findItem(R.id.order_daily);
		MenuItem item7 = menu.findItem(R.id.order_weekly);
		MenuItem item8 = menu.findItem(R.id.order_monthly);
		MenuItem item9 = menu.findItem(R.id.back_btn);
		MenuItem item10 = menu.findItem(R.id.settings_btn1);
		MenuItem item11 = menu.findItem(R.id.dummy);
		MenuItem item12 = menu.findItem(R.id.settings_btn3);
		item.setVisible(true);
		item2.setVisible(true);
		item3.setVisible(true);
		item4.setVisible(true);
		item5.setVisible(true);
		item6.setVisible(false);
		item7.setVisible(false);
		item8.setVisible(false);
		item9.setVisible(false);
		item10.setEnabled(true);
		item10.setVisible(true);
		item11.setVisible(false);
		item12.setVisible(false);
		super.onPrepareOptionsMenu(menu);
	}

	/*
	 * @Override public void onCreateContextMenu(ContextMenu menu, View v,
	 * ContextMenuInfo menuInfo) { super.onCreateContextMenu(menu, v, menuInfo);
	 * Log.d("fr1", "nyom"); if (v.getId() == R.id.listitem_lv) {
	 * AdapterView.AdapterContextMenuInfo info =
	 * (AdapterView.AdapterContextMenuInfo) menuInfo; selectedListItem =
	 * info.position; menu.setHeaderTitle("" + info.position + " " +
	 * idArray.get(selectedListItem)); // menu.setHeaderTitle("Menu");
	 * android.view.MenuInflater inflater = getSherlockActivity()
	 * .getMenuInflater(); inflater.inflate(R.menu.main2, menu); } }
	 * 
	 * public boolean onContextItemSelected(android.view.MenuItem item) {
	 * DatabaseHandler db = new DatabaseHandler(getActivity()); //
	 * db.deleteShopRow(idArray.get(selectedListItem)); if (item.getItemId() ==
	 * R.id.delete_btn) { Log.d("ContextCheck", "EDIT!"); // toast =
	 * Toast.makeText(this, "Edit!", Toast.LENGTH_SHORT); // toast.show(); //
	 * db.deleteContact(clist.get(selectedListItem));
	 * db.deleteShopRow(idArray.get(selectedListItem)); //
	 * db.deleteContact(clist.get(selectedListItem)); Log.d("nyomos fragment1",
	 * "" + selectedListItem + " " + idArray.get(selectedListItem));
	 * refreshLastFewElements(10); db.close(); }
	 * 
	 * else if (item.getItemId() == R.id.edit_btn) { Log.d("ContextCheck",
	 * "edit"); HashMap<String, String> map = new HashMap<String, String>(); //
	 * color = // map.put(KEY_TITLE, s.getTypeName()); // map.put(KEY_SUBTITLE,
	 * s.getDate()); // map.put(KEY_CORNER, "" + s.getPrice() + " �"); map =
	 * itemList.get(selectedListItem); String type = map.get(KEY_TITLE); String
	 * date = map.get(KEY_SUBTITLE); String price[] =
	 * map.get(KEY_CORNER).split(" ");
	 * 
	 * Log.d("edit", date + " " + type + " " + price[0]);
	 * 
	 * openItemDialog(true, type, date, price[0]); // toast =
	 * Toast.makeText(this, "Edit!", Toast.LENGTH_SHORT); // toast.show(); //
	 * db.deleteContact(clist.get(selectedListItem)); //
	 * db.deleteShopRow(idArray.get(selectedListItem)); //
	 * db.deleteContact(clist.get(selectedListItem)); Log.d("nyomos fragment1",
	 * "" + selectedListItem + " " + idArray.get(selectedListItem));
	 * refreshLastFewElements(10); db.close(); }
	 * 
	 * else if (item.getItemId() == R.id.delete_btn2) { Log.d("ContextCheck",
	 * "EDIT!ttt"); // toast = Toast.makeText(this, "Edit!",
	 * Toast.LENGTH_SHORT); // toast.show(); //
	 * db.deleteContact(clist.get(selectedListItem)); //
	 * db.deleteShopRow(idArray.get(selectedListItem)); //
	 * db.deleteContact(clist.get(selectedListItem)); //
	 * db.deleteCategory(selectedCat); }
	 * 
	 * return true; }
	 */

	public void refreshLastFewElements(int num) {
		DatabaseHandler db = new DatabaseHandler(getSherlockActivity());
		clist = db.getLastFewItems(num);
		itemList.clear();
		idArray.clear();
		for (ShopList s : clist) {
			HashMap<String, String> map = new HashMap<String, String>();
			// color =
			map.put(KEY_TITLE, s.getTypeName());
			map.put(KEY_SUBTITLE, s.getDate());
			map.put(KEY_CORNER, "" + s.getPrice() + " �");
			itemList.add(map);
			idArray.add(s.getId());
			Log.d("idarray", "" + s.getId());
		}
		// adapter.notifyDataSetChanged();

		db.close();

		mAdapterList.notifyDataSetChanged();

		// refreshCurrentFragment();

	}

	public void setBkg() {
		if (MainActivity.dark_bkg == false && mView != null) {
			// ((MainActivity) parent).activePage = 1;
			mView.setBackgroundColor(Color.parseColor("#f1f1f2"));
		} else if (MainActivity.dark_bkg == true && mView != null) {
			mView.setBackgroundColor(Color.BLACK);
		}
	}

	public void openItemDialog(final boolean edit, String e_type,
			String e_date, String e_price) {
		// custom dialog
		final Dialog dialog = new Dialog(getActivity());

		dialog.setContentView(R.layout.customdialog);
		if (!edit) {
			dialog.setTitle("Add new item");
		} else {
			dialog.setTitle("Edit item");
		}
		// set the custom dialog components - text, image and button
		final EditText text_price = (EditText) dialog
				.findViewById(R.id.editText1);
		// text.setText("Android custom dialog example!");
		final DatePicker dp = (DatePicker) dialog
				.findViewById(R.id.datePicker1);

		final TextView tv = (TextView) dialog.findViewById(R.id.textView3);
		// dp.setCalendarViewShown(false);
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= 11) {
			try {
				Method m = dp.getClass().getMethod("setCalendarViewShown",
						boolean.class);
				m.invoke(dp, false);
			} catch (Exception e) {
			} // eat exception in our case
		}
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogbutton);
		// if button is clicked, close the custom dialog

		DatabaseHandler db = new DatabaseHandler(getSherlockActivity());
		List<String> list = new ArrayList<String>();
		list = db.getCategories();
		db.close();
		String[] array_spinner;
		int i = 0;
		/*
		 * for(String c: cat) { Log.d("cat", c); array_spinner[i++]=c; }
		 */
		final Spinner spin = (Spinner) dialog.findViewById(R.id.spinner1);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item,
				list);
		spin.setAdapter(adapter);

		if (edit == true) {
			spin.setSelection(getPositionOfType(list, e_type));
			text_price.setText(e_price);
			dp.updateDate(Integer.parseInt(e_date.substring(0, 4)),
					Integer.parseInt(e_date.substring(5, 7)) - 1,
					Integer.parseInt(e_date.substring(8, 10)));
			// spin.setSelection(position, animate);
		}

		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (spin.getSelectedItem() == null) {
					tv.setText("You need to add category first!");
				} else if (text_price.length() > 0
						&& Integer.parseInt(text_price.getText().toString()) != 0
						&& spin.getSelectedItem().toString() != null
						&& Integer.parseInt(text_price.getText().toString()) < 214748369) {

					year = dp.getYear();
					month = dp.getMonth() + 1;
					day = dp.getDayOfMonth();
					// updateDisplay();
					// Log.d("datepicker", ""+year+" "+month+" "+day);

					if (month < 10 && day < 10) {
						date = "" + year + "-0" + month + "-0" + day;
						Log.d("datepicker1", date);
					} else if (month < 10 && day >= 10) {
						date = "" + year + "-0" + month + "-" + day;
						Log.d("datepicker2", date);
					} else if (month >= 10 && day < 10) {
						date = "" + year + "-" + month + "-0" + day;
						Log.d("datepicker3", date);
					} else if (month >= 10 && day >= 10) {
						date = "" + year + "-" + month + "-" + day;
						Log.d("datepicker4", date);
					}

					cat = spin.getSelectedItem().toString();
					price = Integer.parseInt(text_price.getText().toString());
					Log.d("result", "" + date + " " + cat + " " + price);

					ShopList slist = new ShopList(cat, price, date);
					DatabaseHandler db = new DatabaseHandler(
							getSherlockActivity());
					if (edit == false) {
						db.addContact(slist);
					}

					else if (edit == true) {
						db.updateShoppingItem(cat, date, price,
								idArray.get(selectedListItem));
					}
					db.close();

					HashMap<String, String> map = new HashMap<String, String>();
					// color =
					map.put(KEY_TITLE, cat);
					map.put(KEY_SUBTITLE, date);
					map.put(KEY_CORNER, "" + price + " �");
					itemList.add(map);
					// lv.setAdapter(adapter);
					mAdapterList.notifyDataSetChanged();

					refreshLastFewElements(10);
					dialog.dismiss();
				}

			}
		});

		dialog.show();
	}

	private int getPositionOfType(List<String> list, String e_type) {
		// TODO Auto-generated method stub
		int position = 0;
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i).equals(e_type)) {
				position = i;
			}
		}

		return position;
	}

	public void openMenuDialog() {
		// custom dialog
		final Dialog dialog = new Dialog(getActivity());

		dialog.setContentView(R.layout.menu_dialog1);
		dialog.setTitle("Menu");

		Button editBtn = (Button) dialog.findViewById(R.id.menuEditBtn);
		Button deleteBtn = (Button) dialog.findViewById(R.id.menuDeleteBtn);

		final DatabaseHandler db = new DatabaseHandler(getSherlockActivity());

		editBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Log.d("ContextCheck", "edit");
				HashMap<String, String> map = new HashMap<String, String>();

				map = itemList.get(selectedListItem);
				String type = map.get(KEY_TITLE);
				String date = map.get(KEY_SUBTITLE);
				String price[] = map.get(KEY_CORNER).split(" ");

				Log.d("edit", date + " " + type + " " + price[0]);

				openItemDialog(true, type, date, price[0]);

				Log.d("nyomos fragment1",
						"" + selectedListItem + " "
								+ idArray.get(selectedListItem));
				refreshLastFewElements(10);
				db.close();
				dialog.dismiss();

			}
		});

		deleteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Log.d("ContextCheck", "DELETE");

				AlertDialog.Builder builder1 = new AlertDialog.Builder(
						getSherlockActivity());
				builder1.setTitle("Delete category");
				builder1.setMessage("Are you sure you want to delete this item");
				builder1.setCancelable(true);
				builder1.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								db.deleteShopRow(idArray.get(selectedListItem));
								// db.deleteContact(clist.get(selectedListItem));
								Log.d("nyomos fragment1", "" + selectedListItem
										+ " " + idArray.get(selectedListItem));
								refreshLastFewElements(10);
								db.close();
								// dialog.cancel();
							}
						});
				builder1.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

				AlertDialog alert11 = builder1.create();
				alert11.show();

				dialog.dismiss();

			}
		});

		dialog.show();
	}

}
