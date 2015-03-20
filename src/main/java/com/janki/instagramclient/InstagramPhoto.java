package com.janki.instagramclient;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by janki on 3/18/15.
 * <p/>
 * - Popular: https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
 * - Response
 * - Type:{ data => [x] => “type” } (image or video)
 * - Caption: { data => [X] => caption => text}
 * - URL: {data => [x] => images => standard resolution = > url}
 * - Author: { data => [x] => user => username}
 * - Total Comments: { data => [x] => comments => count }
 * - Last Comment: { data => [x] => comments => data[x] => text}
 * - Last Comment Username: { data => [x] comments => data[x] => from => username }
 * - Profile photo: { data => [x] => user => profile_picture}
 * - Create Time: { data => [x] created_time}
 * - Total Likes: { data => [x] likes => count}
 */
public class InstagramPhoto {
    public String username;
    public String caption;
    public String imagerUrl;
    public int imageHeight;
    public int likeCount;
    public String lastComment;
    public String lastCommentUser;
    public int totalComments;
    public String profilePhotoUrl;
    public long createTime;
    public int totalLikes;
    public String relativeTime;

    public InstagramPhoto(JSONObject photoJson) {
        try {

            if (!photoJson.isNull("user")) {
                this.username = photoJson.getJSONObject("user").getString("username");
                this.profilePhotoUrl = photoJson.getJSONObject("user").getString("profile_picture");

            }
            if (!photoJson.isNull("caption")) {
                this.caption = photoJson.getJSONObject("caption").getString("text");
            } else {
                this.caption = " ";
            }
            if (!photoJson.isNull("images")) {
                this.imagerUrl = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                this.imageHeight = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            }
            if (!photoJson.isNull("likes")) {
                this.likeCount = photoJson.getJSONObject("likes").getInt("count");
            }
            if (!photoJson.isNull("comments")) {
                this.totalComments = photoJson.getJSONObject("comments").getInt("count");
                JSONArray comments = photoJson.getJSONObject("comments").getJSONArray("data");

                this.lastComment = comments.getJSONObject(comments.length() - 1).getString("text");
                this.lastCommentUser = comments.getJSONObject(comments.length() - 1).getJSONObject("from").getString("username");
            }
            if (!photoJson.isNull("created_time")) {
                this.createTime = photoJson.getLong("created_time");
                this.relativeTime = (String) DateUtils.getRelativeTimeSpanString(this.createTime * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            }
            if (!photoJson.isNull("likes")) {
                this.totalLikes = photoJson.getJSONObject("likes").getInt("count");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
