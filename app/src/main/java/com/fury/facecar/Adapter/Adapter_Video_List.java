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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fury.facecar.Model.Home_Video;
import com.fury.facecar.Model.Video_List;
import com.fury.facecar.R;
import com.fury.facecar.VideoShow;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by FURY on 2/2/2018.
 */

public class Adapter_Video_List extends RecyclerView.Adapter<Adapter_Video_List.VideoViewHolder> {

    List<Video_List> videoList;
    Context context;
    int r = 1;
    boolean isLoading = false;
    OnLoadMoreLisener onLoadMoreLisener;

    public Adapter_Video_List(List<Video_List> videoList, Context context, RecyclerView recyclerView) {
        this.videoList = videoList;
        this.context = context;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int total = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastVisibleItemPosition();
                if (isLoading == false && total <= lastVisible + 0){
                    if (onLoadMoreLisener != null){
                        onLoadMoreLisener.OnLoadMore();
                    }
                    isLoading = true;
                }
            }
        });

    }

    public void setOnLoadMoreLisener(OnLoadMoreLisener onLoadMoreLisener) {
        this.onLoadMoreLisener = onLoadMoreLisener;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_list,parent,false);


        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        //Get file font
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile.ttf");

        final Video_List video = videoList.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            holder.Subject.setText(Html.fromHtml(video.getTitle(), Html.FROM_HTML_MODE_COMPACT));
            holder.Like.setText(Html.fromHtml(video.getLike(), Html.FROM_HTML_MODE_COMPACT));
            holder.View.setText(Html.fromHtml(video.getView(), Html.FROM_HTML_MODE_COMPACT));
        }else {
            holder.Subject.setText(Html.fromHtml(video.getTitle()));
            holder.Like.setText(Html.fromHtml(video.getLike()));
            holder.View.setText(Html.fromHtml(video.getView()));
        }

        //Set Font
        holder.Subject.setTypeface(typeface);
        holder.Like.setTypeface(typeface);
        holder.View.setTypeface(typeface);

        Picasso.with(context)
                .load(video.getUrl())
                .fit()
                .error(R.drawable.shadow_1)
                .placeholder(R.drawable.shadow_1)
                .into(holder.Image);
        holder.btnItemVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to Show Recipe
                Intent show = new Intent(context, VideoShow.class);
                String id = String.valueOf(video.geturljson());
                show.putExtra("KEY_UrlVideo", id);
                context.startActivity(show);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        TextView Subject,View,Like;
        PorterShapeImageView Image;
        RelativeLayout btnItemVideo;

        public VideoViewHolder(View itemView) {
            super(itemView);
            Image = (PorterShapeImageView)itemView.findViewById(R.id.img_Item_Video_List);
            Subject = (TextView)itemView.findViewById(R.id.txt_Item_Video_List);
            View = (TextView)itemView.findViewById(R.id.txtItemVideoListView);
            Like = (TextView)itemView.findViewById(R.id.txtItemVideoListLike);
            btnItemVideo = (RelativeLayout) itemView.findViewById(R.id.btnItemVideoList);
        }
    }
}
