package com.mmj.contact;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.data;
import static android.R.attr.excludeFromRecents;
import static android.R.attr.value;

public class edit_contact extends AppCompatActivity implements View.OnClickListener{
    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText addressEdit;
    private DBService dbHelper;//sqlite database helper
    private Button edit,delete;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String data_index;
    private String c[];
    private String tempname;

    private LocalBroadcastManager localBroadcastManager; //for sending broadcast

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        edit=(Button)findViewById(R.id.save2);
        delete=(Button)findViewById(R.id.delete);
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
        nameEdit=(EditText)findViewById(R.id.edit1);
        phoneEdit=(EditText)findViewById(R.id.edit2);
        addressEdit=(EditText)findViewById(R.id.edit3);
        dbHelper=new DBService(this,"contact.db",null,2);
        db=dbHelper.getWritableDatabase();
        Intent intent=getIntent();
        data_index=intent.getStringExtra("data2");
        c = data_index.split("\t\t\t\t\t");
        Log.d("data2",String.valueOf(data_index));
        tempname= c[0];
        cursor=db.rawQuery("select * from CONTACT where name=?",new String[]{String.valueOf(tempname)});
        try{
            if(cursor.moveToFirst()) {
                nameEdit.setText(cursor.getString(cursor.getColumnIndex("NAME")));
                phoneEdit.setText(cursor.getString(cursor.getColumnIndex("PHONE")));
                addressEdit.setText(cursor.getString(cursor.getColumnIndex("ADDRESS")));
            }
        }
        catch (IndexOutOfBoundsException e){
            Log.d("index error","index out of bound");
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.save2:
                String name=nameEdit.getText().toString();
                String phone=phoneEdit.getText().toString();
                String address=addressEdit.getText().toString();
                ContentValues values=new ContentValues();
                values.put("NAME",name);
                values.put("PHONE",phone);
                values.put("ADDRESS",address);
                Log.d("name",String.valueOf(name));
                db.update("CONTACT",values,"name=?",new String[]{String.valueOf(tempname)});//把数据更新成新的
                values.clear();
                localBroadcastManager =LocalBroadcastManager.getInstance(this);
                Intent intent = new Intent("com.mmj.contact.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);
                finish();
                break;
            case R.id.delete:
                db.delete("CONTACT","name=?",new String[]{String.valueOf(tempname)});
                localBroadcastManager =LocalBroadcastManager.getInstance(this);
                Intent intent_delete = new Intent("com.mmj.contact.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent_delete);
                finish();
                break;
        }
    }
}
