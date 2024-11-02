package com.example.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private Integer mProgress;
    static TextView textView;
    private Handler mHandler;
    Boolean bo=true;
    Intent intent = new Intent();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.min) ;
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x111) {
                    textView.setText(mProgress.toString()+"s");           //更新进度
                } else {
                    if(bo==true) {
                        intent.setClass(MainActivity.this, TwoActivity.class);
                        startActivity(intent);
                    }

                }
            }
        };
        new Thread(new Runnable() {
            public void run () {
                mProgress = 6;
                while (true) {//循环获取耗时操作完成的百分比，直到耗时操作结束
                    try {
                        Thread.sleep(1000);                    //线程休眠1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();                //输出异常信息
                    }//获取耗时操作完成的百分比
                    Message m = new Message();            //创建并实例化一个消息对象
                    if (mProgress > 1) {        //当完成进度不到100时表示耗时任务未完成
                        m.what = 0x111;                    //设置代表耗时操作未完成的消息代码
                        mHandler.sendMessage(m);        //发送信息
                    } else {                                //当完成进度到达100时表示耗时操作完成
                        m.what = 0x110;                    //设置代表耗时操作已经完成的消息代码
                        mHandler.sendMessage(m);        //发送消息
                        break;                             //退出循环
                    }
                    mProgress--;
                }
            }
        }).start();
        Button button = (Button) findViewById(R.id.down);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bo=false;
                intent.setClass(MainActivity.this, TwoActivity.class);
                startActivity(intent);
            }

        });
    }
}