package com.air.edward.airticket.Fragment;
import android.app.Activity;
import android.app.AlertDialog;

import com.air.edward.airticket.Activity.LoginActivity;
import com.air.edward.airticket.Object.*;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.air.edward.airticket.Activity.MainActivity;
import com.air.edward.airticket.Activity.SeekListActivity;
import com.air.edward.airticket.Activity.SeekWeatherActivity;
import com.air.edward.airticket.Adapter.MySimpleAdapter;
import com.air.edward.airticket.DataBase.AirOrderSqlite;
import com.air.edward.airticket.Object.User;
import com.air.edward.airticket.R;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class HelperFragment extends Fragment  {
    private TextView drome;
    private EditText airport;
    private TextView bus;
    private TextView tip;
    private Button btn;
    private Button seek_weather;
    private String airport_name;

    private AirOrderSqlite air_db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_helper, container, false);
        airport=(EditText)view.findViewById(R.id.airport);
        drome=(TextView)view.findViewById(R.id.drome);
        bus=(TextView)view.findViewById(R.id.bus);
        tip=(TextView)view.findViewById(R.id.tip);
        btn=(Button)view.findViewById(R.id.button);
        seek_weather=(Button)view.findViewById(R.id.seek_weather);

        airport_name=airport.getText().toString();

        air_db = new AirOrderSqlite(getActivity());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql="select * from help where airport=?";
                Help help=new Help();
                help.setAirport(airport_name);
                Map<String,String> helper=air_db.query(help,sql);
                if(helper.isEmpty()) {
                    new AlertDialog.Builder(getActivity()).setTitle("错误")
                            .setMessage("该机场不存在").setPositiveButton("确定", null)
                            .show();
                }
                else{
                    Toast.makeText(getActivity(),"查询成功", Toast.LENGTH_LONG).show();
                    drome.setText(helper.get("drome"));
                    bus.setText(helper.get("bus"));
                    tip.setText(helper.get("tip"));
                }
            }
        });

        seek_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SeekWeatherActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}