package Model;

import android.net.Uri;

/**
 * Created by Chamod on 7/3/2016.
 */
public class Image
{
    private int id;
    private String description;
    private String destination;
    private boolean backed=false;
    private String modifiedDate;
    private Uri uri;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isBacked() {
        return backed;
    }

    public void setBacked(boolean backed) {
        this.backed = backed;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void loadImage()
    {
        //load image from url

    }

}
