package com.mmj.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.button;
import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity implements BatteryReceiver.myListener{
    private BatteryReceiver batterybroadcast;
    private TextView batterLevel;
    private Button button, b2;
    /**
     * 这是兼容的 AlertDialog
     */
    public void showDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("统计显示电池电量");
        int level=batterybroadcast.getLevel();
        builder.setMessage(level+"%");
        builder.setNeutralButton("返回", null);
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        batterLevel=(TextView)findViewById(R.id.battery);
        button=(Button)findViewById(R.id.showbutton);
        b2=(Button)findViewById(R.id.toast);
        batterybroadcast=new BatteryReceiver();//创建广播对象
        IntentFilter intentfilter=new IntentFilter();//过滤器
        intentfilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batterybroadcast,intentfilter);//bind绑定
        batterybroadcast.setmyListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.showbutton) {
                    MainActivity.this.showDialog();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.toast){
                    Toast.makeText(MainActivity.this,"battery level:"+batterybroadcast.getLevel()+"%",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(batterybroadcast);//要手动将绑定解除
    }
    @Override//because implements BatteryReceiver.myListener
    public void onListener(String level){
        batterLevel.setText("当前电量"+level+"%");
    }

}
