package com.example.chamod.backupalbumfinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import Handlers.AccountHandler;
import Handlers.AlbumHandler;
import Handlers.OpenedAlbumHandler;


public class LoggedAccountActivity extends ActionBarActivity {
    private AccountHandler accountHandler;
    private AlbumHandler albumHandler;

    private AlbumListAdapter albumListAdapter;

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
        albumListAdapter=new AlbumListAdapter(this,albumHandler.getAlbumsOfUser(accountHandler.getLoggedUser()));
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
                                albumHandler.addAlbum(accountHandler.getLoggedUser(), albumName);
                                albumListAdapter.notifyDataSetChanged();
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

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete Album");
        builder.setMessage("Are you sure you want to delete the album '"+albumHandler.getAlbums().get(position).getName()+"' ?");
        builder.setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    albumHandler.deleteAlbum(position);
                    albumListAdapter.notifyDataSetChanged();
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
        try {
            final int position = listViewAlbums.getPositionForView((View) view.getParent());
            OpenedAlbumHandler openedAlbumHandler = OpenedAlbumHandler.getInstance();
            openedAlbumHandler.setOpenedAlbum(albumHandler.getAlbums().get(position));

            if (openedAlbumActivityIntent == null)
                openedAlbumActivityIntent = new Intent(this, OpenedAlbumActivity.class);
            startActivity(openedAlbumActivityIntent);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error="+e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void logOut()
    {
        accountHandler.logOut();
        startActivity(new Intent(this, MainLogin.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logged_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_logOutLoggedAccount:
                logOut();
                return true;
            case R.id.action_CreateAccountLoggedAccount:
                startActivity(new Intent(this,NewAccountActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainLogin.class));
    }
}
