package com.air.edward.airticket.Activity;

import java.util.Map;

import com.air.edward.airticket.R;
import com.air.edward.airticket.DataBase.AirOrderSqlite;
import com.air.edward.airticket.Object.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class SelectedAirActivity extends AppCompatActivity{
	
	private TextView startdrome;
	private TextView arrivedrome;
	private TextView date;
	private TextView company;
	private TextView startTime;
	private TextView arriveTime;
	private TextView airlineCode;
	private TextView order;
	
	private int position;
	private String startDate;
	Toolbar toolbar = null;

	private AirOrderSqlite air_db;
	private Map<String,String> listItem;
	private void setupToolbar() {
		toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		final ActionBar actionBar = getSupportActionBar();

		if (actionBar != null) {
			actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}
	@Override
	public  void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectedair);
		
		startdrome=(TextView)findViewById(R.id.startdrome);
		arrivedrome=(TextView)findViewById(R.id.arrivedrome);
		date=(TextView)findViewById(R.id.date);
		company=(TextView)findViewById(R.id.company);
		startTime=(TextView)findViewById(R.id.startTime);
		arriveTime=(TextView)findViewById(R.id.arriveTime);
		airlineCode=(TextView)findViewById(R.id.airlineCode);
		order=(TextView)findViewById(R.id.order);
		
		Bundle bundle=this.getIntent().getExtras();
		position=bundle.getInt("position");
		startDate=bundle.getString("date");
		listItem=SeekListActivity.listItems.get(position);
		startdrome.setText(listItem.get("StartDrome"));
		arrivedrome.setText(listItem.get("ArriveDrome"));
		airlineCode.setText(listItem.get("AirlineCode"));
		company.setText(listItem.get("Company"));
		startTime.setText(listItem.get("StartTime"));
		arriveTime.setText(listItem.get("ArriveTime"));
		date.setText(startDate);
		order.setClickable(true); 
		air_db=new AirOrderSqlite(this);
		order.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String sql="insert into orders values(?,?,?,?,?,?,?,?,?)";
				Order order=new Order();
				order.setStartdrome(listItem.get("StartDrome"));
				order.setArrivedrome(listItem.get("ArriveDrome"));
				order.setAirlinecode(listItem.get("AirlineCode"));
				order.setStartdate(startDate);
				order.setCompany(listItem.get("Company"));
				order.setStartTime(listItem.get("StartTime"));
				order.setArriveTime(listItem.get("ArriveTime"));
				order.setState("未支付");
				air_db.insert(order, sql);
				finish();
			}
		});
		
	}

}
