package com.janki.instagramclient.adpaters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.janki.instagramclient.InstagramPhoto;
import com.janki.instagramclient.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by janki on 3/18/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhoto photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvTotalComments = (TextView) convertView.findViewById(R.id.tvCommentTotal);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhotos);
        ImageView ivProfilePhoto = (ImageView) convertView.findViewById(R.id.ivProfilePhoto);
        TextView tvRelativeTime = (TextView) convertView.findViewById(R.id.tvRelativeTime);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);

        tvTotalComments.setText("view all " + photo.totalComments + " comments.");

        String caption = " <font color ='#517fa4'> <b>"+ photo.username + " </b> </font>  <font color = '#373332'>" + photo.caption + " </font>";
        tvCaption.setText(Html.fromHtml(caption));

        String[] relativeTime = photo.relativeTime.split(" ");
        String time = relativeTime[0].concat(String.valueOf(relativeTime[1].charAt(0)));
        tvRelativeTime.setText(time + "  ");
        tvLikes.setText(photo.totalLikes + " likes");

        String comment = "<font color ='#517fa4'> <b>" + photo.lastCommentUser + "</b> </font> " + photo.lastComment;
        tvComment.setText(Html.fromHtml(comment));

        tvUsername.setText(Html.fromHtml(" <font color ='#517fa4'> <b>"+ photo.username + " </b> </font>"));

        ivProfilePhoto.setImageResource(0); // Clear the view
        if (photo.profilePhotoUrl != null) {
            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.LTGRAY)
                    .borderWidthDp(1)
                    .cornerRadiusDp(30)
                    .oval(false)
                    .build();

            // fetch the profile picture for the user
            Picasso.with(getContext())
                    .load(photo.profilePhotoUrl)
                    .fit()
                    .transform(transformation)
                    .into(ivProfilePhoto);
        }

        ivPhoto.setImageResource(0); // Clear the view

        Picasso.with(getContext())
                .load(photo.imagerUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.error_placeholder)
                .into(ivPhoto);

        return convertView;
    }
}
