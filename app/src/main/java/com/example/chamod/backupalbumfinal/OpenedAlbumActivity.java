package com.example.chamod.backupalbumfinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Handlers.OpenedAlbumHandler;
import Model.Image;


public class OpenedAlbumActivity extends ActionBarActivity {

    private OpenedAlbumHandler openedAlbumHandler;
    private ImageListAdapter imageListAdapter;
    private ListView listViewImages;
    private TextView txtAlbumName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opened_album);

        openedAlbumHandler=OpenedAlbumHandler.getInstance();
        openedAlbumHandler.setOpenedAlbumActivity(this);

        listViewImages=(ListView)findViewById(R.id.listViewImages);

        setImageListView();

        txtAlbumName=(TextView)findViewById(R.id.txtAlbumName);
        txtAlbumName.setText(openedAlbumHandler.getOpenedAlbum().getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opened_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setImageListView() {
        ArrayList<Image> images=openedAlbumHandler.getImagesOfAlbum(openedAlbumHandler.getOpenedAlbum());
        Image[] imagesArray=new Image[images.size()];
        for(int i=0;i<imagesArray.length;i++)
        {
            imagesArray[i]=images.get(i);
        }
        imageListAdapter=new ImageListAdapter(this,imagesArray);
        listViewImages.setAdapter(imageListAdapter);
    }

    public void addImage(View view)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("New Image");
        builder.setMessage("Enter Image Description");
        final EditText txtImageDesc=new EditText(this);
        builder.setView(txtImageDesc);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String imageDesc=txtImageDesc.getText().toString().trim();
                        if(!imageDesc.equals("")) {

                                ArrayList<Image> images=openedAlbumHandler.getImages();
                                Image[]  imagesArray = new Image[images.size()+1];
                                for (int i = 0; i < images.size(); i++)
                                    imagesArray[i] = images.get(i);
                                Image image=openedAlbumHandler.createImage(imageDesc);
                                imagesArray[imagesArray.length - 1] = image;
                                imageListAdapter = new ImageListAdapter(OpenedAlbumActivity.this, imagesArray);
                                listViewImages.setAdapter(imageListAdapter);


                                openedAlbumHandler.addImage(image);

                        }
                    }
                }
        );
        builder.show();
    }
}
