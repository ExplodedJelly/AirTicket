package com.air.edward.airticket.Fragment;
import android.app.Activity;
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

import com.air.edward.airticket.Activity.MainActivity;
import com.air.edward.airticket.Activity.SeekListActivity;
import com.air.edward.airticket.Adapter.MySimpleAdapter;
import com.air.edward.airticket.DataBase.AirOrderSqlite;
import com.air.edward.airticket.R;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class UserFragment extends Fragment  {
    private TextView email;
    private TextView address;
    private TextView user_name;
    private TextView phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        user_name=(TextView)view.findViewById(R.id.user_name);
        email=(TextView)view.findViewById(R.id.email);
        phone=(TextView)view.findViewById(R.id.phone);
        address=(TextView)view.findViewById(R.id.address);

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        user_name.setText(sharedPreferences.getString("user_name", ""));
        email.setText(sharedPreferences.getString("email", ""));
        phone.setText(sharedPreferences.getString("phone", ""));
        address.setText(sharedPreferences.getString("address", ""));

        return view;
    }
}