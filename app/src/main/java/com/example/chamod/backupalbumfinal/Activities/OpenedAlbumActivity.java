package com.example.chamod.backupalbumfinal.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chamod.backupalbumfinal.ImageListAdapter;
import com.example.chamod.backupalbumfinal.R;
import com.example.chamod.backupalbumfinal.SelectImageFragment;

import Handlers.AccountHandler;
import Handlers.OpenedAlbumHandler;
import Model.Image;


public class OpenedAlbumActivity extends ActionBarActivity implements SelectImageFragment.ISelectImage {

    private OpenedAlbumHandler openedAlbumHandler;
    private ImageListAdapter imageListAdapter;
    private ListView listViewImages;
    private TextView txtAlbumName;

    private View selectImageFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opened_album);

        openedAlbumHandler=OpenedAlbumHandler.getInstance();
        openedAlbumHandler.setOpenedAlbumActivity(this);

        listViewImages=(ListView)findViewById(R.id.listViewImages);

        txtAlbumName=(TextView)findViewById(R.id.txtAlbumName);
        txtAlbumName.setText("Album : "+openedAlbumHandler.getOpenedAlbum().getName());

        selectImageFragment=(View)findViewById(R.id.fragmentSelectImg);
        selectImageFragment.setVisibility(View.INVISIBLE);

        setImageListView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opened_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_logOutOpenedAlbum :
                logOut();
                return true;
            case R.id.action_CreateAccountOpenedAlbum:
                startActivity(new Intent(this,NewAccountActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setImageListView() {
        imageListAdapter=new ImageListAdapter(OpenedAlbumActivity.this,openedAlbumHandler.getImagesOfAlbum(openedAlbumHandler.getOpenedAlbum()));
        listViewImages.setAdapter(imageListAdapter);
    }

    //button click to add image
    public void addNewImageButtonClick(View view)
    {
        try {
            selectImageFragment.setVisibility(View.VISIBLE);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"error="+e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    //button click to delete image
    public void deleteImage(View view)
    {
        final int position=listViewImages.getPositionForView((View)view.getParent());

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete Image");
        builder.setMessage("Are you sure you want to delete the image ?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       openedAlbumHandler.deleteImage(position);
                       imageListAdapter.notifyDataSetChanged();
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

    public void saveImgDesc(View view)
    {
        int position=listViewImages.getPositionForView((View)view.getParent());
        EditText txtImgDesc=(EditText)findViewById(R.id.txtImageDesc);
        Button btnEditImgDesc=(Button)findViewById(R.id.btnEditDesc);
        Button btnSaveImgDesc=(Button)findViewById(R.id.btnSaveImgDesc);


        openedAlbumHandler.updateImageDesc(position,txtImgDesc.getText().toString());
        txtImgDesc.setEnabled(false);
        btnEditImgDesc.setEnabled(true);
        btnSaveImgDesc.setEnabled(false);
    }

    public void logOut()
    {
        AccountHandler.getInstance().logOut();
        startActivity(new Intent(this, MainLoginActivity.class));
    }

    @Override
    public void addImage(Bitmap imageBitmap, Uri imageUri, String imageDesc) {
        Image image=openedAlbumHandler.createImage(imageDesc,imageUri);
        openedAlbumHandler.addImage(image);
        imageListAdapter.notifyDataSetChanged();
    }
}
