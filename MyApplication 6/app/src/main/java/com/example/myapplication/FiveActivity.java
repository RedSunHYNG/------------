package com.example.myapplication;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class FiveActivity extends AppCompatActivity {
    private  SharedPreferences mPreferences;
    EditText name,pwd;
    Button btnlogin,btnreg,btn1,btn2,btn3,btn4,btn5;
    Mysql mysql;
    SQLiteDatabase db;
    SharedPreferences sp,sp1,sp2;
    TextView showhello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPreferences = getSharedPreferences("demoshareprefeces",MODE_PRIVATE);
        int value = mPreferences.getInt("value",0);
        super.onCreate(savedInstanceState);
        if(value==0){
            setContentView(R.layout.activity_four);
        }else {
            setContentView(R.layout.activity_four_two);
            sp = this.getSharedPreferences("username", this.MODE_PRIVATE);  //获取sharepreferences
            showhello = this.findViewById(R.id.word);           //显示欢迎
            showhello.setText("欢迎你！"+sp.getString("Loginname",""));     //获取用户名
        }
        name = this.findViewById(R.id.name);            //用户名输入框
        pwd = this.findViewById(R.id.pwd);              //密码输入框
        btnlogin = this.findViewById(R.id.login);         //登录按钮
        btnreg = this.findViewById(R.id.reg);               //注册按钮
        btn1 = this.findViewById(R.id.navigation_one);
        btn2 = this.findViewById(R.id.navigation_two);
        btn3 = this.findViewById(R.id.navigation_there);
        btn4 = this.findViewById(R.id.navigation_four);
        btn5= this.findViewById(R.id.unlogin);
        sp1 =  this.getSharedPreferences("useinfo",this.MODE_PRIVATE);
        sp2 = this.getSharedPreferences("username",this.MODE_PRIVATE);
        name.setText(sp1.getString("usname",null));
        pwd.setText(sp1.getString("uspwd",null));
        mysql = new Mysql(this,"Userinfo",null,1);      //建数据库或者取数据库
        db = mysql.getReadableDatabase();
        btnlogin.setOnClickListener(new View.OnClickListener() {                //登录事件
            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
                String password = pwd.getText().toString();                 //获取用户输入的用户名和密码
                //查询用户名和密码相同的数据
                Cursor cursor = db.query("logins",new String[]{"usname","uspwd"}," usname=? and uspwd=?",new String[]{username,password},null,null,null);
                int flag = cursor.getCount();                            //查询出来的记录项的条数，若没有该用户则为0条
                if(flag!=0){                                            //若查询出的记录不为0，则进行跳转操作
                    Intent intent = new Intent();
                    intent.setClass(FiveActivity.this, FiveActivity.class);            //设置页面跳转
                    SharedPreferences.Editor editor = sp2.edit();
                    SharedPreferences.Editor editor2 = mPreferences.edit();
                    cursor.moveToFirst();                                   //将光标移动到position为0的位置，默认位置为-1
                    String loginname = cursor.getString(0);
                    editor.putString("Loginname",loginname);
                    int value = 1;
                    editor2.putInt("value",value);
                    editor.commit();
                    editor2.commit();
                    startActivity(intent);

                }
                else{
                    Toast.makeText(FiveActivity.this,"用户名或密码错误！",Toast.LENGTH_LONG).show();             //提示用户信息错误或没有账号
                }

            }
        });
        btnreg.setOnClickListener(new View.OnClickListener() {                  //注册事件
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FiveActivity.this,Register.class);          //跳转到注册页面
                startActivity(intent);
                Toast.makeText(FiveActivity.this,"前往注册！",Toast.LENGTH_SHORT).show();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FiveActivity.this, TwoActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FiveActivity.this, ThereActivity.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FiveActivity.this, FourActivity.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FiveActivity.this, FiveActivity.class);
                startActivity(intent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor2 = mPreferences.edit();
                int value = 0;
                editor2.putInt("value",value);
                editor2.commit();
                Intent intent = new Intent();
                intent.setClass(FiveActivity.this, FiveActivity.class);
                startActivity(intent);
            }
        });
    }
}


