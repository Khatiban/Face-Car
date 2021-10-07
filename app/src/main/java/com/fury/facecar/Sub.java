package com.fury.facecar;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
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
import com.fury.facecar.Adapter.Adapter_Image_List;
import com.fury.facecar.Adapter.Adapter_Sub_List;
import com.fury.facecar.Adapter.OnLoadMoreLisenerImage;
import com.fury.facecar.Model.Image_List;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FURY on 5/19/2018.
 */

public class Sub extends Activity {


    int numberLoad = 0;
    int numberLoadItem = 0;

    //Active
    public static Activity aty;

    //Slider menu
    DrawerLayout drawerlayout;

    //object
    TextView txt_Home, btn_menu_buy_text, btn_menu_image_text, btn_menu_home_text, btn_menu_video_text, btn_menu_post_text, txt_menu_1, txt_menu_2, txt_menu_3, txt_menu_4, txt_menu_5, txt_menu_6, txt_face_car_menu;
    RelativeLayout home_Video, txt_menu_home, txt_menu_mark, txt_menu_post_me, txt_menu_profile, txt_menu_support, txt_menu_about, home_News;
    LinearLayout btn_menu_post, btn_menu_video, btn_menu_home, btn_menu_image, btn_menu_buy;
    ProgressWheel progress_wheel;
    ImageView btn_Menu, btn_Search;

    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    private List<Image_List> image = new ArrayList<>();
    private Adapter_Sub_List imageAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.root_sub_list);

        ID();
        font();
        setListView();

        boolean n = Net();
        if (n) {
            Sub();
        } else {

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

    public boolean Net() {
        if (app_net.getInstance(Sub.this).isOnline()) {
            return true;
        } else {
            return false;
        }
    }

    public void setListView(){

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        imageAdapter = new Adapter_Sub_List(image, Sub.this, recyclerView);
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnLoadMoreLisener(new OnLoadMoreLisenerImage() {
            @Override
            public void OnLoadMore() {
//check net
                if (Net()) {
                    //get server
                    Sub();
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
    }


    public void Sub() {
        progress_wheel.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(Sub.this);
        String url = "https://facecar.ir/wp-json/wp/v2/posts?categories=22&per_page=100";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        //Get 10 Item After Loading
                        numberLoadItem += 10;
                        if (numberLoadItem > response.length()){
                            numberLoadItem = response.length();
                        }
                        for (int i = numberLoad; i < numberLoadItem; i++) {
                            numberLoad ++ ;
                            JSONObject jsonObject = response.getJSONObject(i);
                            String Id = null;
                            String Title = null;
                            String Title2 = null;
                            String View = null;
                            String Like = null;
                            String Url = null;
                            if (!jsonObject.isNull("id")) {
                                Id = jsonObject.getString("id");
                            }
                            if (!jsonObject.isNull("_links")) {

                                JSONObject d = jsonObject.getJSONObject("_links");
                                JSONArray w = d.getJSONArray("self");
                                JSONObject jsonObject2 = w.getJSONObject(0);
                                //Title = new String(d.getString("rendered").getBytes("ISO-8859-1"), "UTF-8");
                                Title = jsonObject2.getString("href");

                            }
                            if (!jsonObject.isNull("title")) {

                                JSONObject d = jsonObject.getJSONObject("title");
                                //Title = new String(d.getString("rendered").getBytes("ISO-8859-1"), "UTF-8");
                                Title2 = d.getString("rendered");

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
                                    JSONObject w = g.getJSONObject("medium_large");
                                    Url = w.getString("source_url");
                                } catch (Exception e) {
                                    JSONObject d = jsonObject.getJSONObject("better_featured_image");
                                    JSONObject f = d.getJSONObject("media_details");
                                    JSONObject g = f.getJSONObject("sizes");
                                    JSONObject w = g.getJSONObject("medium");
                                    Url = w.getString("source_url");
                                }

                            }
                            image.add(new Image_List(Id,Title ,Url,View,Like,Title2));
                        }
                        imageAdapter.notifyDataSetChanged();
                        imageAdapter.setLoading(false);
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
