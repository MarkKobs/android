package com.mmj.localservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void btn1_click(View view){
        Intent i = new Intent("android.broadcast.TOAST");
        sendBroadcast(i);
    }
    public void btn2_click(View view){
        Intent i=new Intent("android.broadcast.NOTIFICATION");
        sendBroadcast(i);
    }
    public void btn3_click(View view){
        Intent i =new Intent("android.broadcast.ACTIVITY");
        sendBroadcast(i);
    }
}
