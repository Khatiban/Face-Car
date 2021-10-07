package com.fury.facecar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fury.facecar.Adapter.Adapter_Home_News;
import com.fury.facecar.Adapter.Adapter_Home_Video;
import com.fury.facecar.Model.Home_News;
import com.fury.facecar.Model.Home_Video;
import com.fury.facecar.Model.Image_List;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

public class Home extends AppCompatActivity {

    final String url_Slider[] = new String[5];
    final String url_Slider_more[] = new String[5];

    //Slider menu
    DrawerLayout drawerlayout;

    //object
    TextView txt_Home, txt_video_home, btn_menu_buy_text, btn_menu_image_text, btn_menu_home_text, btn_menu_video_text, btn_menu_post_text, txt_face_car_menu, txt_menu_1, txt_menu_2, txt_menu_3, txt_menu_4, txt_menu_5, txt_menu_6, txt_News_home;
    ImageView btn_Menu, btn_Search;
    RelativeLayout home_Video, txt_menu_home, txt_menu_mark, txt_menu_post_me, txt_menu_profile, txt_menu_support, txt_menu_about, home_News;
    LinearLayout btn_menu_post, btn_menu_video, btn_menu_home, btn_menu_image, btn_menu_buy;
    ProgressWheel progress_wheel;
    BannerSlider bannerSlider;

    private List<Home_Video> videos = new ArrayList<>();
    private List<Home_News> news = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private Adapter_Home_Video videoAdapter;
    private Adapter_Home_News videoAdapter2;

    int pro = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_page_home);

        ID();
        font();
        progress();
        Recycler();
        Click();

        boolean n = Net();
        if (n) {
            jsonSlider();
            video();
            News();
        } else {

        }

    }


    //recycler
    public void Recycler() {
        //Get Ready List video
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setItemViewCacheSize(10);
        recyclerView2.setDrawingCacheEnabled(true);
        recyclerView2.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView2.setLayoutManager(new LinearLayoutManager(Home.this));
        videoAdapter = new Adapter_Home_Video(videos, Home.this, recyclerView);
        videoAdapter2 = new Adapter_Home_News(news, Home.this, recyclerView2);
        recyclerView.setAdapter(videoAdapter);
        recyclerView2.setAdapter(videoAdapter2);
    }


    //get all data from rest
    public void video() {
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        String url = "https://facecar.ir/wp-json/wp/v2/posts?categories=17";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        //Get 10 Item After Loading
                        int numberLoadItem = 5;
                        for (int i = 0; i < numberLoadItem; i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String Id = null;
                            String Title = null;
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
                            if (!jsonObject.isNull("better_featured_image")) {

                                JSONObject d = jsonObject.getJSONObject("better_featured_image");
                                JSONObject f = d.getJSONObject("media_details");
                                JSONObject g = f.getJSONObject("sizes");
                                JSONObject w = g.getJSONObject("medium");
                                Url = w.getString("source_url");

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

                            videos.add(new Home_Video(Title, Url, Id, UrlJson));
                        }
                        videoAdapter.notifyDataSetChanged();
                        pro = pro + 1;
                        progress();
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


    public void News() {
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        String url = "https://facecar.ir/wp-json/wp/v2/posts?categories=22";

         JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        //Get 10 Item After Loading
                        int numberLoadItem = 5;
                        for (int i = 0; i < numberLoadItem; i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String Id = null;
                            String Title = null;
                            String Sub = null;
                            String View = null;
                            String Like = null;
                            String Url = null;
                            if (!jsonObject.isNull("id")) {
                                Id = jsonObject.getString("id");
                            }
                            if (!jsonObject.isNull("title")) {

                                JSONObject d = jsonObject.getJSONObject("title");
                                //Title = new String(d.getString("rendered").getBytes("ISO-8859-1"), "UTF-8");
                                Title = d.getString("rendered");

                            }
                            if (!jsonObject.isNull("content")) {

                                JSONObject d = jsonObject.getJSONObject("content");
                                //Title = new String(d.getString("rendered").getBytes("ISO-8859-1"), "UTF-8");
                                Sub = d.getString("rendered");

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
                                }catch (Exception e){
                                    JSONObject d = jsonObject.getJSONObject("better_featured_image");
                                    JSONObject f = d.getJSONObject("media_details");
                                    JSONObject g = f.getJSONObject("sizes");
                                    JSONObject w = g.getJSONObject("medium");
                                    Url = w.getString("source_url");
                                }

                            }
                            news.add(new Home_News(Sub, Url, Id, Title, View, Like));
                        }
                        videoAdapter2.notifyDataSetChanged();
                        pro = pro + 1;
                        progress();
                    }
                } catch (JSONException e) {
                    //Error Code
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("image error", String.valueOf(error));
                // do something
            }
        });

        requestQueue.add(jsonArrayRequest);
    }


    public void font() {

        //Get file font
        Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSansMobile.ttf");

        txt_Home.setTypeface(typeface);
        txt_video_home.setTypeface(typeface);
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
        txt_News_home.setTypeface(typeface);

    }

    public void ID() {

        //sliding view
        bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);
        drawerlayout = (DrawerLayout) findViewById(R.id.root_page_1);
        txt_Home = (TextView) findViewById(R.id.txt_Home);
        txt_video_home = (TextView) findViewById(R.id.txt_video_home);
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
        txt_News_home = (TextView) findViewById(R.id.txt_News_home);
        btn_Menu = (ImageView) findViewById(R.id.btn_Menu);
        btn_Search = (ImageView) findViewById(R.id.btn_Search);
        home_Video = (RelativeLayout) findViewById(R.id.home_Video);
        txt_menu_home = (RelativeLayout) findViewById(R.id.txt_menu_home);
        txt_menu_mark = (RelativeLayout) findViewById(R.id.txt_menu_mark);
        txt_menu_post_me = (RelativeLayout) findViewById(R.id.txt_menu_post_me);
        txt_menu_profile = (RelativeLayout) findViewById(R.id.txt_menu_profile);
        txt_menu_support = (RelativeLayout) findViewById(R.id.txt_menu_support);
        txt_menu_about = (RelativeLayout) findViewById(R.id.txt_menu_about);
        home_News = (RelativeLayout) findViewById(R.id.home_News);
        btn_menu_post = (LinearLayout) findViewById(R.id.btn_menu_post);
        btn_menu_video = (LinearLayout) findViewById(R.id.btn_menu_video);
        btn_menu_home = (LinearLayout) findViewById(R.id.btn_menu_home);
        btn_menu_image = (LinearLayout) findViewById(R.id.btn_menu_image);
        btn_menu_buy = (LinearLayout) findViewById(R.id.btn_menu_buy);
        progress_wheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        recyclerView = (RecyclerView) findViewById(R.id.RecycleList_home_video);
        recyclerView2 = (RecyclerView) findViewById(R.id.RecycleList_home_News);

    }

    public boolean Net() {
        if (app_net.getInstance(Home.this).isOnline()) {
            return true;
        } else {
            return false;
        }
    }

    //Set Slider
    public void slider() {

        List<Banner> banners = new ArrayList<>();
        //add banner using image url
        banners.add(new RemoteBanner(url_Slider[0]));
        banners.add(new RemoteBanner(url_Slider[1]));
        banners.add(new RemoteBanner(url_Slider[2]));
        banners.add(new RemoteBanner(url_Slider[3]));
        banners.add(new RemoteBanner(url_Slider[4]));
        bannerSlider.setBanners(banners);

        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                //Toast.makeText(Home.this, "Banner with position " + String.valueOf(position) + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //get json Slider
    public void jsonSlider() {

        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        final String url = "https://facecar.ir/wp-content/themes/facecar/extra/proccess/php/restapi/api.php?getdata=sliderinfo";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String Url = null;
                            if (!jsonObject.isNull("app_url")) {
                                Url = jsonObject.getString("app_url");
                                Log.e("img",Url);
                            }
                            String more = null;
                            if (!jsonObject.isNull("more")) {
                                more = jsonObject.getString("more");
                            }

                            url_Slider[i] = Url;
                            url_Slider_more[i] = more;

                        }

                        //Set
                        slider();
                        pro = pro + 1;
                        progress();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the adapter.
        // Because the RecyclerView won't unregister the adapter, the
        // ViewHolders are very likely leaked.
        recyclerView.setAdapter(null);
        recyclerView2.setAdapter(null);

    }

    public void Click(){

        btn_menu_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Video = new Intent(Home.this,Sub.class);
                startActivity(Video);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn_menu_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Video = new Intent(Home.this,VideoList.class);
                startActivity(Video);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn_menu_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Video = new Intent(Home.this,Images.class);
                startActivity(Video);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn_menu_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Video = new Intent(Home.this,Shop.class);
                startActivity(Video);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        home_Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Video = new Intent(Home.this,VideoList.class);
                startActivity(Video);
                overridePendingTransition(0, 0);
                finish();
            }
        });

    }


    public void progress(){
        if (pro == 3){
            progress_wheel.setVisibility(View.GONE);
            home_Video.setVisibility(View.VISIBLE);
            home_News.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView2.setVisibility(View.VISIBLE);
            bannerSlider.setVisibility(View.VISIBLE);
        }else {
            progress_wheel.setVisibility(View.VISIBLE);
            home_Video.setVisibility(View.GONE);
            home_News.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            recyclerView2.setVisibility(View.GONE);
            bannerSlider.setVisibility(View.GONE);
        }
    }

}
