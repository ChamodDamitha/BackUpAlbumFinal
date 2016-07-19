package com.example.chamod.backupalbumfinal;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import Support.Utility;

/**
 * Created by Chamod on 7/19/2016.
 */
public class ViewImageListTask extends AsyncTask<Uri,Void,Bitmap>
{
    private Activity relatedActivity;
    private final WeakReference<ImageView> imageViewWeakReference;
    private final int imageHeight,imageWidth;

    public ViewImageListTask(ImageView imageView, Activity activity) {
        super();
        relatedActivity=activity;
        imageViewWeakReference=new WeakReference<ImageView>(imageView);
        imageHeight=imageView.getMaxHeight();
        imageWidth=imageView.getMaxWidth();
    }



    @Override
    protected Bitmap doInBackground(Uri... params) {
            try {
                return Utility.decodeBitmap(params[0], relatedActivity, imageHeight, imageWidth);
            }
            catch (Exception e) {
                Toast.makeText(relatedActivity, "Error in asyncTask = " + e.toString(), Toast.LENGTH_LONG).show();
            }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

            if(imageViewWeakReference!=null && bitmap!=null)
            {
                final ImageView imageView=imageViewWeakReference.get();
                if(imageView!=null)
                {
                    imageView.setImageBitmap(bitmap);
                }
            }
    }
}
