
package com.example.myapplication; import android.content.ComponentName;
        import android.content.Intent;
        import android.content.ServiceConnection;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.IBinder;
        import android.os.Message;
        import android.view.View;
        import android.widget.Button;
        import android.widget.SeekBar;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

        import java.text.SimpleDateFormat;
        import java.util.Date;


public class FourActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4;
    Button btn11, btn22, btn33;
    Intent intent;
    SeekBar seekBar;
    TextView textView, textView2;
    MyConnection myConnection;
    Audio.Finder controller;
    public Handler handler = new Handler() {
        public void handleMessage(Message message) {//实时更新进度条
            super.handleMessage(message);
            System.out.println(message.what);
            Update();
        }
    };

    public void Update() {
        int currentTime = controller.getCurrentPosition();//获取当前进度
        seekBar.setProgress(currentTime);//设置进度条
        textView.setText(formatTime(currentTime));//设置显示时间
        handler.sendEmptyMessageDelayed(0, 500);//每隔500ms更新一次
    };

    private String formatTime(int length)//时间转化
    {
        Date date = new Date(length);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String totalTime = sdf.format(date);
        return totalTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_there);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        btn11 = (Button) findViewById(R.id.button);
        btn22 = (Button) findViewById(R.id.button2);
        intent = new Intent(FourActivity .this, Audio.class);
        btn11.setOnClickListener(new mClick());
        btn22.setOnClickListener(new mClick());
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        seekBar.setOnSeekBarChangeListener(new sClick());
        btn1 = this.findViewById(R.id.navigation_one);         //登录按钮
        btn2 = this.findViewById(R.id.navigation_two);
        btn3 = this.findViewById(R.id.navigation_there);
        btn4 = this.findViewById(R.id.navigation_four);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FourActivity .this, TwoActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FourActivity .this, ThereActivity.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FourActivity .this, FourActivity.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FourActivity .this, FiveActivity.class);
                startActivity(intent);
            }
        });
    }

    class sClick implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {//拖动进度条实时监控
            int progress = seekBar.getProgress();//获取当前进度条位置
            seekBar.setProgress(progress);//更新进度条
            controller.setProgress(progress);//更新音乐进度
        }

    }

    class mClick implements View.OnClickListener {
        public void onClick(View view) {
            if (view == btn11) {//开始
                intent.putExtra("play", 1);
                FourActivity .this.startService(intent);//开启服务
                myConnection = new MyConnection();//建立新连接对象
                bindService(intent, myConnection, BIND_AUTO_CREATE);//建立和service连接
            } else if(view == btn22) {//暂停
                intent.putExtra("play", 2);
                FourActivity .this.startService(intent);

            }
        }
    }

    class MyConnection implements ServiceConnection {//控制连接实现mediaPlay的调用

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            controller = (Audio.Finder) service;//获取控制连接对象
            int duration = controller.getDuration();//获取音乐总时长
            textView2.setText(formatTime(duration));//设置总时长
            seekBar.setMax(duration);//设置进度条的最大值
            Update();//提醒进度条更新
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

}



