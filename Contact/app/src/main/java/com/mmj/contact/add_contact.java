package com.mmj.contact;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Administrator on 2017/10/30.
 */

public class add_contact extends AppCompatActivity { //add contactors' activity
    private Button savebutton;
    private EditText edit1,edit2,edit3;
    private DBService dbHelper;//sqlite database helper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        dbHelper=new DBService(this,"contact.db",null,2);
        savebutton=(Button)findViewById(R.id.save);
        edit1=(EditText)findViewById(R.id.e1);//for name
        edit2=(EditText)findViewById(R.id.e2);//for phone
        edit3=(EditText)findViewById(R.id.e3);//for address
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.save){
                    String name=edit1.getText().toString();
                    String phone=edit2.getText().toString();
                    String address=edit3.getText().toString();
                    SQLiteDatabase db=dbHelper.getWritableDatabase();
                    ContentValues values=new ContentValues();
                    values.put("NAME",name);
                    values.put("PHONE",phone);
                    values.put("ADDRESS",address);
                    db.insert("contact",null,values);
                    values.clear();
                    Intent intent=new Intent(add_contact.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
