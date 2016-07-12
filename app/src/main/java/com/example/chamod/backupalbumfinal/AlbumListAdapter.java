package com.example.chamod.backupalbumfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Chamod on 7/11/2016.
 */
public class AlbumListAdapter extends ArrayAdapter<String> {

    public AlbumListAdapter(Context context, String[] albumsNames) {
        super(context,R.layout.custom_row_album_list,albumsNames);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View customView=inflater.inflate(R.layout.custom_row_album_list,parent,false);

        String singleAlbumName=getItem(position);
        TextView txtAlbumNameList=(TextView)customView.findViewById(R.id.txtAlbumNameInList);
        ImageView imageView=(ImageView)customView.findViewById(R.id.imageView);


        txtAlbumNameList.setText(singleAlbumName);
        imageView.setImageResource(R.drawable.folder_icon2);
        return customView;
    }
}
