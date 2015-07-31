package com.air.edward.airticket.Fragment;
import android.app.DatePickerDialog;
import android.content.Intent;
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

import com.air.edward.airticket.Activity.MainActivity;
import com.air.edward.airticket.Activity.SeekListActivity;
import com.air.edward.airticket.Adapter.MySimpleAdapter;
import com.air.edward.airticket.DataBase.AirOrderSqlite;
import com.air.edward.airticket.R;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class OrderFragment extends Fragment  {

    private ListView listview;
    public List<Map<String,String>> orders;
    private AirOrderSqlite air_db;
    public MySimpleAdapter simpleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        air_db=new AirOrderSqlite(getActivity());
        String sql = "select * from orders";
        orders=air_db.queryOrder(sql);
        listview=(ListView)view.findViewById(R.id.order_list);

        simpleAdapter = new MySimpleAdapter(getActivity(), orders, R.layout.order_list_item,
                new String[] {"id","stardrome","arrivedrome","airlinecode","date","company","startTime","arriveTime","state"}, new int[]{R.id.number,R.id.startdrome,R.id.arrivedrome,R.id.airlinecode,R.id.date,R.id.company,R.id.startTime,R.id.arriveTime,R.id.state});
        listview.setAdapter(simpleAdapter);

        return view;
    }
}