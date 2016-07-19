package Support;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.chamod.backupalbumfinal.ImageViewUri;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


    public static Bitmap decodeBitmap(Uri selectedImage,Activity activity,int reqHeight,int reqWidth) throws FileNotFoundException
    {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;                                                            //avoids memory allocation while decoding
        BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(selectedImage),null,options);
        final int imageHeight=options.outHeight;
        final int imageWidth=options.outWidth;

        int inSampleSize=1;
        while((imageHeight/inSampleSize)>reqHeight || (imageWidth/inSampleSize)>reqWidth)
        {
            inSampleSize*=2;
        }

        options.inSampleSize=inSampleSize;
        options.inJustDecodeBounds=false;

        return BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(selectedImage),null,options);
    }
}
