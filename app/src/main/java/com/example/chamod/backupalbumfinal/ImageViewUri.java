package com.example.chamod.backupalbumfinal;

import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by Chamod on 7/19/2016.
 */
public class ImageViewUri
{
    private ImageView imageView;
    private Uri uri;

    public ImageViewUri(ImageView imageView, Uri uri) {
        this.imageView = imageView;
        this.uri = uri;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Uri getUri() {
        return uri;
    }
}
