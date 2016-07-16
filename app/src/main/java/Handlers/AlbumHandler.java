package Handlers;

import com.example.chamod.backupalbumfinal.LoggedAccountActivity;

import java.util.ArrayList;

import Databases.AlbumDB;
import Model.Album;
import Model.User;
import Support.Utility;

/**
 * Created by Chamod on 7/11/2016.
 */
public class AlbumHandler
{
    private static AlbumHandler albumHandler;
    private AlbumDB albumDB;
    private LoggedAccountActivity loggedAccountActivity;

    private ArrayList<Album> albums=new ArrayList<>();

    public static AlbumHandler getInstance()
    {
        if(albumHandler==null)
            albumHandler=new AlbumHandler();
        return albumHandler;
    }

    public void setLoggedAccountActivity(LoggedAccountActivity loggedAccountActivity) {
        this.loggedAccountActivity = loggedAccountActivity;
        albumDB=AlbumDB.getInstance(loggedAccountActivity);
    }

    private AlbumHandler()
    {}

    public boolean isAlbumNameUsed(String albumName)
    {
        for(Album a:albums)
        {
            if(a.getName().equals(albumName))return true;
        }
        return false;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(User user,String albumName)
    {
        Album album=new Album();
        album.setName(albumName);
        album.setDateModified(Utility.getCurrentDate());
        album.setNoOfImages(0);
        album.setId(albumDB.addAlbum(album, user));
        albums.add(album);
    }

    public ArrayList<Album> getAlbumsOfUser(User user)
    {
        setAlbums(albumDB.getAlbumsOfUser(user));
        return albums;
    }

    public void deleteAlbum(int position)
    {
        Album album=albums.get(position);
        albumDB.deleteAlbum(album);
        albums.remove(album);
    }
}
