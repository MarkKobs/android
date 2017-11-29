package com.mmj.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


public class MainActivity extends AppCompatActivity{
    private TextView textView,clock;
    private Button settingButton,deleteButton;
    private static final int msgKey1 = 1;
    private final int DATE_DIALOG = 2;
    private int hclock=0;
    private int mclock=0;//hour minute
    private AlarmManager alarmManager;
    private  android.os.Handler handler= new android.os.Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case msgKey1:
                    textView.setText(getTime());
                    break;
            }
        }
    };
    public void showDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("闹钟");
        builder.setMessage(String.valueOf(hclock)+" : "+String.valueOf(mclock));
        builder.setNeutralButton("返回", null);
        builder.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.local_time);
        clock = (TextView) findViewById(R.id.clock);
        /*1.线程后台处理时间*/
        new TimeThread().start();
        /*2.给button绑定监听器*/
        settingButton = (Button) findViewById(R.id.setting);
        deleteButton = (Button) findViewById(R.id.delete);
        settingButton.setOnClickListener(listener);
        deleteButton.setOnClickListener(listener);
        /*3.设置闹钟,两个参数已提供(hclock,mclock)*/
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);


    }
    public void setClock(){
        if(mclock==0||hclock==0){
            Toast.makeText(this, "设置失败", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Intent intent = new Intent("ELITOR_CLOCK");
            Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min=c.get(Calendar.MINUTE);
            int second=c.get(Calendar.SECOND);
            int interval=(hclock-hour)*60*60+(mclock-min)*60+(0-second);//单位为秒
            if(interval>0){
                Toast.makeText(this, "还剩"+String.valueOf(hclock-hour)+" : "+String.valueOf(mclock-min-1)+" : "+String.valueOf(60-second)+"执行", Toast.LENGTH_LONG).show();
                intent.putExtra("msg","闹铃开始" );
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 2 * 1000, pi);
            }
            else{
                Toast.makeText(this,"闹钟设置有误",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void showTimeDialog(){
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                hclock=hour;
                mclock=min;
                clock.setText("闹钟:"+String.valueOf(hclock)+":"+String.valueOf(mclock));
                setClock();//闹钟时间设置
            }
        },0,0,true);//true表示使用24小时制
        timePickerDialog.show();
    }
    public void CancelClock(){
        Intent intent=new Intent("ELITOR_CLOCK");
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(pi);
        clock.setText("暂未设置");
        Toast.makeText(getApplicationContext(),"闹钟取消成功",Toast.LENGTH_LONG);
    }
    private View.OnClickListener listener=new View.OnClickListener() { //click listener
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.setting:
                    showTimeDialog();
                    break;
                case R.id.delete:
                    CancelClock();
                    break;
            }
        }
    };
    public class TimeThread extends Thread {
        @Override
        public void run () {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    handler.sendMessage(msg);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(true);
        }
    }

    //获得当前年月日时分秒星期
    public String getTime(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        String mHour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));//时
        String mMinute = String.valueOf(c.get(Calendar.MINUTE));//分
        String mSecond = String.valueOf(c.get(Calendar.SECOND));//秒

        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return mYear + "年" + mMonth + "月" + mDay+"日"+"  "+"星期"+mWay+"  "+mHour+":"+mMinute+":"+mSecond;
    }

}
