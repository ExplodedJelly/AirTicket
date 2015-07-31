package com.air.edward.airticket.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.air.edward.airticket.R;

public class SeekListActivity extends AppCompatActivity {
	private String beginaddress;
	private String endaddress;
	private String date;
	//命名空间
    private static final String serviceNameSpace="http://WebXml.com.cn/";
    // WSDL文档中的URL  
    private static final String url = "http://webservice.webxml.com.cn/webservices/DomesticAirline.asmx";
    //调用方法
    private static final String getDomesticAirlinesTime="getDomesticAirlinesTime";
    static final String soapActionAirlinesTime = "http://WebXml.com.cn/getDomesticAirlinesTime";
    public static List<Map<String,String>> listItems;
    private ListView listview;
	Toolbar toolbar = null;
	
	Handler handler = new Handler() {  
        public void handleMessage(Message msg) {
        	if(msg.what==0){
        		listview = (ListView)findViewById(R.id.seek_list);
        		SimpleAdapter simpleAdapter = new SimpleAdapter(SeekListActivity.this, listItems, R.layout.seek_list_item,
                        new String[] {"Company","AirlineCode","StartDrome","ArriveDrome","StartTime","ArriveTime"}, new int[]{R.id.Company,R.id.Airlinecode,R.id.StartDrome,R.id.ArriveDrome,R.id.StartTime,R.id.ArriveTime});  
                listview.setAdapter(simpleAdapter);
        	    listview.setOnItemClickListener(new OnItemClickListener() {  
        	        @Override  
        	        public void onItemClick(AdapterView<?> adapterView, View view, int position,  
        	            long id) { 
        	        	Toast.makeText(SeekListActivity.this,"您选择了标题："+listItems.get(position).get("Company"), Toast.LENGTH_LONG).show();  
        	        	Intent intent=new Intent(SeekListActivity.this,SelectedAirActivity.class);
        	        	Bundle bundle=new Bundle();
        	        	bundle.putInt("position", position);
        	        	bundle.putString("date", date);
        	        	System.out.println("****"+date);
        	        	intent.putExtras(bundle);
        	        	System.out.println(listItems.get(position).get("Company"));
        	        	startActivity(intent);
        	        }
        	    });  
        	}  
        }  
    };
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
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.seek_list);
        setupToolbar();
        listItems=new ArrayList<Map<String,String>>();
		Bundle bundle=this.getIntent().getExtras();
		beginaddress=bundle.getString("beginaddress");
		endaddress=bundle.getString("endaddress");
		date=bundle.getString("date");
		Toast.makeText(SeekListActivity.this,"您选择了标题："+beginaddress+endaddress+date, Toast.LENGTH_LONG).show();
		new Thread(new Runnable(){
			public void run(){
				seekThread();
			}
		}).start();	
	}
	
	public void seekThread(){
		//实例化SoapObject对象
        SoapObject soapObject=new SoapObject(serviceNameSpace,getDomesticAirlinesTime);
        //输入查询参数
        soapObject.addProperty("startCity", beginaddress);  
        soapObject.addProperty("lastCity", endaddress);
        soapObject.addProperty("theDate",date);
        ///免费用户userid为空  
        soapObject.addProperty("userId", "");
        // 通过SOAP1.1协议得到envelop对象
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        // 将soapObject对象设置为envelop对象，传出消息 
        
        envelope.bodyOut = soapObject;  
        envelope.dotNet = true;       
        HttpTransportSE httpTransportSE = new HttpTransportSE(url);  
        
        try   
        {  
            httpTransportSE.call(soapActionAirlinesTime, envelope); 
            System.out.println("××3××调用webservice服务成功");  
        }   
        catch (Exception e)   
        {  
            e.printStackTrace();  
            System.out.println("××4××调用webservice服务失败");  
        };
        
        if(envelope.bodyIn instanceof SoapFault){ 
            String str=((SoapFault)envelope.bodyIn).faultstring;
            System.out.println(str); 
             
        } 
        else{ 
        	SoapObject resultObj = (SoapObject) envelope.bodyIn;  
        	SoapObject soap = (SoapObject)resultObj.getProperty(0);
        	SoapObject soap1=(SoapObject)soap.getProperty(1);
        	System.out.println(soap1.getProperty(0).toString());
        	SoapObject soap2=(SoapObject)soap1.getProperty(0); 
        	int count = soap2.getPropertyCount();  
        	for (int i = 0; i < count; i++) { 
        		SoapObject soap3=(SoapObject)soap2.getProperty(i); 
        		Map<String,String> listItem = new HashMap<String, String>();  
        		listItem.put("Company", soap3.getProperty(0).toString());
        		listItem.put("AirlineCode", soap3.getProperty(1).toString());
        		listItem.put("StartDrome", soap3.getProperty(2).toString());
        		listItem.put("ArriveDrome", soap3.getProperty(3).toString());
        		listItem.put("StartTime", soap3.getProperty(4).toString());
        		listItem.put("ArriveTime", soap3.getProperty(5).toString());
        		listItems.add(listItem); 
        	}
        	System.out.println("××5××获得服务数据成功");  
            handler.sendEmptyMessage(0);
        }
	}
}
