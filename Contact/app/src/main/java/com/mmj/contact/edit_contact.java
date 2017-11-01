package com.mmj.contact;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.provider.Settings;
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
    private int data_index;
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
        data_index=intent.getIntExtra("extra_data",4)+1;//from 0 starting , index+1 :change to starting from 1
        Log.d("extra_data",String.valueOf(data_index));
        cursor=db.rawQuery("select * from CONTACT where ID=?",new String[]{String.valueOf(data_index)});
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
                db.update("CONTACT",values,"ID=?",new String[]{String.valueOf(data_index)});//把数据更新成新的
                Toast.makeText(this, "edited", Toast.LENGTH_SHORT).show();
                values.clear();
                Intent intent=new Intent(edit_contact.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.delete:
                db.delete("CONTACT","ID=?",new String[]{String.valueOf(data_index)});
                Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(edit_contact.this,MainActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
}
