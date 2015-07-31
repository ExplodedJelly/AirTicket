package com.air.edward.airticket.DataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.air.edward.airticket.Object.*;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AirOrderSqlite extends SQLiteOpenHelper {
	
	private SQLiteDatabase db;
	
	public  AirOrderSqlite(Context context){
		this(context,"AirOrder.db",null,1);
	}
	
	public AirOrderSqlite(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	//该方法只会在第一次创建数据库的时候执行
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table user(_id integer PRIMARY KEY AUTOINCREMENT,user_name varchar(15),password varchar(15),email varchar(25),phone varchar(11),address varchar(30))");
		db.execSQL("create table orders(_id integer PRIMARY KEY AUTOINCREMENT,startdrome varchar(20),arrivedrome varchar(20),airlinecode varchar(20),startdate varchar(20),company varchar(20),startTime varchar(20),arriveTime varchar(20),state varchar(15))");
		db.execSQL("create table help(_id integer PRIMARY KEY AUTOINCREMENT,airport varchar(100),dorme varchar(20),bus varchar(30),tip varchar(50))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	//插入用户信息
	public void insert(User user,String sql){
		//String sql = "insert into user values(?,?,?,?,?,?)";
		//获取SQLiteDatabase实例
		db = getWritableDatabase();
		Object args[] = {null,user.getUser_name(),user.getPassword(),user.getEmail(),user.getPhone(),user.getAddress()};
		db.execSQL(sql,args);
		db.close();
	}
	//插入订单信息
	public void insert(Order order,String sql){
		db=getWritableDatabase();
		Object args[]={null,order.getStartdrome(),order.getArrivedrome(),order.getAirlinecode(),order.getStartdate(),order.getCompany(),order.getStartTime(),order.getArriveTime(),order.getState()};
		db.execSQL(sql,args);
		db.close();
	}
	//删除订单信息
	public void delete(String id,String sql){
		//String sql = "delete from order where _id=?";
		db = getWritableDatabase();
		Object args[] = {id};
		db.execSQL(sql,args);
		db.close();
	}
	//更新订单信息
	public void update(String id, String sql) {
		// TODO Auto-generated method stub
		db = getWritableDatabase();
		Object args[] = {id};
		db.execSQL(sql,args);
		db.close();
	}
	
	public Cursor  queryForCursor(){
		String sql = "select _id,name from user";
		db = getReadableDatabase();
		return db.rawQuery(sql, null);
	}
	
	//订单查询
	public List<Map<String,String>> queryOrder(String sql){
		List<Map<String,String>> orders= new ArrayList<Map<String,String>>();
		//String sql = "select _id,name from order";
		db = getReadableDatabase();
		Cursor c = db.rawQuery(sql,null);
		c.moveToLast();
        c.moveToNext();
		Map<String,String> order = null;
		while(c.moveToPrevious()){
			order = new HashMap<String, String>();
			String id = c.getString( c.getColumnIndex("_id") );
			String stardrome = c.getString(c.getColumnIndex("startdrome"));
			String arrivedrome = c.getString(c.getColumnIndex("arrivedrome"));
			String airlinecode = c.getString(c.getColumnIndex("airlinecode"));
			String date = c.getString(c.getColumnIndex("startdate"));
			String company = c.getString(c.getColumnIndex("company"));
			String startTime = c.getString(c.getColumnIndex("startTime"));
			String arriveTime = c.getString(c.getColumnIndex("arriveTime"));
			String state = c.getString(c.getColumnIndex("state"));
			order.put("id", id);
			order.put("stardrome", stardrome);
			order.put("arrivedrome", arrivedrome);
			order.put("airlinecode", airlinecode);
			order.put("date", date);
			order.put("company", company);
			order.put("startTime", startTime);
			order.put("arriveTime", arriveTime);
			order.put("state", state);
			orders.add(order);
		}
		c.close();
		db.close();
		return orders;
	}
	
	//用户登录查询
	public Map<String, String> query(User loginer,String sql){
		db = getReadableDatabase();
		Cursor c = db.rawQuery(sql,new String []{loginer.getEmail(),loginer.getPassword()});
		Map<String,String> user = null;
		user = new HashMap<String, String>();
		if(c.getCount()>0){
			c.moveToNext();
			String id = c.getString( c.getColumnIndex("_id"));
			String user_name = c.getString(c.getColumnIndex("user_name"));
			String password = c.getString(c.getColumnIndex("password"));
			String email = c.getString(c.getColumnIndex("email"));
			String phone = c.getString(c.getColumnIndex("phone"));
			String address = c.getString(c.getColumnIndex("address"));
			user.put("id", id);
			user.put("user_name", user_name);
			user.put("password", password);
			user.put("email", email);
			user.put("phone", phone);
			user.put("address", address);
		}
		c.close();
		db.close();
		return user;
	}
	//机场信息查询
	public Map<String, String> query(Help help,String sql){
		db = getReadableDatabase();
		Cursor c = db.rawQuery(sql,new String []{help.getAirport()});
		Map<String,String>  msg= null;
		msg = new HashMap<String, String>();
		if(c.getCount()>0){
			c.moveToNext();
			String airport= c.getString( c.getColumnIndex("airport"));
			String drome = c.getString(c.getColumnIndex("dorme"));
			String bus = c.getString(c.getColumnIndex("bus"));
			String tip = c.getString(c.getColumnIndex("tip"));
			msg.put("airport", airport);
			msg.put("drome", drome);
			msg.put("bus", bus);
			msg.put("tip", tip);
		}
		c.close();
		db.close();
		return msg;
	}

}
