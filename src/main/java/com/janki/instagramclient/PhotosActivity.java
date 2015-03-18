package com.janki.instagramclient;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.janki.instagramclient.adpaters.InstagramPhotosAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "855a425665ba4d9d9873a95ad1bb7767";
    private ArrayList<InstagramPhoto> photos;
    InstagramPhotosAdapter aphotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        // Send out API request
        photos = new ArrayList<InstagramPhoto>();

        aphotos = new InstagramPhotosAdapter(this, photos);
        ListView lvphotos = (ListView) findViewById(R.id.lvPhotos);
        lvphotos.setAdapter(aphotos);

        fetchPopularPhotos();

    }

    public void fetchPopularPhotos()
    {
//    - Popular: https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
//      - Response
//        - Type:{ data => [x] => “type” } (image or video)
//        - Caption: { data => [X] => caption => text}
//        -URL: {data => [x] => images => standard resolution = > url}
//        - Author: { data => [x] => user => username}
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID ;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url,null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Expecting Json Object
                JSONArray photosJson = null;

                try {
                    photosJson = response.getJSONArray("data");
                    for(int i = 0; i < photosJson.length(); i++){
                        JSONObject photoJSON = photosJson.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        photo.imagerUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likeCount = photoJSON.getJSONObject("likes").getInt("count");

                        photos.add(photo);

                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }

                aphotos.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,  String responseString, Throwable throwable) {
               //Do Something
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
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
}
