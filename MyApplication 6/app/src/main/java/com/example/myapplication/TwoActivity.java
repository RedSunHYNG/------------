package com.example.myapplication;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TwoActivity extends AppCompatActivity {
    Button btn1,btn2,btn3,btn4,btn5;
    TextView text0;
    EditText text1,text2,text3,text4,text5,text6,text7,text8,text9,text10;
    private  final  String FILE_NAME = "alls";
    private SharedPreferences mPreferences;
    private SharedPreferences alls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        text0 = this.findViewById(R.id.text_0);
        text1 = this.findViewById(R.id.text_1);
        text2 = this.findViewById(R.id.text_2);
        text3 = this.findViewById(R.id.text_3);
        text4 = this.findViewById(R.id.text_4);
        text5 = this.findViewById(R.id.text_5);
        text6 = this.findViewById(R.id.text_6);
        text7 = this.findViewById(R.id.text_7);
        text8 = this.findViewById(R.id.text_8);
        text9 = this.findViewById(R.id.text_9);
        text10 = this.findViewById(R.id.text_10);
        btn1 = this.findViewById(R.id.navigation_one);         //登录按钮
        btn2 = this.findViewById(R.id.navigation_two);
        btn3 = this.findViewById(R.id.navigation_there);
        btn4 = this.findViewById(R.id.navigation_four);
        btn5 = this.findViewById(R.id.bottom_btn39);
        mPreferences = getSharedPreferences("demoshareprefeces",MODE_PRIVATE);
        int value = mPreferences.getInt("value",0);
        initWidge();
        if(value!=0){
            try {
                FileInputStream fis = openFileInput(FILE_NAME);
                DataInputStream dis = new DataInputStream(fis);
                String turn =dis.readLine();
                dis.close();
                fis.close();
                System.out.println(turn);
                text0.setText(turn);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(TwoActivity.this, TwoActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(TwoActivity.this, ThereActivity.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(TwoActivity.this, FourActivity.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(TwoActivity.this, FiveActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initWidge() {
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreferences = getSharedPreferences("demoshareprefeces",MODE_PRIVATE);
                int value = mPreferences.getInt("value",0);
                if(value !=0){
                    String Text1=text1.getText().toString();
                    String Text2=text2.getText().toString();
                    String Text3=text3.getText().toString();
                    String Text4=text4.getText().toString();
                    String Text5=text5.getText().toString();
                    String Text6=text6.getText().toString();
                    String Text7=text7.getText().toString();
                    String Text8=text8.getText().toString();
                    String Text9=text9.getText().toString();
                    String Text10=text10.getText().toString();
                    Alls alls1= new Alls(Text1,Text2,Text3,Text4,Text5,Text6,Text7,Text8,Text9,Text10);
                    write(alls1);
                }else {
                    Toast.makeText(TwoActivity.this,"用户未登录！", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void write(Alls alls1)
    {
        try{
            FileOutputStream fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
            fos.write(String.valueOf(alls1).getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class Alls {
    private String Text1;
    private String Text2;
    private String Text3;
    private String Text4;
    private String Text5;
    private String Text6;
    private String Text7;
    private String Text8;
    private String Text9;
    private String Text10;
    public Alls(String Text1, String Text2, String Text3,String Text4,String Text5,String Text6,String Text7,String Text8,String Text9,String Text10)
    {
        this.Text1 = Text1;
        this.Text2 = Text2;
        this.Text3 = Text3;
        this.Text4 = Text4;
        this.Text5 = Text5;
        this.Text6 = Text6;
        this.Text7 = Text7;
        this.Text8 = Text8;
        this.Text9 = Text9;
        this.Text10 = Text10;
    }
    public String toString()
    {
        return "Text1:" + Text1 + "; Text2: " + Text2+ "; Text3: " + Text3+ "; Text4: " + Text4+ "; Text5: " + Text5+ "; Text6: " + Text6+ "; Text7: " + Text7+ "; Text8: " + Text8+ "; Text9: " + Text9+ "; Text10: " + Text10;
    }
}