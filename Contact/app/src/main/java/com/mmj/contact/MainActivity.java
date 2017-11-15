package com.mmj.contact;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Vector;


//for show:main page
public class MainActivity extends AppCompatActivity{
    private DBService dbHelper;
    private Vector vector=new Vector();//创建一个容器，因为数组的长度未知:存放联系人信息
    private SQLiteDatabase db;
    private ArrayAdapter<Vector> adapter;
    private ListView listView;
    private  IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context,Intent intent){
            //refresh
            Toast.makeText(MainActivity.this, "received", Toast.LENGTH_SHORT).show();
            Intent current = getIntent();
            overridePendingTransition(0, 0);
            finish();
            overridePendingTransition(0, 0);
            startActivity(current);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.mmj.contact.LOCAL_BROADCAST");//接收本地广播
        localReceiver=new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
        SetView();
        listView=(ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(MainActivity.this,edit_contact.class);
                intent.putExtra("data2",vector.get(i).toString());
                startActivity(intent);
            }
        });
    }
    public void SetView(){
        dbHelper=new DBService(this,"contact.db",null,2);
        db=dbHelper.getWritableDatabase();//获取数据库
        Cursor cursor=db.query("contact",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Log.d("circle","1111");
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String phone=cursor.getString(cursor.getColumnIndex("phone"));
                String address=cursor.getString(cursor.getColumnIndex("address"));
                String info=name+"\t\t\t\t\t"+phone+"\t\t\t\t\t"+address;
                vector.addElement(info);//一条联系人信息加入容器vector中
            }while(cursor.moveToNext());
        }
        adapter=new ArrayAdapter<Vector>(MainActivity.this,R.layout.list_new,vector);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        //通过调用item.getItemId()来判断菜单项
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this,"You Clicked Add",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,add_contact.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }
    public void onDestroy(){
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}
