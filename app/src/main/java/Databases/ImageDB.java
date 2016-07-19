package Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

import Model.Album;
import Model.Image;

/**
 * Created by Chamod on 7/13/2016.
 */
public class ImageDB extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="BackUpAlbum3.db";
    private static final String TABLE_IMAGES="images";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_DESC="description";
    private static final String COLUMN_DATE_MODIFIED="modified_date";
    private static final String COLUMN_URI="uri";
    private static final String COLUMN_BACKED="backed";
    private static final String COLUMN_ALBUM_ID="album_id";

    static Context context;
    private static ImageDB imageDB;
    public static ImageDB getInstance(Context context)
    {
        if(imageDB==null) {
            imageDB = new ImageDB(context, null, null, 1);
            ImageDB.context=context;
        }
        return imageDB;
    }

    private ImageDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE IF NOT EXISTS "+TABLE_IMAGES+"( "+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DESC + " TEXT,"+ COLUMN_DATE_MODIFIED +" DATE,"+COLUMN_URI+" TEXT UNIQUE," + COLUMN_BACKED+" TINYINT,"
                + COLUMN_ALBUM_ID + " INTEGER );" ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGES);
        onCreate(db);
    }

    public int addImage(Album album, Image image)//return image id
    {
        ContentValues values=new ContentValues();
        values.put(COLUMN_DESC,image.getDescription());
        values.put(COLUMN_DATE_MODIFIED,image.getModifiedDate());
        values.put(COLUMN_URI,image.getUri().toString());
        values.put(COLUMN_BACKED,0);
        values.put(COLUMN_ALBUM_ID,album.getId());

        SQLiteDatabase db=getWritableDatabase();

        db.insert(TABLE_IMAGES, null, values);

        String query="SELECT MAX("+COLUMN_ID+") FROM "+TABLE_IMAGES+" ;";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        int id=c.getInt(c.getColumnIndex("MAX("+COLUMN_ID+")"));
        db.close();
        return id;
    }

    public ArrayList<Image> getImagesOfAlbum(Album album)
    {
        ArrayList<Image> images=new ArrayList<>();
        Image image;
        SQLiteDatabase db=getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_IMAGES + " WHERE " + COLUMN_ALBUM_ID + "=" + album.getId() + " ;";

            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                image = new Image();
                image.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                image.setDescription(c.getString(c.getColumnIndex(COLUMN_DESC)));
                image.setModifiedDate(c.getString(c.getColumnIndex(COLUMN_DATE_MODIFIED)));
                image.setUri(Uri.parse(c.getString(c.getColumnIndex(COLUMN_URI))));
                image.setBacked(false);
                images.add(image);

                c.moveToNext();
            }

        return images;
    }

    public void deleteImage(Image image)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_IMAGES,COLUMN_ID+" = "+image.getId(),null);
        db.close();
    }

    public void updateImageDetails(Image image)
    {
        ContentValues values=new ContentValues();
        values.put(COLUMN_DESC,image.getDescription());
        values.put(COLUMN_DATE_MODIFIED,image.getModifiedDate());

        SQLiteDatabase db=getWritableDatabase();
        db.update(TABLE_IMAGES,values,COLUMN_ID+" = "+image.getId(),null);
        db.close();
    }
}
