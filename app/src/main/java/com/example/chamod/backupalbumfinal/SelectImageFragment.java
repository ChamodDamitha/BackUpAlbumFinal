package com.example.chamod.backupalbumfinal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;

import Support.Utility;

/**
 * Created by Chamod on 7/16/2016.
 */
public class SelectImageFragment extends Fragment {
    private EditText txtDescFrgmnt;
    private Button btnSelectImgFrgmnt;
    private ImageView imageViewfrgmnt;
    private Activity relatedActivity;

    private Bitmap imageBitmap;
    private Uri imageUri;
    private String imageDesc;

    private View view;

    public interface ISelectImage
    {
        void addImage(Bitmap imageBitmap,Uri imageUri,String imageDesc);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        relatedActivity=activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_add_image,container,false);

        imageViewfrgmnt=(ImageView)view.findViewById(R.id.imageViewfrgmnt);
        btnSelectImgFrgmnt=(Button)view.findViewById(R.id.btnSelectImgFrgmnt);
        btnSelectImgFrgmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        txtDescFrgmnt=(EditText)view.findViewById(R.id.txtDescFrgmnt);
        txtDescFrgmnt.setText(null);

        final ImageView imageViewfrgmnt=(ImageView)view.findViewById(R.id.imageViewfrgmnt);
        imageViewfrgmnt.setImageBitmap(null);

        Button btnOkFrgmnt=(Button)view.findViewById(R.id.btnOkFrgmnt);
        btnOkFrgmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passImageDetails();
                imageViewfrgmnt.setImageDrawable(null);
                txtDescFrgmnt.setText("");
            }
        });

        Button btnCancelFragment=(Button)view.findViewById(R.id.btnCancelFragment);
        btnCancelFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewfrgmnt.setImageDrawable(null);
                txtDescFrgmnt.setText("");
                view.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

    public void selectImage()
    {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        final int ACTIVITY_SELECT_IMAGE=1234;
        startActivityForResult(intent,ACTIVITY_SELECT_IMAGE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 1234:
                if(resultCode==relatedActivity.RESULT_OK){
                    imageUri=data.getData();

                    imageBitmap= null;
                    try {
                        imageBitmap = Utility.decodeBitmap(imageUri, relatedActivity,
                                imageViewfrgmnt.getMaxHeight(),imageViewfrgmnt.getMaxWidth());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    imageViewfrgmnt.setImageBitmap(imageBitmap);
                    imageViewfrgmnt.setVisibility(View.VISIBLE);

                }
        }
    }

    public void passImageDetails()
    {
        ISelectImage iSelectImage=(ISelectImage)relatedActivity;
        iSelectImage.addImage(imageBitmap,imageUri,txtDescFrgmnt.getText().toString());
        view.setVisibility(View.INVISIBLE);
    }

}
