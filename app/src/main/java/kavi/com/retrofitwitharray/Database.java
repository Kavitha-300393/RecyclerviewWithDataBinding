package kavi.com.retrofitwitharray;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Database extends SQLiteOpenHelper {
    public Database(Context applicationcontext) {
        super(applicationcontext, "kavi.db", null, 1);
    }
    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE taskli(  name TEXT,priceUsd TEXT,changePercent24Hr TEXT, symbol TEXT)";


        database.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS taskli";
        database.execSQL(query);

        onCreate(database);
    }
    /**
     * Inserts User into SQLite DB
    // * @param queryValues
     * @param
     */
    public boolean insertUser(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", queryValues.get("name"));
       values.put("priceUsd",queryValues.get("priceUsd"));
        values.put("changePercent24Hr",queryValues.get("changePercent24Hr"));
        values.put("symbol",queryValues.get("symbol"));



        long result=database.insert("taskli", null, values);
        if(result==-1)
            return false;
        else
            return true;
    }

    public ArrayList<AndroidVersion> getAllUserlist(){
        ArrayList<AndroidVersion> lists=new ArrayList<>();
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from taskli",null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String priceUsd = cursor.getString(1);
                String changePercent24Hr=cursor.getString(2);
                String symbol= cursor.getString(3);

             AndroidVersion task=new AndroidVersion(name,priceUsd,changePercent24Hr,symbol);
                lists.add(task);
            }while (cursor.moveToNext());
        }
        return lists;
    }
    public Integer truncateTable()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        db.delete("taskli", "1", null);
        db.close();
        //String toast=null;
        //toast("Table Data Truncated!");
        return 1;
    }
}
