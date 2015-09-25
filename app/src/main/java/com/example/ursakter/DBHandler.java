package com.example.ursakter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Jonathan on 2015-03-02.
 */
public class
        DBHandler extends SQLiteOpenHelper {

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
        Cursor cursorExc = db.query(TABLE_EXCUSES, new String[]{"_id", "text", "approvals"}, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
        if(cursorExc != null){
            cursorExc.moveToFirst();
        }

        Excuse newExcuse = new Excuse(Integer.parseInt(cursorExc.getString(0)),cursorExc.getString(1), Integer.parseInt(cursorExc.getString(2)));

        cursorExc.close();

        return newExcuse;
    }

    public ArrayList<Excuse> getAllExcuses(){
        ArrayList<Excuse> allExcuses = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_EXCUSES+";", null);

        if(cursor != null){
            cursor.moveToFirst();

            do{
                Excuse newExcuse = new Excuse(Integer.parseInt(cursor.getString(0)),cursor.getString(1), Integer.parseInt(cursor.getString(2)));
                allExcuses.add(newExcuse);
            }while(cursor.moveToNext());

            cursor.close();

            return allExcuses;
        }

        return null;
    }

    public void updateExcuse(Excuse excuse){
        ContentValues cv = new ContentValues();
        cv.put("approvals", excuse.getApprovals());
        db.update(TABLE_EXCUSES,cv,"_id = ?",new String[]{Integer.toString(excuse.getId())});
    }

    public Category fetchCategory(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_CATEGORY+" WHERE _id = ?;", new String[]{Integer.toString(id)});
        cursor.moveToFirst();
        Category category = new Category(cursor.getInt(0), cursor.getString(1));

        cursor.close();
        return category;
    }

    public ArrayList<Excuse> getExcusesByCategory(int categoryId){
        ArrayList<Excuse> excuses = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT e.*, category.category_name AS category FROM "+TABLE_EXCUSES+" AS e " +
                "LEFT OUTER JOIN excuse2category AS e2c ON e._id = e2c.excuse_id " +
                "LEFT OUTER JOIN "+TABLE_CATEGORY+" ON e2c.category_id = category._id " +
                "WHERE e2c.category_id = ?;", new String[]{Integer.toString(categoryId)});

        if(cursor != null){
            cursor.moveToFirst();

            do{
                Excuse newExcuse = new Excuse(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
                excuses.add(newExcuse);

            }while(cursor.moveToNext());

            cursor.close();

            return excuses;
        }

        return null;
    }

    public int countExcuses(){
        Cursor cursor = db.rawQuery("SELECT _id FROM "+TABLE_EXCUSES+";",null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public ArrayList<Category> fetchCategories(){
        ArrayList<Category> categories = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " ORDER BY category_name;", null);

        if(cursor != null){
            cursor.moveToFirst();
            do{
                Category newCategory = new Category(cursor.getInt(0), cursor.getString(1));
                categories.add(newCategory);
            }while(cursor.moveToNext());

            cursor.close();

            return categories;
        }

        return null;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
