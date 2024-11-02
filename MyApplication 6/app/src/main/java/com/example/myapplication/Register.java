
package com.example.myapplication;



import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText usename,usepwd,usepwd2;
    Button submit,submit2;
    Mysql mysql;
    SQLiteDatabase db;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usename = this.findViewById(R.id.usename);			    //用户名编辑框
        usepwd =  this.findViewById(R.id.usepwd);				//设置初始密码编辑框
        submit =   this.findViewById(R.id.submit);
        submit2 =   this.findViewById(R.id.submit2);	//注册按钮
        mysql = new Mysql(this,"Userinfo",null,1);      //建数据库
        db = mysql.getReadableDatabase();
        sp = this.getSharedPreferences("useinfo",this.MODE_PRIVATE);

        submit2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Register.this, TwoActivity.class);
                startActivity(intent);
            }});
        submit.setOnClickListener(new View.OnClickListener() {
            boolean flag = true;            //判断用户是否已存在的标志位
            @Override
            public void onClick(View v) {
                String name = usename.getText().toString();				//用户名
                String pwd01 = usepwd.getText().toString();				//密码//二次输入的密码
                String sex = "";										//性别
                if(name.equals("")||pwd01 .equals("")){
                    Toast.makeText(Register.this, "用户名或密码不能为空!！", Toast.LENGTH_LONG).show();
                }
                else{
                    Cursor cursor = db.query("logins",new String[]{"usname"},null,null,null,null,null);

                    while (cursor.moveToNext()){
                        if(cursor.getString(0).equals(name)){
                            flag = false;
                            break;
                        }
                    }
                    if(flag==true){                                             //判断用户是否已存在	                        //判断两次输入的密码是否一致，若一致则继续，不一致则提醒密码不一致
                        ContentValues cv = new ContentValues();
                        cv.put("usname",name);
                        cv.put("uspwd",pwd01);
                        db.insert("logins",null,cv);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("usname",name);
                        editor.putString("uspwd",pwd01);
                        editor.commit();
                        Intent intent = new Intent();
                        intent.setClass(Register.this,TwoActivity.class);      //跳转到登录页面
                        startActivity(intent);
                        Toast.makeText(Register.this, "注册成功！", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(Register.this, "用户已存在！", Toast.LENGTH_LONG).show();			//提示密码不一致
                    }

                }
            }


        });
    }
}