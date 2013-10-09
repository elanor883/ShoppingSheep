package com.elanor883.shoppingsheep;

public class Categories {
     
    //private variables
    int _shop_id;
    String _type_name;
    int _c_id;
    String _resid;
     
    // Empty constructor

    public Categories(){
        
    }
    // constructor
    public Categories(int shop_id, String type_name, String resid, int c_id){
        this._shop_id = shop_id;
        this._type_name = type_name;
        this._resid = resid;
        this._c_id = c_id;

    }
     
    // constructor
    public Categories(String type_name, String resid, int c_id){
        this._type_name = type_name;
        this._resid = resid;
        this._c_id = c_id;
    }
    // getting ID
    public int getId(){
        return this._shop_id;
    }
     
    // setting id
    public void setId(int shop_id){
        this._shop_id = shop_id;
    }
     
    public String getTypeName(){
        return this._type_name;
    }
     
    // setting name
    public void setTypeName(String type_name){
        this._type_name = type_name;
    }
    
    // getting name
    public String getResid(){
        return this._resid;
    }
     
    // setting name
    public void setResid(String resid){
        this._resid = resid;
    }
     
    // getting name
    public int getCId(){
        return this._c_id;
    }
     
    // setting name
    public void setCId(int c_id){
        this._c_id = c_id;
    }
}
