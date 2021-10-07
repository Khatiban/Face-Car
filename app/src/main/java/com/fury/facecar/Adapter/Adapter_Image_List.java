package com.fury.facecar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fury.facecar.DownloadManagerHelper;
import com.fury.facecar.Images;
import com.fury.facecar.Model.Image_List;
import com.fury.facecar.R;
import com.fury.facecar.VideoShow;
import com.squareup.picasso.Picasso;

import net.alhazmy13.mediagallery.library.activity.MediaGallery;
import net.alhazmy13.mediagallery.library.views.MediaGalleryView;

import org.json.JSONException;
import org.json.JSONObject;
import org.michaelbel.bottomsheet.BottomSheet;
import org.michaelbel.bottomsheet.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by FURY on 4/2/2018.
 */

public class Adapter_Image_List extends RecyclerView.Adapter<Adapter_Image_List.VideoViewHolder> {

    List<Image_List> ImageList;
    Context context;
    int r = 1;
    boolean isLoading = false;
    OnLoadMoreLisenerImage onLoadMoreLisener;
    ArrayList<String> list;

    public Adapter_Image_List(List<Image_List> ImageList, Context context, RecyclerView recyclerView) {
        this.ImageList = ImageList;
        this.context = context;

        final StaggeredGridLayoutManager linearLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int total = linearLayoutManager.getItemCount();
                int lastVisible = 0;
                linearLayoutManager.findViewByPosition(lastVisible);
                if (isLoading == false && total <= lastVisible + 3) {
                    if (onLoadMoreLisener != null) {
                        onLoadMoreLisener.OnLoadMore();
                    }
                    isLoading = true;
                }
            }
        });

    }

    public void setOnLoadMoreLisener(OnLoadMoreLisenerImage onLoadMoreLisener) {
        this.onLoadMoreLisener = onLoadMoreLisener;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public Adapter_Image_List.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);


        return new Adapter_Image_List.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_Image_List.VideoViewHolder holder, final int position) {

        final Image_List video = ImageList.get(position);

        final String id = video.getId();
        final String url = video.getUrl();
        final String view = video.getView();

        Picasso.with(context)
                .load(video.getUrlImage())
                .error(R.drawable.shadow_1)
                .placeholder(R.drawable.shadow_1)
                .into(holder.Image);

        holder.Image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                int[] items = new int[]{
                        R.string.like,
                        R.string.download,
                        R.string.share
                };

                int[] icons = new int[]{
                        R.drawable.heart_on,
                        R.drawable.icondownload,
                        R.drawable.iconshare
                };

                BottomSheet.Builder builder = new BottomSheet.Builder(context);
                builder.setDarkTheme(false);
                builder.setWindowDimming(0);
                builder.setDividers(true);
                builder.setFullWidth(true);
                builder.setTitle("تعداد بازدید : " + view);
                builder.setCellHeight(Utils.dp(context, 48));
                builder.setItems(items, icons, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Map<String, String> params = new HashMap();
                                    params.put("postView", "");
                                    params.put("postLike", "");

                                    JSONObject parameters = new JSONObject(params);
                                    final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, "https://facecar.ir/wp-content/themes/facecar/extra/proccess/php/rating.php?likepost=" + id + "&type=like", parameters, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            //TODO: handle success
                                            Toast.makeText(context, "سپاس", Toast.LENGTH_SHORT).show();
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            //TODO: handle failure
                                            Toast.makeText(context, "سپاس", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    Volley.newRequestQueue(context).add(jsonRequest);

                                } else if (which == 1) {

                                    if (list == null) {

                                        Toast.makeText(context, "لطفا قبل از دانلود بازدید کنید.", Toast.LENGTH_SHORT).show();

                                    } else {

                                           for(int i=0; list.size() > i; i++){
                                               String uuid = UUID.randomUUID().toString();
                                               new DownloadManagerHelper(Images.aty, list.get(i), Environment.DIRECTORY_DOWNLOADS)
                                                       .showNotificationProgress()
                                                       .setDownloadFileName(uuid + ".jpg")
                                                       .setRequestType("image/JPG")
                                                       .setNotificationTitle("Download Video")
                                                       .setNotificationDescription(uuid)
                                                       .setDownloadCompleteListener(new DownloadManagerHelper.DownloadCompleteListener() {
                                                           @Override
                                                           public void onDownloadComplete() {
                                                           }
                                                       })
                                                       .setDownloadProgressListener(new DownloadManagerHelper.DownloadProgressListener() {
                                                           @Override
                                                           public void onDownloadProgressListener(int percent) {
                                                               Log.i("LOG", "Percent is : " + percent);
                                                           }
                                                       })
                                                       .startDownload();
                                           }

                                        Toast.makeText(context, "دانلود شروع شد", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Intent i = new Intent(Intent.ACTION_SEND);
                                    i.setType("text/plain");
                                    i.putExtra(Intent.EXTRA_SUBJECT, "فیس کار بزرگ ترین مرجع ویدیو ، عکس و اخبار ماشین FaceCar.ir ");
                                    i.putExtra(Intent.EXTRA_TEXT, url);
                                    context.startActivity(Intent.createChooser(i, "Share URL"));
                                }
                            }
                        }
                );
                builder.show();

                return false;
            }
        });

        holder.Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Title = String.valueOf(Html.fromHtml(Title, Html.FROM_HTML_MODE_COMPACT));
                            Sub = String.valueOf(Html.fromHtml(Sub, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            Title = String.valueOf(Html.fromHtml(Title));
                            Sub = String.valueOf(Html.fromHtml(Sub));
                        }

                        String substr = Sub.substring(Sub.indexOf("#s-img-div#") + 11, Sub.indexOf("#e-img-div#"));

                        String[] strings = substr.split("#e-img-link#");

                        StringBuilder all = new StringBuilder();

                        int j = strings.length;

                        for (int s = 0; s <= j; s++) {
                            try {
                                all.append(strings[s]);
                            } catch (Exception e) {
                                Log.e("Exception", String.valueOf(e));
                            }
                        }

                        String[] strings3 = all.toString().split("#s-img-link#");

                        int po = strings3.length;

                        strings3[0] = strings3[po - 1];

                        ArrayList<String> ar = new ArrayList<String>();

                        for (int s = 0; s <= strings3.length; s++) {
                            try {
                                String b = strings3[s];
                                URL myURL = new URL(b);
                                ar.add(String.valueOf(myURL));
                            } catch (Exception e) {
                                Log.e("Exception", String.valueOf(e));
                            }
                        }

                        //ArrayList<String> list;
                        list = ar;

                        Log.e("list", list.toString());

                        MediaGallery.Builder((Activity) context, list)
                                .title(Title)
                                .backgroundColor(R.color.colorAccent)
                                .placeHolder(R.drawable.icon_menu_image_on)
                                .selectedImagePosition(1)
                                .show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //TODO: handle failure

                    }
                });


                Volley.newRequestQueue(context).add(jsonRequest);

            }
        });
    }


    @Override
    public int getItemCount() {
        return ImageList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView Image;

        public VideoViewHolder(View itemView) {
            super(itemView);
            Image = (ImageView) itemView.findViewById(R.id.images);
        }
    }
}
