package com.example.chamod.backupalbumfinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import Handlers.AccountHandler;
import Handlers.AlbumHandler;
import Handlers.OpenedAlbumHandler;
import Model.Album;


public class LoggedAccountActivity extends ActionBarActivity {
    private AccountHandler accountHandler;
    private AlbumHandler albumHandler;

    private AlbumListAdapter albumListAdapter;
    private String[] albumNames;

    private ListView listViewAlbums;

    private Intent openedAlbumActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_account);


        listViewAlbums=(ListView)findViewById(R.id.listViewAlbums);

        accountHandler=AccountHandler.getInstance();

        albumHandler=AlbumHandler.getInstance();
        albumHandler.setLoggedAccountActivity(this);
        setAlbumListView();

    }

    private void setAlbumListView() {
        ArrayList<Album> albums=albumHandler.getAlbumsOfUser(accountHandler.getLoggedUser());
        albumNames=new String[albums.size()];
        for(int i=0;i<albumNames.length;i++)
        {
            albumNames[i]=albums.get(i).getName();
        }
        albumListAdapter=new AlbumListAdapter(this,albumNames);
        listViewAlbums.setAdapter(albumListAdapter);
    }

    public void addAlbum(View view)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("New Album");
        builder.setMessage("Enter Album Name");
        final EditText txtAlbumNme=new EditText(this);
        txtAlbumNme.setSingleLine();
        builder.setView(txtAlbumNme);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String albumName=txtAlbumNme.getText().toString().trim();
                        if(!albumName.equals("")) {
                            if(!albumHandler.isAlbumNameUsed(albumName)) {
                                String[] temp = new String[albumNames.length + 1];
                                for (int i = 0; i < albumNames.length; i++)
                                    temp[i] = albumNames[i];
                                temp[temp.length - 1] = albumName;
                                albumNames = temp;
                                albumListAdapter = new AlbumListAdapter(LoggedAccountActivity.this, albumNames);
                                listViewAlbums.setAdapter(albumListAdapter);


                                albumHandler.addAlbum(accountHandler.getLoggedUser(), albumName);
                            }
                        }
                    }
                }
        );
        builder.show();
    }

    public void deleteAlbum(View view)
    {

        final int position=listViewAlbums.getPositionForView((View)view.getParent());
        String albumName=albumNames[position];

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete Album");
        builder.setMessage("Are you sure you want to delete the album '"+albumName+"' ?");
        builder.setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String[] temp=new String[albumNames.length-1];
                    for(int i=0;i<temp.length;i++)
                    {
                        if(i>=position) temp[i]=albumNames[i+1];
                        else temp[i]=albumNames[i];
                    }
                    albumNames=temp;

                    albumListAdapter=new AlbumListAdapter(LoggedAccountActivity.this,albumNames);
                    listViewAlbums.setAdapter(albumListAdapter);

                    albumHandler.deleteAlbum(position);
                }
            }
        );
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //null event to do nothing
            }
        });
        builder.show();
    }

    public void gotoAlbum(View view)
    {
        final int position=listViewAlbums.getPositionForView((View)view.getParent());
        OpenedAlbumHandler openedAlbumHandler=OpenedAlbumHandler.getInstance();
        openedAlbumHandler.setOpenedAlbum(albumHandler.getAlbums().get(position));

        if(openedAlbumActivityIntent==null)
            openedAlbumActivityIntent=new Intent(this,OpenedAlbumActivity.class);
        startActivity(openedAlbumActivityIntent);
    }

    public void logOut()
    {
        accountHandler.logOut();
        startActivity(new Intent(this, MainLogin.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logged_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_logOut :
                logOut();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
