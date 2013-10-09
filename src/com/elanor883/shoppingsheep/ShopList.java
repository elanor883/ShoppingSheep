package com.elanor883.shoppingsheep;

public class ShopList {
     
    //private variables
    int _shop_id;
    String _date;
    String _type_name;
    int _price;
    int _week, _month;
     
    // Empty constructor

    public ShopList(){
        
    }
    // constructor
    public ShopList(int shop_id, String type_name, int price, String date){
        this._shop_id = shop_id;
        this._date = date;
        this._type_name = type_name;
        this._price = price;
    }
     
    // constructor
    public ShopList(String type_name, int price, String date){
        this._type_name = type_name;
        this._price = price;
        this._date = date;
    }
    // getting ID
    public int getId(){
        return this._shop_id;
    }
     
    // setting id
    public void setId(int shop_id){
        this._shop_id = shop_id;
    }
     
    // getting name
    public String getTypeName(){
        return this._type_name;
    }
     
    // setting name
    public void setTypeName(String type_name){
        this._type_name = type_name;
    }
     
    // getting phone number
    public int getPrice(){
        return this._price;
    }
     
    // setting phone number
    public void setPrice(int price){
        this._price = price;
    }
    
    public String getDate(){
        return this._date;
    }
     
    // setting phone number
    public void setDate(String date){
        this._date = date;
    }
    
    public void setWeek(int week)
    {
    	this._week = week;
    }
    
    public int getWeek()
    {
    	return this._week;
    }
    
    public void setMonth(int month)
    {
    	this._month = month;
    }
    
    public int getMonth()
    {
    	return this._month;
    }
}