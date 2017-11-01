package com.mmj.contact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by mk on 2017/10/30.
 */

public class DBService extends SQLiteOpenHelper {
    public static final String CREATE_CONTACT="create table if not exists contact(" +
            "id integer primary key autoincrement," +
            "name text," +
            "phone text," +
            "address text)";
    public static final String INIT_TABLE="insert into contact (name,phone,address) values ('john','13584554308','jiangsu')";
    private Context mContext;
    public DBService(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_CONTACT);//创建联系人列表
        Toast.makeText(mContext, "table Contact has been initialized", Toast.LENGTH_SHORT).show();
    }
    public void onUpgrade(SQLiteDatabase db,int i, int j){
    }
}
