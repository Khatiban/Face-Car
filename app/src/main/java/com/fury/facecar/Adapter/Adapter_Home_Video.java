package com.fury.facecar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fury.facecar.Home;
import com.fury.facecar.Model.Home_Video;
import com.fury.facecar.R;
import com.fury.facecar.VideoShow;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by FURY on 2/2/2018.
 */

public class Adapter_Home_Video  extends RecyclerView.Adapter<Adapter_Home_Video.VideoViewHolder> {

    List<Home_Video> videoList;
    Context context;
    int r = 1;
    boolean isLoading = false;

    public Adapter_Home_Video(List<Home_Video> videoList, Context context, RecyclerView recyclerView) {
        this.videoList = videoList;
        this.context = context;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int total = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_video,parent,false);


        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        //Get file font
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile.ttf");

        final Home_Video video = videoList.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            holder.Subject.setText(Html.fromHtml(video.getSub(), Html.FROM_HTML_MODE_COMPACT));
        }else {
            holder.Subject.setText(Html.fromHtml(video.getSub()));
        }

        //Set Font
        holder.Subject.setTypeface(typeface);

        Picasso.with(context)
                .load(video.getUrl_image())
                .fit()
                .error(R.drawable.shadow_1)
                .placeholder(R.drawable.shadow_1)
                .into(holder.Image);
        holder.btnItemVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to Show Recipe

                if (VideoShow.here == null){
                    Intent show = new Intent(context, VideoShow.class);
                    String id = String.valueOf(video.getUrlJson());
                    show.putExtra("KEY_UrlVideo", id);
                    context.startActivity(show);
                }else {

                    VideoShow.here.finish();

                    Intent show = new Intent(context, VideoShow.class);
                    String id = String.valueOf(video.getUrlJson());
                    show.putExtra("KEY_UrlVideo", id);
                    context.startActivity(show);

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        TextView Subject;
        PorterShapeImageView Image;
        LinearLayout btnItemVideo;

        public VideoViewHolder(View itemView) {
            super(itemView);
            Image = (PorterShapeImageView)itemView.findViewById(R.id.picItemHomeVideo);
            Subject = (TextView)itemView.findViewById(R.id.txtItemHomeVideo);
            btnItemVideo = (LinearLayout) itemView.findViewById(R.id.btnItemHomeVideo);
        }
    }
}
