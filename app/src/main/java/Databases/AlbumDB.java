package Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import Model.Album;
import Model.User;

/**
 * Created by Chamod on 7/11/2016.
 */
public class AlbumDB extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="BackUpAlbum.db";
    private static final String TABLE_ALBUMS="albums";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_DATE_MODIFIED="modified_date";
    private static final String COLUMN_NO_OF_IMAGES="no_of_images";
    private static final String COLUMN_USER_ID="user_id";


    public AlbumDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE IF NOT EXISTS "+TABLE_ALBUMS+"( "+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"+ COLUMN_DATE_MODIFIED +" DATE," + COLUMN_NO_OF_IMAGES+" INTEGER,"
                + COLUMN_USER_ID + " INTEGER );" ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ALBUMS);
        onCreate(db);
    }


    public int addAlbum(Album album, User user)
    {
        ContentValues values=new ContentValues();
        values.put(COLUMN_NAME,album.getName());
        values.put(COLUMN_DATE_MODIFIED,album.getDateModified());
        values.put(COLUMN_NO_OF_IMAGES,album.getNoOfImages());
        values.put(COLUMN_USER_ID,user.getId());


        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_ALBUMS,null,values);

        String query="SELECT MAX("+COLUMN_ID+") FROM "+TABLE_ALBUMS+" ;";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        int id=c.getInt(c.getColumnIndex("MAX("+COLUMN_ID+")"));
        db.close();
        return id;
    }

    public ArrayList<Album> getAlbumsOfUser(User user)
    {
        ArrayList<Album> albums=new ArrayList<>();
        Album album;
        SQLiteDatabase db=getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_USER_ID + "=" + user.getId() + " ;";

            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                album = new Album();
                album.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                album.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
                album.setDateModified(c.getString(c.getColumnIndex(COLUMN_DATE_MODIFIED)));
                album.setNoOfImages(c.getInt(c.getColumnIndex(COLUMN_NO_OF_IMAGES)));
                albums.add(album);

                c.moveToNext();
            }

        return albums;

    }

    public void deleteAlbum(Album album) {
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_ALBUMS,COLUMN_ID+" = "+album.getId(),null);
    }
}

