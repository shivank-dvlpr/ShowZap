package app.shivank.showzap.wallpapershd;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehandler extends SQLiteOpenHelper {
    // All Static variables
// Database Version
    private static final int DATABASE_VERSION = 1;
    static SQLiteDatabase db;

    // Database Name
    private static final String DATABASE_NAME = "dataManager";

    //  table name
    private static final String TABLE_NAME = "imagetable";
    private static final String COLUMN_NAME = "imagename";

    //  Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_IMG_URL = "ImgFavourite";
    private static final String KEY_IS_FAVOURITE = "IsFavourite";

    ContentResolver mContentResolver;

    public Databasehandler(Context context) {
        super(context, "imageDb.db", null, 1);

        db = this.getWritableDatabase();
        //mContentResolver = context.getContentResolver();

        // db = this.getWritableDatabase();

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create TABLE images( id INTEGER PRIMARY KEY, name TEXT)");
    }

    public boolean insertImage(String image) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //database.execSQL("delete from "+ "images"); // delete all data from database at once
        String ll = "insert into " + "images" + " values(null, '" + image + "')";
        database.execSQL(ll);
        database.close();
        return true;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("drop table if exists images");
        // Create tables again
        onCreate(db);
    }

    public Cursor allData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from images", null);
        return cursor;
    }

    public void deleteFav(String id) {

        String where = "name=?";

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("images", where, new String[]{id});

        db.close();

    }


    public void delete(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor cursor = db.rawQuery("SELECT id FROM images WHERE id='"+id+"'",null);

        db.execSQL("delete from" + " images" + " where id=" + id);

        db.close();
        //db.delete("images","id=?", new String[]{id});
    }

}
