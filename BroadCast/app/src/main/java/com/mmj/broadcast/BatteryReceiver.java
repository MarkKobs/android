package com.mmj.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BatteryReceiver extends BroadcastReceiver {
    public myListener mylistener;
    private int level;
    @Override
    public void onReceive(Context context, Intent intent){
        this.level=intent.getIntExtra("level",0);//获取当前电量
        mylistener.onListener(this.level+""); //convert int to string
        System.out.println(this.level+"%");
    }
    interface myListener{//抽象函数
        void onListener(String level);
    }
    public void setmyListener(myListener mylistener){
        this.mylistener=mylistener;
    }
    public int getLevel(){
        return level;
    }
}