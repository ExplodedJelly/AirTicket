package com.air.edward.airticket.Adapter;

import java.util.List;
import java.util.Map;

import com.air.edward.airticket.DataBase.AirOrderSqlite;
import com.air.edward.airticket.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MySimpleAdapter extends SimpleAdapter {
		private TextView delete;
		private TextView cancle;
		private TextView pay;
		private AirOrderSqlite air_db;
		private Context context;
		private List<Map<String,String>> data;
		
		public MySimpleAdapter(Context context,
				List<Map<String, String>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			this.context=context;
			this.data=data;
			// TODO Auto-generated constructor stub
		}
		
		@Override 
        public int getCount() { 
            // TODO Auto-generated method stub 
            return data.size(); 
        } 
        @Override 
        public Object getItem(int arg0) { 
            // TODO Auto-generated method stub 
            return arg0; 
        } 
        @Override 
        public long getItemId(int arg0) { 
            // TODO Auto-generated method stub 
            return arg0; 
        } 
        
        @SuppressLint("HandlerLeak") private Handler mHandler = new Handler() {
    		@Override
    		public void handleMessage(Message msg) {
    			// TODO Auto-generated method stub
    			super.handleMessage(msg);
    			String id=data.get(msg.arg1).get("id");
    			switch (msg.what) {
    			case 0:
    				String sql = "delete from orders where _id=?";
    				air_db=new AirOrderSqlite(context);
    				air_db.delete(id, sql);
    				sql="select * from orders";
    				List<Map<String,String>> orders=air_db.queryOrder(sql);
    				data.clear();
    				data.addAll(orders);
    				notifyDataSetChanged();// 这个函数很重要，告诉Listview适配器数据发生了变化
    				break;
    			case 1:
    				sql = "update orders set state = '已取消' where _id=?";//修改的SQL语句
    				air_db=new AirOrderSqlite(context);
    				air_db.update(id, sql);
    				sql="select * from orders";
    				orders=air_db.queryOrder(sql);
    				data.clear();
    				data.addAll(orders);
    				notifyDataSetChanged();// 这个函数很重要，告诉Listview适配器数据发生了变化
    				break;
    			case 2:
    				
    				break;
    			}
    		}
    	};
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final int mPosition = position;
			convertView = super.getView(position, convertView, parent);
			delete=(TextView)convertView.findViewById(R.id.delete);
			cancle=(TextView)convertView.findViewById(R.id.cancle);
			pay=(TextView)convertView.findViewById(R.id.pay);
			delete.setClickable(true);
	        delete.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mHandler.obtainMessage(0, mPosition, 0)
					.sendToTarget();
				}
	        	
	        });
	        cancle.setClickable(true);
	        cancle.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mHandler.obtainMessage(1, mPosition, 0)
					.sendToTarget();
				}
	        	
	        });
	        pay.setClickable(true);
	        pay.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mHandler.obtainMessage(2, mPosition, 0)
					.sendToTarget();
				}
	        	
	        });
			return convertView;
		}
	}