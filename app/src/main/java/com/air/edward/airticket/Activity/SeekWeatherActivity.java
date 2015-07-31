package com.air.edward.airticket.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.air.edward.airticket.R;

public class SeekWeatherActivity extends AppCompatActivity{
	
	private EditText city;
	private Button seek;
	Toolbar toolbar = null;
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
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seekweather);
		setupToolbar();
		city=(EditText)findViewById(R.id.city);
		seek=(Button)findViewById(R.id.seek_weather);
		seek.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SeekWeatherActivity.this,ShowWeatherActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("city", city.getText().toString());
				intent.putExtras(bundle);
				startActivity(intent);
			}
			
		});	
	}
}
