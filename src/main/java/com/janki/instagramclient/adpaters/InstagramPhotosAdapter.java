package com.janki.instagramclient.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.janki.instagramclient.InstagramPhoto;
import com.janki.instagramclient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by janki on 3/18/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context,android.R.layout.simple_list_item_1 , objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhoto photo = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvPhotos);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhotos);

        tvCaption.setText(photo.caption);
        ivPhoto.setImageResource(0); // Clear the view

        Picasso.with(getContext()).load(photo.imagerUrl).into(ivPhoto);

        return convertView;
    }
}
