package com.example.chamod.backupalbumfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import Model.Image;

/**
 * Created by Chamod on 7/13/2016.
 */
public class ImageListAdapter extends ArrayAdapter<Image> {

    public ImageListAdapter(Context context, Image[] images) {
        super(context,R.layout.custom_row_image_list ,images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View customView=inflater.inflate(R.layout.custom_row_image_list,parent,false);

        Image singleImage=getItem(position);

        TextView txtImageDesc=(TextView)customView.findViewById(R.id.txtImageDesc);
        ImageView imageViewAlbumImage=(ImageView)customView.findViewById(R.id.imageViewAlbumImage);


        txtImageDesc.setText(singleImage.getDescription());
        //setting manual image should be done

        return customView;


    }
}
