package com.elanor883.shoppingsheep;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Environment;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "shoppingManager";

	// Table names
	private static final String TABLE_SHOPLIST = "shoplist";
	private static final String TABLE_CATEGORIES = "categories";

	// Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TYPE_NAME = "type_name";
	private static final String KEY_PRICE = "price";
	private static final String KEY_DATE = "date";
	private static final String KEY_RESID = "resid";
	private static final String KEY_C_ID = "c_id";

	public DatabaseHandler(Context fragmentTab4) {
		super(fragmentTab4, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_SHOPLIST_TABLE = "CREATE TABLE " + TABLE_SHOPLIST + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE_NAME + " TEXT, "
				+ KEY_PRICE + " INTEGER, " + KEY_DATE + " TEXT" + ")";

		db.execSQL(CREATE_SHOPLIST_TABLE);

		String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE_NAME
				+ " TEXT, " + KEY_RESID + " TEXT, " + KEY_C_ID + " INTEGER "
				+ ")";

		db.execSQL(CREATE_CATEGORIES_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPLIST);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
		// Create tables again

		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	void addShop(ShopList contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TYPE_NAME, contact.getTypeName());
		values.put(KEY_PRICE, contact.getPrice());
		values.put(KEY_DATE, contact.getDate());

		// Inserting Row
		db.insert(TABLE_SHOPLIST, null, values);
		db.close(); // Closing database connection

	}

	void addCategory(Categories cat) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TYPE_NAME, cat.getTypeName());
		values.put(KEY_RESID, cat.getResid());
		values.put(KEY_C_ID, cat.getCId());
		// Inserting Row
		db.insert(TABLE_CATEGORIES, null, values);
		db.close(); // Closing database connection

	}

	// Getting single shop item
	ShopList getShop(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_SHOPLIST, new String[] { KEY_ID,
				KEY_TYPE_NAME, KEY_PRICE, KEY_DATE }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		ShopList shopping = new ShopList(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), Integer.parseInt(cursor.getString(2)),
				cursor.getString(3));

		return shopping;
	}

	// Getting All shop items
	public List<ShopList> getAllShops() {
		List<ShopList> shoppinglistList = new ArrayList<ShopList>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_SHOPLIST
				+ " ORDER BY date DESC";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ShopList shop_list = new ShopList();
				shop_list.setId(Integer.parseInt(cursor.getString(0)));
				shop_list.setTypeName(cursor.getString(1));
				shop_list.setPrice(Integer.parseInt(cursor.getString(2)));
				shop_list.setDate(cursor.getString(3));

				shoppinglistList.add(shop_list);
			} while (cursor.moveToNext());
		}

		cursor.close();

		return shoppinglistList;
	}

	// Updating single shop item
	public int updateContact(ShopList shopping) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TYPE_NAME, shopping.getTypeName());
		values.put(KEY_PRICE, shopping.getPrice());
		values.put(KEY_DATE, shopping.getDate());

		// updating row
		return db.update(TABLE_SHOPLIST, values, KEY_ID + " = ?",
				new String[] { String.valueOf(shopping.getId()) });
	}

	// Deleting single shop item
	public void deleteContact(ShopList contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SHOPLIST, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getId()) });
		db.close();
	}

	// Getting shoppings Count
	public int getShoppingsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_SHOPLIST;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public ArrayList<String> getCategories() {
		String selectQuery = "SELECT " + KEY_TYPE_NAME + " FROM "
				+ TABLE_CATEGORIES;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		ArrayList<String> cat = new ArrayList<String>();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				cat.add(cursor.getString(0));
				// Adding contact to list
			} while (cursor.moveToNext());
		}
		cursor.close();
		return cat;
	}

	public List<Categories> getFullCategories() {
		String selectQuery = "SELECT * FROM " + TABLE_CATEGORIES;
		List<Categories> catList = new ArrayList<Categories>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Categories cat = new Categories();
				cat.setId(Integer.parseInt(cursor.getString(0)));
				cat.setTypeName(cursor.getString(1));
				cat.setResid(cursor.getString(2));
				cat.setCId(Integer.parseInt(cursor.getString(3)));

				catList.add(cat);
			} while (cursor.moveToNext());
		}

		cursor.close();
		return catList;
	}

	public List<ShopList> getCostPerDayPerType() {
		String selectQuery = "select date, type_name, sum(price) from shoplist GROUP BY date, type_name";

		List<ShopList> shoppinglistList = new ArrayList<ShopList>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ShopList shop_list = new ShopList();
				shop_list.setDate(cursor.getString(0));
				shop_list.setTypeName(cursor.getString(1));
				shop_list.setPrice(Integer.parseInt(cursor.getString(2)));

				shoppinglistList.add(shop_list);
			} while (cursor.moveToNext());
		}

		cursor.close();

		return shoppinglistList;
	}

	public List<ShopList> getSumCostPerDay() {
		String selectQuery = "select date, sum(price) from shoplist GROUP BY date";

		List<ShopList> shoppinglistList = new ArrayList<ShopList>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ShopList shop_list = new ShopList();
				shop_list.setDate(cursor.getString(0));
				shop_list.setPrice(Integer.parseInt(cursor.getString(1)));

				shoppinglistList.add(shop_list);
			} while (cursor.moveToNext());
		}

		cursor.close();

		return shoppinglistList;
	}

	public List<ShopList> getSumCostPerWeek() {
		String selectQuery = "select strftime('%W', date) AS week, sum(price) from shoplist GROUP BY week";

		List<ShopList> shoppinglistList = new ArrayList<ShopList>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ShopList shop_list = new ShopList();
				shop_list.setWeek(Integer.parseInt(cursor.getString(0)));
				shop_list.setPrice(Integer.parseInt(cursor.getString(1)));

				shoppinglistList.add(shop_list);
			} while (cursor.moveToNext());
		}

		cursor.close();

		return shoppinglistList;
	}

	public List<ShopList> getSumCostPerMonth() {
		String selectQuery = "select strftime('%m', date) AS month, sum(price) from shoplist GROUP BY month";

		List<ShopList> shoppinglistList = new ArrayList<ShopList>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ShopList shop_list = new ShopList();
				shop_list.setMonth(Integer.parseInt(cursor.getString(0)));
				shop_list.setPrice(Integer.parseInt(cursor.getString(1)));

				shoppinglistList.add(shop_list);
			} while (cursor.moveToNext());
		}

		cursor.close();

		return shoppinglistList;
	}

	public List<ShopList> getCostPerWeekPerType() {
		String selectQuery = "select strftime('%W', date) AS week, type_name, sum(price) from shoplist GROUP BY week, type_name";

		List<ShopList> shoppinglistList = new ArrayList<ShopList>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ShopList shop_list = new ShopList();
				shop_list.setWeek(Integer.parseInt(cursor.getString(0)));
				shop_list.setTypeName(cursor.getString(1));
				shop_list.setPrice(Integer.parseInt(cursor.getString(2)));

				shoppinglistList.add(shop_list);
			} while (cursor.moveToNext());
		}

		cursor.close();

		return shoppinglistList;
	}

	public List<ShopList> getCostPerMonthPerType() {
		String selectQuery = "select strftime('%m', date) AS month, type_name, sum(price) from shoplist GROUP BY month, type_name";

		List<ShopList> shoppinglistList = new ArrayList<ShopList>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ShopList shop_list = new ShopList();
				shop_list.setMonth(Integer.parseInt(cursor.getString(0)));
				shop_list.setTypeName(cursor.getString(1));
				shop_list.setPrice(Integer.parseInt(cursor.getString(2)));

				shoppinglistList.add(shop_list);
			} while (cursor.moveToNext());
		}

		cursor.close();

		return shoppinglistList;
	}

	public List<ShopList> getLastFewItems(int num) {

		String selectQuery;
		if (num != 0) {
			selectQuery = "select id, date, price, type_name from shoplist order by date desc limit "
					+ num;
		} else {
			selectQuery = "select id, date, price, type_name from shoplist order by date desc";
		}

		List<ShopList> shoppinglistList = new ArrayList<ShopList>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ShopList shop_list = new ShopList();
				shop_list.setId(Integer.parseInt(cursor.getString(0)));
				shop_list.setDate(cursor.getString(1));
				shop_list.setPrice(Integer.parseInt(cursor.getString(2)));
				shop_list.setTypeName(cursor.getString(3));

				shoppinglistList.add(shop_list);
			} while (cursor.moveToNext());
		}

		cursor.close();

		return shoppinglistList;
	}

	public String getResId(String current_cat) {
		String selectQuery = "SELECT " + KEY_RESID + " FROM "
				+ TABLE_CATEGORIES + " WHERE " + KEY_TYPE_NAME + "='"
				+ current_cat + "'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		String result = null;

		if (cursor.moveToFirst()) {
			do {
				result = cursor.getString(0);

			} while (cursor.moveToNext());
		}
		cursor.close();
		return result;
	}

	public boolean exportDB() {
		File sd = Environment.getExternalStorageDirectory();
		File data = Environment.getDataDirectory();
		FileChannel source = null;
		FileChannel destination = null;
		String currentDBPath = "/data/" + "com.elanor883.shoppingsheep"
				+ "/databases/" + DATABASE_NAME;
		File file = new File("data/" + currentDBPath);
		if (file.exists()) {
			String backupDBPath = DATABASE_NAME;
			File currentDB = new File(data, currentDBPath);
			File backupDB = new File(sd, backupDBPath);
			try {
				source = new FileInputStream(currentDB).getChannel();
				destination = new FileOutputStream(backupDB).getChannel();
				destination.transferFrom(source, 0, source.size());
				source.close();
				destination.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	public static String DB_FILEPATH = "/data/data/com.elanor883.shoppingsheep/databases/"
			+ DATABASE_NAME;

	public boolean importDB(String dbPath) throws IOException {

		close();
		File newDb = new File(dbPath);
		File oldDb = new File(DB_FILEPATH);
		if (newDb.exists()) {
			copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));

			getWritableDatabase().close();
			return true;
		}
		return false;
	}

	private void copyFile(FileInputStream fromFile, FileOutputStream toFile)
			throws IOException {
		FileChannel fromChannel = null;
		FileChannel toChannel = null;
		try {
			fromChannel = fromFile.getChannel();
			toChannel = toFile.getChannel();
			fromChannel.transferTo(0, fromChannel.size(), toChannel);
		} finally {
			try {
				if (fromChannel != null) {
					fromChannel.close();
				}
			} finally {
				if (toChannel != null) {
					toChannel.close();
				}
			}
		}
	}

	public void deleteShopRow(int ind) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SHOPLIST, KEY_ID + " = ?",
				new String[] { String.valueOf(ind) });
		db.close();
	}

	public void updateShoppingItem(String e_type, String e_date, int e_price,
			int e_id) {
		String updateQuery = "UPDATE shoplist SET type_name='" + e_type
				+ "', date='" + e_date + "', price=" + e_price + " WHERE id="
				+ e_id;

		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(updateQuery);

		db.close();

	}

	public void deleteCategory(String name) {

		SQLiteDatabase db = this.getWritableDatabase();
		String deleteQuery = "DELETE FROM " + TABLE_CATEGORIES + "	 WHERE "
				+ KEY_TYPE_NAME + "='" + name + "'";

		db.execSQL(deleteQuery);
		deleteQuery = "DELETE FROM " + TABLE_SHOPLIST + "	 WHERE "
				+ KEY_TYPE_NAME + "='" + name + "'";
		db.execSQL(deleteQuery);
		db.close();
	}

	public void updateCategory(String old_type, String e_type, String e_resid,
			int e_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String updateQuery = "UPDATE " + TABLE_CATEGORIES + "  SET type_name='"
				+ e_type + "'," + KEY_RESID + "='" + e_resid + "'  WHERE id="
				+ e_id;

		db.execSQL(updateQuery);
		updateQuery = "UPDATE " + TABLE_SHOPLIST + "  SET type_name='" + e_type
				+ "'  WHERE type_name='" + old_type + "'";

		db.execSQL(updateQuery);

		db.close();

	}

}