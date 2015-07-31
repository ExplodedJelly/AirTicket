package com.air.edward.airticket.Activity;

import com.air.edward.airticket.R;
import com.air.edward.airticket.DataBase.AirOrderSqlite;
import com.air.edward.airticket.Object.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText user_name;
    private EditText password;
    private EditText password_again;
    private EditText email;
    private EditText phone;
    private EditText address;
    private Button submit;

    private AirOrderSqlite air_db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        air_db=new AirOrderSqlite(this);

        user_name=(EditText)findViewById(R.id.et_registername);
        password=(EditText)findViewById(R.id.et_registerpassword1);
        password_again=(EditText)findViewById(R.id.et_registerpassword2);
        email=(EditText)findViewById(R.id.et_email);
        phone=(EditText)findViewById(R.id.et_phone);
        address=(EditText)findViewById(R.id.et_address);
        submit=(Button)findViewById(R.id.button_register_done);
        submit.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name=user_name.getText().toString();
                String psd=password.getText().toString();
                String psda=password_again.getText().toString();
                String eml=email.getText().toString();
                String pho=phone.getText().toString();
                String ads=address.getText().toString();
                if(psd.equals(psda)){
                    String sql = "insert into user values(?,?,?,?,?,?)";
                    User user=new User();
                    user.setUser_name(name);
                    user.setPassword(psd);
                    user.setEmail(eml);
                    user.setPhone(pho);
                    user.setAddress(ads);
                    air_db.insert(user,sql);
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }

        });
    }

}
