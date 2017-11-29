package com.mmj.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyTag", "onclock......................");
        String msg = intent.getStringExtra("msg");
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        Intent clockintent=new Intent(context,clock.class);
        context.startActivity(clockintent);
    }

}
