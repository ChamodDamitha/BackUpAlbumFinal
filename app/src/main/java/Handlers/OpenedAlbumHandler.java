package Handlers;

import com.example.chamod.backupalbumfinal.OpenedAlbumActivity;

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
        imageDB=new ImageDB(openedAlbumActivity,null,null,1);
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
        //test
        images.add(image);
        imageDB.addImage(openedAlbum,image);

    }
    //test
    public Image createImage(String desc)
    {
        Image image=new Image();
        image.setDescription(desc);
        image.setModifiedDate(Utility.getCurrentDate());
        image.setId(imageDB.addImage(openedAlbum, image));
        return image;
    }

    public void deleteImage(int position)
    {
        //dkgnldl
    }

    public ArrayList<Image> getImagesOfAlbum(Album album)
    {
        setImages(imageDB.getImagesOfAlbum(album));
        return getImages();
    }
}
