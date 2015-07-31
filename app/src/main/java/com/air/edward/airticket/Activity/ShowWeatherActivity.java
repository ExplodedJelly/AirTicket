package com.air.edward.airticket.Activity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.air.edward.airticket.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowWeatherActivity extends AppCompatActivity{

    private TextView cityname;
    private ListView weatherlist;
    private String city;
    private List<Map<String,String>> weather_list;
    Toolbar toolbar;

    //鍛藉悕绌洪棿
    private static final String serviceNameSpace="http://WebXml.com.cn/";
    // WSDL鏂囨。涓殑URL
    private static final String url = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
    //璋冪敤鏂规硶
    private static final String getDomesticAirlinesTime="getWeather";
    static final String soapActionAirlinesTime = "http://WebXml.com.cn/getWeather";

    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what==0){
                cityname.setText(city);
                SimpleAdapter simpleAdapter = new SimpleAdapter(ShowWeatherActivity.this, weather_list, R.layout.weathershow_item,
                        new String[] {"date","temperature","wind"}, new int[]{R.id.date,R.id.temperature,R.id.wind});
                weatherlist.setAdapter(simpleAdapter);
            }
        }
    };
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        setContentView(R.layout.showweather);
        setupToolbar();

        cityname=(TextView)findViewById(R.id.cityname);
        weatherlist=(ListView)findViewById(R.id.wetherlist);
        Bundle bundle=this.getIntent().getExtras();
        city=bundle.getString("city");
        new Thread(new Runnable(){
            public void run(){
                seekThread();
            }
        }).start();
    }

    public void seekThread(){
        //瀹炰緥鍖朣oapObject瀵硅薄
        SoapObject soapObject=new SoapObject(serviceNameSpace,getDomesticAirlinesTime);
        //杈撳叆鏌ヨ鍙傛暟
        soapObject.addProperty("theCityCode", city);
        ///鍏嶈垂鐢ㄦ埛userid涓虹┖
        soapObject.addProperty("theUserID", "b84abff5c395474d9aa7df234843619d");
        // 閫氳繃SOAP1.1鍗忚寰楀埌envelop瀵硅薄
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        // 灏唖oapObject瀵硅薄璁剧疆涓篹nvelop瀵硅薄锛屼紶鍑烘秷鎭?

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        try
        {
            httpTransportSE.call(soapActionAirlinesTime, envelope);
            System.out.println("脳脳3脳脳璋冪敤webservice鏈嶅姟鎴愬姛");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("脳脳4脳脳璋冪敤webservice鏈嶅姟澶辫触");
        };

        if(envelope.bodyIn instanceof SoapFault){
            String str=((SoapFault)envelope.bodyIn).faultstring;
            System.out.println(str);

        }
        else{
            SoapObject resultObj = (SoapObject) envelope.bodyIn;
            SoapObject soap = (SoapObject)resultObj.getProperty(0);
            city=soap.getProperty(0).toString();
            weather_list=new ArrayList<Map<String,String>>();
            for(int i=7;i<42;){
                Map<String,String> day=new HashMap<String,String>();
                day.put("date", soap.getProperty(i).toString());
                day.put("temperature", soap.getProperty(i+1).toString());
                day.put("wind", soap.getProperty(i+2).toString());
                weather_list.add(day);
                i+=5;
            }
            System.out.println("脳脳5脳脳鑾峰緱鏈嶅姟澶╂皵鏁版嵁鎴愬姛");
            handler.sendEmptyMessage(0);
        }
    }

}
