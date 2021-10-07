package com.fury.facecar;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fury.facecar.Adapter.Adapter_Home_Video;
import com.fury.facecar.Model.Home_Video;
import com.pnikosis.materialishprogress.ProgressWheel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.michaelbel.bottomsheet.BottomSheet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.VideoInfo;
import tcking.github.com.giraffeplayer2.VideoView;

/**
 * Created by FURY on 5/11/2018.
 */

public class VideoShow extends Activity {

    String yourDataObject = null;
    VideoView videoView;
    ImageView btnBack, btn_download_video, btn_share_video, null1;
    TextView txt_view_video, txt_like_video, txt_sub_video, txt_list_video_1;
    RecyclerView RecycleList_list_video_1;
    public static Activity here;
    private List<Home_Video> videos = new ArrayList<>();
    private Adapter_Home_Video videoAdapter;
    ProgressWheel progress_wheel;
    int pro = 0;
    String link, low, def, high, nameFile;
    int file_size = 0, file_size2 = 0, file_size3 = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoshow);

        here = this;

        setID();
        setTypeFace();
        Recycler();
        onClick();
        progress();

        boolean n = Net();
        if (n) {
            videoList1();
            getUrl();
        }else {

        }


    }

    public boolean Net() {
        if (app_net.getInstance(VideoShow.this).isOnline()) {
            return true;
        } else {
            return false;
        }
    }

    public void getUrl() {

        if (getIntent().hasExtra("KEY_UrlVideo")) {
            yourDataObject = getIntent().getStringExtra("KEY_UrlVideo");
            getDetailVideo(yourDataObject);
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + "KEY_UrlVideo");
        }

    }

    public void ShowVideo(String url, String text) {

        //standalone player
        videoView.setVideoPath(url).getPlayer();

        //standalone player
        VideoInfo videoInfo = new VideoInfo(Uri.parse(url))
                .setTitle(text)//config title
                .setShowTopBar(true); //show mediacontroller top bar

        GiraffePlayer.createPlayer(VideoShow.this, videoInfo);

    }

    public void getDetailVideo(String url) {

        Map<String, String> params = new HashMap();
        params.put("postView", "");
        params.put("postLike", "");

        JSONObject parameters = new JSONObject(params);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success

                String Title = null;
                String Sub = null;
                String view = null;
                String like = null;
                String Link = null;

                if (!response.isNull("title")) {

                    JSONObject d = null;
                    try {
                        d = response.getJSONObject("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Title = new String(d.getString("rendered").getBytes("ISO-8859-1"), "UTF-8");
                    try {
                        Title = d.getString("rendered");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                if (!response.isNull("guid")) {

                    JSONObject d = null;
                    try {
                        d = response.getJSONObject("guid");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Title = new String(d.getString("rendered").getBytes("ISO-8859-1"), "UTF-8");
                    try {
                        Link = d.getString("rendered");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                if (!response.isNull("content")) {

                    JSONObject d = null;
                    try {
                        d = response.getJSONObject("content");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Title = new String(d.getString("rendered").getBytes("ISO-8859-1"), "UTF-8");
                    try {
                        Sub = d.getString("rendered");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                if (!response.isNull("postView")) {

                    try {
                        view = response.getString("postView");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (!response.isNull("postLike")) {

                    try {
                        like = response.getString("postLike");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Title = String.valueOf(Html.fromHtml(Title, Html.FROM_HTML_MODE_COMPACT));
                    Sub = String.valueOf(Html.fromHtml(Sub, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    Title = String.valueOf(Html.fromHtml(Title));
                    Sub = String.valueOf(Html.fromHtml(Sub));
                }
                String substr,High,Low;
                try{
                    substr = Sub.substring(Sub.indexOf("#s-video-def#") + 13, Sub.indexOf("#e-video-def#"));
                }catch (Exception e){
                    substr = "";
                }
                try{
                    High = Sub.substring(Sub.indexOf("#s-video-high#") + 14, Sub.indexOf("#e-video-high#"));
                }catch (Exception e){
                    High = "";
                }
                try{
                    Low = Sub.substring(Sub.indexOf("#s-video-low#") + 13, Sub.indexOf("#e-video-low#"));
                }catch (Exception e){
                    Low = "";
                }


                String Def = substr;

                Log.e("url", substr);
                Log.e("url", Title);

                ShowVideo(substr, Title);
                try {
                    setText(view, like, Title, Link, Low, Def, High);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pro = pro + 1;
                progress();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure

            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);

    }

    public void setID() {

        videoView = (VideoView) findViewById(R.id.video_view);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btn_download_video = (ImageView) findViewById(R.id.btn_download_video);
        btn_share_video = (ImageView) findViewById(R.id.btn_share_video);
        null1 = (ImageView) findViewById(R.id.null1);
        txt_view_video = (TextView) findViewById(R.id.txt_view_video);
        txt_like_video = (TextView) findViewById(R.id.txt_like_video);
        txt_sub_video = (TextView) findViewById(R.id.txt_sub_video);
        txt_list_video_1 = (TextView) findViewById(R.id.txt_list_video_1);
        RecycleList_list_video_1 = (RecyclerView) findViewById(R.id.RecycleList_list_video_1);
        progress_wheel = (ProgressWheel) findViewById(R.id.progress_wheel);

    }

    public void setText(String View, String Like, String Sub, String Link, String Low, String Def, String High) throws IOException {

        txt_view_video.setText(View);
        txt_like_video.setText(Like);
        txt_sub_video.setText(Sub);
        link = Link;
        low = Low;
        def = Def;
        high = High;

        nameFile = Sub;

    }

    public void setTypeFace() {

        //Get file font
        Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSansMobile.ttf");

        txt_view_video.setTypeface(typeface);
        txt_like_video.setTypeface(typeface);
        txt_sub_video.setTypeface(typeface);

    }

    //recycler
    public void Recycler() {
        //Get Ready List video
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(true);
        RecycleList_list_video_1.setLayoutManager(layoutManager);
        RecycleList_list_video_1.setHasFixedSize(true);
        RecycleList_list_video_1.setItemViewCacheSize(5);
        RecycleList_list_video_1.setDrawingCacheEnabled(true);
        RecycleList_list_video_1.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        videoAdapter = new Adapter_Home_Video(videos, VideoShow.this, RecycleList_list_video_1);
        RecycleList_list_video_1.setAdapter(videoAdapter);
    }

    public void onClick() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        btn_download_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            // Explain to the user why we need to read the contacts
                        }

                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                        // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                        // app-defined int constant that should be quite unique

                        return;
                    }
                }

                String item[] = {"کیفیت پایین" , "کیفیت متوسط" , "کیفیت عالی" };

                BottomSheet.Builder builder = new BottomSheet.Builder(VideoShow.this);
                builder.setTitle("انتخاب کیفیت")
                        .setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String url;

                                if (which == 0) {
                                    url = low;
                                } else if (which == 1) {
                                    url = def;
                                } else {
                                    url = high;
                                }

                                new DownloadManagerHelper(VideoShow.this, url, Environment.DIRECTORY_DOWNLOADS)
                                        .showNotificationProgress()
                                        .setDownloadFileName( nameFile + ".mp4")
                                        .setRequestType("video/MP4")
                                        .setNotificationTitle("Download Video")
                                        .setNotificationDescription(nameFile)
                                        .setDownloadCompleteListener(new DownloadManagerHelper.DownloadCompleteListener() {
                                            @Override
                                            public void onDownloadComplete() {
                                                Toast.makeText(VideoShow.this, "دانلود به پایان رسید.", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setDownloadProgressListener(new DownloadManagerHelper.DownloadProgressListener() {
                                            @Override
                                            public void onDownloadProgressListener(int percent) {
                                                Log.i("LOG", "Percent is : " + percent);
                                            }
                                        })
                                        .startDownload();

                                Toast.makeText(VideoShow.this, "دانلود شروع شد", Toast.LENGTH_SHORT).show();

                            }
                        });
                builder.setFullWidth(true);
                builder.show();

            }
        });

        btn_share_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "FaceCar");
                i.putExtra(Intent.EXTRA_TEXT, " فیس کار: نقد و بررسی خودرو(مجموعه عکس، اخبار و ویدیو با زیرنویس اختصاصی) " + link);
                startActivity(Intent.createChooser(i, "Share URL"));

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the adapter.
        // Because the RecyclerView won't unregister the adapter, the
        // ViewHolders are very likely leaked.
        RecycleList_list_video_1.setAdapter(null);

    }

    public void videoList1() {
        RequestQueue requestQueue = Volley.newRequestQueue(VideoShow.this);
        String url = "https://facecar.ir/wp-json/wp/v2/posts?categories=17";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        //Get 10 Item After Loading

                        Random r = new Random();

                        int Rand[] = {r.nextInt((response.length()) + 1), r.nextInt((response.length()) + 1), r.nextInt((response.length()) + 1), r.nextInt((response.length()) + 1), r.nextInt((response.length()) + 1), r.nextInt((response.length()) + 1), r.nextInt((response.length()) + 1), r.nextInt((response.length()) + 1)};

                        int numberLoadItem = 8;

                        Log.e("rand", String.valueOf(Rand[2]));

                        for (int i = 0; i < numberLoadItem; i++) {

                            int a = Rand[i];

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


    public void progress() {
        if (pro == 2) {
            progress_wheel.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            btn_download_video.setVisibility(View.VISIBLE);
            btn_share_video.setVisibility(View.VISIBLE);
            txt_view_video.setVisibility(View.VISIBLE);
            txt_like_video.setVisibility(View.VISIBLE);
            txt_sub_video.setVisibility(View.VISIBLE);
            RecycleList_list_video_1.setVisibility(View.VISIBLE);
            txt_list_video_1.setVisibility(View.VISIBLE);
            null1.setVisibility(View.VISIBLE);
        } else {
            progress_wheel.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            btn_download_video.setVisibility(View.GONE);
            btn_share_video.setVisibility(View.GONE);
            txt_view_video.setVisibility(View.GONE);
            txt_like_video.setVisibility(View.GONE);
            txt_sub_video.setVisibility(View.GONE);
            RecycleList_list_video_1.setVisibility(View.GONE);
            txt_list_video_1.setVisibility(View.GONE);
            null1.setVisibility(View.GONE);
        }
    }


}
