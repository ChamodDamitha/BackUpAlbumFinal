package Handlers;

import android.net.Uri;

import com.example.chamod.backupalbumfinal.Activities.OpenedAlbumActivity;

import java.util.ArrayList;

import Databases.ImageDB;
import Model.Album;
import Model.Image;
import Support.Utility;

/**
 * Created by Chamod on 7/13/2016.
 */
public class OpenedAlbumHandler
{
    private Album openedAlbum;
    private ArrayList<Image> images;
    private ImageDB imageDB;
    private static OpenedAlbumHandler openedAlbumHandler;

    private OpenedAlbumActivity openedAlbumActivity;

    public static OpenedAlbumHandler getInstance()
    {
        if(openedAlbumHandler==null)
            openedAlbumHandler=new OpenedAlbumHandler();
        return openedAlbumHandler;
    }

    public void setOpenedAlbumActivity(OpenedAlbumActivity openedAlbumActivity) {
        this.openedAlbumActivity = openedAlbumActivity;
        imageDB=ImageDB.getInstance(openedAlbumActivity);
    }

    private OpenedAlbumHandler()
    {


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

    public void addImage(Image image)
    {
        //take para and save to DB
        images.add(image);
        imageDB.addImage(openedAlbum,image);

    }
    //test
    public Image createImage(String desc,Uri imageUri)
    {
        Image image=new Image();
        image.setDescription(desc);
        image.setModifiedDate(Utility.getCurrentDate());
        image.setUri(imageUri);
        image.setId(imageDB.addImage(openedAlbum, image));
        return image;
    }

    public void deleteImage(int position)
    {
        Image image=images.get(position);
        imageDB.deleteImage(image);
        images.remove(image);
    }

    public ArrayList<Image> getImagesOfAlbum(Album album)
    {
        setImages(imageDB.getImagesOfAlbum(album));
        return getImages();
    }

    public void updateImageDesc(int position,String desc)
    {
        Image image=images.get(position);
        image.setDescription(desc);
        image.setModifiedDate(Utility.getCurrentDate());
        imageDB.updateImageDetails(image);
    }
}
