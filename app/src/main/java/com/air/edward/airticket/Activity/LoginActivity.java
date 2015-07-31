package com.air.edward.airticket.Activity;

import java.util.Map;

import com.air.edward.airticket.R;
import com.air.edward.airticket.DataBase.AirOrderSqlite;
import com.air.edward.airticket.Object.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button submit;
    private Button register;
    private AirOrderSqlite air_db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        air_db=new AirOrderSqlite(this);


        password=(EditText)findViewById(R.id.et_loginpassword);
        email=(EditText)findViewById(R.id.et_loginname);
        submit=(Button)findViewById(R.id.button_login);
        register=(Button)findViewById(R.id.button_register);

        submit.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String eml=email.getText().toString();
                String psd=password.getText().toString();
                if (eml.equals("") || psd.equals("")) {
                    new AlertDialog.Builder(LoginActivity.this).setTitle("错误")
                            .setMessage("账户或密码不能为空").setPositiveButton("确定", null)
                            .show();
                }
                else {
                    String sql="select * from user where email=? and password=?";
                    User loginer=new User();
                    loginer.setEmail(eml);
                    loginer.setPassword(psd);
                    Map<String,String> user=air_db.query(loginer,sql);
                    if(user.isEmpty()) {
                        new AlertDialog.Builder(LoginActivity.this).setTitle("错误")
                                .setMessage("账户或密码错误").setPositiveButton("确定", null)
                                .show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_LONG).show();
                           SharedPreferences mySharedPreferences= getSharedPreferences("test",
                                Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        editor.putString("user_name", user.get("user_name"));
                        editor.putString("email", user.get("email"));
                        editor.putString("phone", user.get("phone"));
                        editor.putString("address", user.get("address"));
                        editor.commit();
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}