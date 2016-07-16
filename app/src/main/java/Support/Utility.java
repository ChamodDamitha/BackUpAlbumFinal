package Support;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chamod on 7/11/2016.
 */
public class Utility
{

    public static String getCurrentDate()
    {
        DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
        Date date=new Date();
        return df.format(date);
    }


    public static Bitmap decodeBitmap(Uri selectedImage,Activity activity) throws FileNotFoundException
    {
        BitmapFactory.Options o=new BitmapFactory.Options();
        o.inJustDecodeBounds=true;
        BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(selectedImage),null,o);
        final int REQUIRED_SIZE=3000;

        int width_tmp=o.outWidth,height_tmp=o.outHeight;

        int scale=1;
        while(true)
        {
            if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
            {
                break;
            }
            width_tmp/=2;
            height_tmp/=2;
            scale*=2;
        }
        BitmapFactory.Options o2=new BitmapFactory.Options();
        o2.inSampleSize=scale;
        return BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(selectedImage),null,o2);
    }
}
