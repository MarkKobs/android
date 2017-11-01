package com.mmj.contact;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Vector;

import static android.R.attr.data;

//for show:main page
public class MainActivity extends AppCompatActivity {
    private DBService dbHelper;
    Vector vector=new Vector();//创建一个容器，因为数组的长度未知:存放联系人信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DBService(this,"contact.db",null,2);
        SQLiteDatabase db=dbHelper.getWritableDatabase();//获取数据库
        Cursor cursor=db.query("contact",null,null,null,null,null,null);
        ArrayAdapter<Vector> adapter;
        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex("NAME"));
                String phone=cursor.getString(cursor.getColumnIndex("PHONE"));
                String address=cursor.getString(cursor.getColumnIndex("ADDRESS"));
                String info=name+"\t\t\t\t\t\t"+phone+"\t\t\t\t\t\t"+address;
                vector.addElement(info);//一条联系人信息加入容器vector中
            }while(cursor.moveToNext());
        }
        adapter=new ArrayAdapter<Vector>(MainActivity.this,R.layout.list_new,vector);
        ListView listView=(ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String data=vector.get(i).toString();
                Intent intent =new Intent(MainActivity.this,edit_contact.class);
                intent.putExtra("extra_data",i);//传递i
                startActivity(intent);
            }
        });
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
}
