package com.example.filmapp.Entity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {

    private final static String databaseName = "FilmDB";
    private final static int databaseVersion = 1;
    private String FILM_TABLE = "Films";
    private static DB dbInstance;

    public DB(Context context)
    {
        super(context,databaseName,null,databaseVersion);
    }

    public synchronized static DB getInstance(Context context)
    {   if(dbInstance == null)   {
            dbInstance = new DB(context.getApplicationContext());
        }
        return dbInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createQuery = "CREATE TABLE " + FILM_TABLE + " ("
                + "userID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title Text,"
                + "year Text,"
                + "imdbID Text,"
                + "type Text,"
                + "poster Text"
                + " )";

        sqLiteDatabase.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void AddNewItem(String title, String year, String imdbID, String type, String poster)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("title",title);
        contentValues.put("year",year);
        contentValues.put("imdbID",imdbID);
        contentValues.put("type",type);
        contentValues.put("poster",poster);

        sqLiteDatabase.insert(FILM_TABLE,null,contentValues);

        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public Result getFilms(int Id)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(FILM_TABLE,null,"userID= ? " ,new String[]{ String.valueOf(Id) },null,null,null);
        Result film = null;
        if(cursor.moveToFirst())
        {      film = new Result(
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getString(cursor.getColumnIndex("year")),
                cursor.getString(cursor.getColumnIndex("imdbID")),
                cursor.getString(cursor.getColumnIndex("type")),
                cursor.getString(cursor.getColumnIndex("poster")));
        }

        return film;
    }

    @SuppressLint("Range")
    public ArrayList<Result> getFilmList()
    {
        ArrayList<Result> filmArrayList = new ArrayList<Result>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(FILM_TABLE,null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                filmArrayList.add(
                        new Result(
                                cursor.getString(cursor.getColumnIndex("title")),
                                cursor.getString(cursor.getColumnIndex("year")),
                                cursor.getString(cursor.getColumnIndex("imdbID")),
                                cursor.getString(cursor.getColumnIndex("type")),
                                cursor.getString(cursor.getColumnIndex("poster"))
                        ));
            }
            while(cursor.moveToNext());

        }

        sqLiteDatabase.close();

        return filmArrayList;
    }

    public void DeleteFilmFromDB(String name)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete( FILM_TABLE,"title= ? ",new String[]
                {
                        String.valueOf(name)
                });

        sqLiteDatabase.close();

    }

}
