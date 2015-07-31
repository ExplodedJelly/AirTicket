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

import com.air.edward.airticket.Activity.MainActivity;
import com.air.edward.airticket.Activity.SeekListActivity;
import com.air.edward.airticket.R;

import java.util.Calendar;


public class HomeFragment extends Fragment  {

    private EditText beginaddress;
    private EditText endaddress;
    private Button seekbtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        beginaddress=(EditText)view.findViewById(R.id.et_takeoffcity);
        endaddress=(EditText)view.findViewById(R.id.et_arrivecity);
        seekbtn=(Button)view.findViewById(R.id.button_airline_query);

        final EditText et_date = (EditText)view.findViewById(R.id.et_date);
        final Calendar calendar = Calendar.getInstance();

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        et_date.setText(DateFormat.format("yyy-MM-dd", calendar));
                    }
                }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }

        });

        seekbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(getActivity(),SeekListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("beginaddress", beginaddress.getText().toString());
                bundle.putString("endaddress", endaddress.getText().toString());
                bundle.putString("date", et_date.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }
}