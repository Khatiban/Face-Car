package com.fury.facecar;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fury.facecar.Adapter.Adapter_Video_List;
import com.fury.facecar.Adapter.OnLoadMoreLisener;
import com.fury.facecar.Model.Video_List;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FURY on 2/21/2018.
 */

public class VideoList extends Activity {

    //Slider menu
    DrawerLayout drawerlayout;

    int numberLoad = 0;
    int numberLoadItem = 0;

    //object
    ProgressWheel progress_wheel;
    ImageView btn_Menu, btn_Search;
    TextView txt_Home, btn_menu_image_text, btn_menu_buy_text, btn_menu_home_text, btn_menu_video_text, btn_menu_post_text, txt_face_car_menu, txt_menu_1, txt_menu_2, txt_menu_3, txt_menu_4, txt_menu_5, txt_menu_6;
    RelativeLayout txt_menu_home, txt_menu_mark, txt_menu_post_me, txt_menu_profile, txt_menu_support, txt_menu_about;
    LinearLayout btn_menu_home, btn_menu_video, btn_menu_post, btn_menu_image, btn_menu_buy;

    private RecyclerView recyclerView;
    private List<Video_List> video = new ArrayList<>();
    private Adapter_Video_List videoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_video_list);

        ID();
        font();
        Recycler();

        boolean n = Net();
        if (n) {
            Videos();
        } else {

        }

    }


    //recycler
    public void Recycler() {
        //Get Ready List video
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(VideoList.this));
        videoAdapter = new Adapter_Video_List(video, VideoList.this, recyclerView);
        recyclerView.setAdapter(videoAdapter);
        videoAdapter.setOnLoadMoreLisener(new OnLoadMoreLisener() {
            @Override
            public void OnLoadMore() {

//check net
                if (Net()) {
                    //get server
                    Videos();
                } else {
                    //not found net
                }

            }
        });
    }

    public void font() {

        //Get file font
        Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSansMobile.ttf");

        txt_Home.setTypeface(typeface);
        btn_menu_buy_text.setTypeface(typeface);
        btn_menu_image_text.setTypeface(typeface);
        btn_menu_home_text.setTypeface(typeface);
        btn_menu_video_text.setTypeface(typeface);
        btn_menu_post_text.setTypeface(typeface);
        txt_face_car_menu.setTypeface(typeface);
        txt_menu_1.setTypeface(typeface);
        txt_menu_2.setTypeface(typeface);
        txt_menu_3.setTypeface(typeface);
        txt_menu_4.setTypeface(typeface);
        txt_menu_4.setTypeface(typeface);
        txt_menu_5.setTypeface(typeface);
        txt_menu_6.setTypeface(typeface);

    }

    public void ID() {

        //sliding view
        drawerlayout = (DrawerLayout) findViewById(R.id.root_page_1);
        txt_Home = (TextView) findViewById(R.id.txt_Home);
        btn_menu_buy_text = (TextView) findViewById(R.id.btn_menu_buy_text);
        btn_menu_image_text = (TextView) findViewById(R.id.btn_menu_image_text);
        btn_menu_home_text = (TextView) findViewById(R.id.btn_menu_home_text);
        btn_menu_video_text = (TextView) findViewById(R.id.btn_menu_video_text);
        btn_menu_post_text = (TextView) findViewById(R.id.btn_menu_post_text);
        txt_face_car_menu = (TextView) findViewById(R.id.txt_face_car_menu);
        txt_menu_1 = (TextView) findViewById(R.id.txt_menu_1);
        txt_menu_2 = (TextView) findViewById(R.id.txt_menu_2);
        txt_menu_3 = (TextView) findViewById(R.id.txt_menu_3);
        txt_menu_4 = (TextView) findViewById(R.id.txt_menu_4);
        txt_menu_5 = (TextView) findViewById(R.id.txt_menu_5);
        txt_menu_6 = (TextView) findViewById(R.id.txt_menu_6);
        btn_Menu = (ImageView) findViewById(R.id.btn_Menu);
        btn_Search = (ImageView) findViewById(R.id.btn_Search);
        txt_menu_home = (RelativeLayout) findViewById(R.id.txt_menu_home);
        txt_menu_mark = (RelativeLayout) findViewById(R.id.txt_menu_mark);
        txt_menu_post_me = (RelativeLayout) findViewById(R.id.txt_menu_post_me);
        txt_menu_profile = (RelativeLayout) findViewById(R.id.txt_menu_profile);
        txt_menu_support = (RelativeLayout) findViewById(R.id.txt_menu_support);
        txt_menu_about = (RelativeLayout) findViewById(R.id.txt_menu_about);
        btn_menu_post = (LinearLayout) findViewById(R.id.btn_menu_post);
        btn_menu_video = (LinearLayout) findViewById(R.id.btn_menu_video);
        btn_menu_home = (LinearLayout) findViewById(R.id.btn_menu_home);
        btn_menu_image = (LinearLayout) findViewById(R.id.btn_menu_image);
        btn_menu_buy = (LinearLayout) findViewById(R.id.btn_menu_buy);
        progress_wheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        recyclerView = (RecyclerView) findViewById(R.id.RecycleList_VideoList);

    }

    public boolean Net() {
        if (app_net.getInstance(VideoList.this).isOnline()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the adapter.
        // Because the RecyclerView won't unregister the adapter, the
        // ViewHolders are very likely leaked.
        recyclerView.setAdapter(null);

    }

    public void Videos() {
        progress_wheel.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(VideoList.this);
        String url = "https://facecar.ir/wp-json/wp/v2/posts?categories=17&per_page=100";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        //Get 10 Item After Loading
                        numberLoadItem += 5;
                        if (numberLoadItem > response.length()){
                            numberLoadItem = response.length();
                        }
                        for (int i = numberLoad; i < numberLoadItem; i++) {
                            numberLoad ++ ;
                            JSONObject jsonObject = response.getJSONObject(i);
                            String Id = null;
                            String Title = null;
                            String View = null;
                            String Like = null;
                            String Url = null;
                            String UrlJson = null;
                            if (!jsonObject.isNull("id")) {
                                Id = jsonObject.getString("id");
                            }
                            if (!jsonObject.isNull("title")) {

                                JSONObject d = jsonObject.getJSONObject("title");
                                //Title = new String(d.getString("rendered").getBytes("ISO-8859-1"), "UTF-8");
                                Title = d.getString("rendered");

                            }
                            if (!jsonObject.isNull("postView")) {

                                View = jsonObject.getString("postView");
                            }
                            if (!jsonObject.isNull("postLike")) {

                                Like = jsonObject.getString("postLike");

                            }
                            if (!jsonObject.isNull("better_featured_image")) {

                                try {
                                    JSONObject d = jsonObject.getJSONObject("better_featured_image");
                                    JSONObject f = d.getJSONObject("media_details");
                                    JSONObject g = f.getJSONObject("sizes");
                                    JSONObject w = g.getJSONObject("medium");
                                    Url = w.getString("source_url");
                                } catch (Exception e) {
                                    JSONObject d = jsonObject.getJSONObject("better_featured_image");
                                    JSONObject f = d.getJSONObject("media_details");
                                    JSONObject g = f.getJSONObject("sizes");
                                    JSONObject w = g.getJSONObject("medium");
                                    Url = w.getString("source_url");
                                }

                            }
                            if (!jsonObject.isNull("_links")) {

                                try {
                                    JSONObject d = jsonObject.getJSONObject("_links");
                                    JSONArray f = d.getJSONArray("self");
                                    JSONObject s = f.getJSONObject(0);
                                    UrlJson = s.getString("href");
                                } catch (Exception e) {
                                    ////
                                }

                            }
                            video.add(new Video_List(Id, Url, Title, Like, View, UrlJson));
                        }
                        videoAdapter.notifyDataSetChanged();
                        videoAdapter.setLoading(false);
                        progress_wheel.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    //Error Code
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something
            }
        });

        requestQueue.add(jsonArrayRequest);
    }


}
