package Model;

import java.util.ArrayList;

/**
 * Created by Chamod on 7/3/2016.
 */
public class User
{
    private String name;
    private String email;
    private String userName;
    private String password;
    private boolean logged=false;
    private ArrayList<Album> albums;
    private int id;

    public User() {
    }

    public User(String name, String email, String userName, String password) {
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addAlbum(Album album)
    {
        albums.add(album);
    }

    public void removeAlbum(Album album)
    {
        albums.remove(album);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
