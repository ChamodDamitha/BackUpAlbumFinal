package Handlers;

import java.util.ArrayList;

import Databases.ImageDB;
import Model.Album;
import Model.Image;

/**
 * Created by Chamod on 7/13/2016.
 */
public class OpenedAlbumHandler
{
    private Album openedAlbum;
    private ArrayList<Image> images;
    private ImageDB imageDB;
    private static OpenedAlbumHandler openedAlbumHandler;

    public static OpenedAlbumHandler getInstance()
    {
        if(openedAlbumHandler==null)
            openedAlbumHandler=new OpenedAlbumHandler();
        return openedAlbumHandler;
    }

    private OpenedAlbumHandler()
    {
        imageDB=new ImageDB();

    }

    public Album getOpenedAlbum() {
        return openedAlbum;
    }

    public void setOpenedAlbum(Album openedAlbum) {
        this.openedAlbum = openedAlbum;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public void addImage()
    {
        //take para and save to DB
    }
}
