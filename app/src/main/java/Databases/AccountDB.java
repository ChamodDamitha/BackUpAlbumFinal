package Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chamod.backupalbumfinal.MainLogin;

import Model.User;

/**
 * Created by Chamod on 7/6/2016.
 */
public class AccountDB extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION=3;
    private static final String DATABASE_NAME="User.db";
    private static final String TABLE_USERS="users";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_NAME="NAME";
    private static final String COLUMN_EMAIL="email";
    private static final String COLUMN_USERNAME="username";
    private static final String COLUMN_PASSWORD="password";
    private  static final String COLUMN_LOGGED="logged";

    private static AccountDB accountDB;
    public static AccountDB getInstance(Context context)
    {
        if(accountDB==null)
            accountDB=new AccountDB(context,null,null,1);
        return accountDB;
    }

    MainLogin mainLogin;

    private AccountDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void setMainLogin(MainLogin mainLogin) {
        this.mainLogin = mainLogin;
    }



    public boolean isUsernameUsed(String username)
    {
        //check in DB
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+TABLE_USERS + " WHERE "+COLUMN_USERNAME+" = '"+username+"' ;";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.isAfterLast())return false;
        return true;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query="CREATE TABLE IF NOT EXISTS "+TABLE_USERS+"( "+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"+ COLUMN_EMAIL +" TEXT," + COLUMN_USERNAME+" TEXT,"
                + COLUMN_PASSWORD + " TEXT,"+COLUMN_LOGGED+" TINYINT );" ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        onCreate(db);
    }



    public int addUser(User user)
    {
        ContentValues values=new ContentValues();
        values.put(COLUMN_NAME,user.getName());
        values.put(COLUMN_EMAIL,user.getEmail());
        values.put(COLUMN_USERNAME,user.getUserName());
        values.put(COLUMN_PASSWORD,user.getPassword());
        values.put(COLUMN_LOGGED,1);

        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_USERS,null,values);

        String query="SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "='"+user.getUserName() +"';";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        int id=c.getInt(c.getColumnIndex(COLUMN_ID));
        db.close();
        return id;
    }

    public User login( String username , String password)
    {

        User user = new User();
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "='"+username +
                    "' AND " + COLUMN_PASSWORD + "='" + password +"';";

        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            user.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
            user.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
            user.setEmail(c.getString(c.getColumnIndex(COLUMN_EMAIL)));
            user.setUserName(c.getString(c.getColumnIndex(COLUMN_USERNAME)));
            user.setPassword(c.getString(c.getColumnIndex(COLUMN_PASSWORD)));
            user.setLogged(true);

            c.moveToNext();


        }

        if(user.getName()==null)return null;

        ContentValues values=new ContentValues();
        values.put(COLUMN_LOGGED,1);
        db.update(TABLE_USERS,values,COLUMN_USERNAME+"='"+user.getUserName()+"'",null);
        db.close();

        return user;
    }

    public void logOut(User user)
    {
        ContentValues values=new ContentValues();
        values.put(COLUMN_LOGGED,0);
        SQLiteDatabase db=getWritableDatabase();
        db.update(TABLE_USERS,values,COLUMN_USERNAME+"='"+user.getUserName()+"'",null);
        db.close();
    }

    public User getLoggedUser()
    {
        try {
            User user = new User();
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_LOGGED + "=1;";

            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            if (c == null) return null;

            user.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
            user.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
            user.setEmail(c.getString(c.getColumnIndex(COLUMN_EMAIL)));
            user.setUserName(c.getString(c.getColumnIndex(COLUMN_USERNAME)));
            user.setPassword(c.getString(c.getColumnIndex(COLUMN_PASSWORD)));
            user.setLogged(true);

            db.close();
            return user;
        }
        catch (Exception e)
        {
            return null;
        }

    }

}
