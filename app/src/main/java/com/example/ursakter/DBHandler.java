package com.example.ursakter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.sql.SQLException;

/**
 * Created by Jonathan on 2015-03-02.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "excusesDB";
    private static final String DB_PATH = "/data/data/com.example.ursakter/databases/";
    private static final String TABLE_EXCUSES = "excuses";
    private static final String TABLE_CATEGORY = "category";
    private static final String TABLE_EXCUSES2CATEGORY = "excuses2category";

    private SQLiteDatabase db;
    private final Context mContext;

    public DBHandler(Context context){
        super(context, DB_NAME, null, 1);
        this.mContext = context;

    }

    public void createDB() throws IOException{
        boolean dbExists = checkDatabase();

        if(!dbExists){
            this.getReadableDatabase();
            copyDB();
        }
    }

    private boolean checkDatabase(){
        //SQLiteDatabase checkDB = null;
        //String path = DB_PATH+DB_NAME;

        File dbFile = mContext.getDatabasePath(DB_NAME);

        //checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);


        /*if(checkDB != null){
            checkDB.close();
        }*/

        return dbFile.exists();

        //return checkDB != null ? true : false;
    }

    private void copyDB(){
        InputStream iStream = null;
        OutputStream oStream = null;

        try {
            iStream = mContext.getAssets().open(DB_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            oStream = new FileOutputStream(DB_PATH+DB_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[1024];
        int length;

        if(iStream != null && oStream != null){
            try {
                while((length = iStream.read(buffer))>0){
                    oStream.write(buffer,0,length);
                }
                oStream.flush();
                oStream.close();
                iStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openDatabase() throws SQLException{
        String path = DB_PATH+DB_NAME;

        try{
            db = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public synchronized void close(){
        if(db != null){
            db.close();
        }

        super.close();
    }

    public Excuse getExcuse(int id){
        Cursor cursorExc = db.query(TABLE_EXCUSES, new String[]{"_id","text","approvals"}, "_id=?", new String[]{String.valueOf(id)},null,null,null);
        if(cursorExc != null){
            cursorExc.moveToFirst();
        }

        Excuse newExcuse = new Excuse(Integer.parseInt(cursorExc.getString(0)),cursorExc.getString(1), Integer.parseInt(cursorExc.getString(2)));

        return newExcuse;
    }

    public void updateExcuse(Excuse excuse){
        ContentValues cv = new ContentValues();
        cv.put("approvals",excuse.getApprovals());
        db.update(TABLE_EXCUSES,cv,"_id = ?",new String[]{Integer.toString(excuse.getId())});
    }

    private Array fetchCategories(int id){

        return null;
    }

    public int countExcuses(){
        Cursor cursor = db.rawQuery("SELECT _id FROM "+TABLE_EXCUSES+";",null);

        return cursor.getCount();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
