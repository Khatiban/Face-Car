package com.fury.facecar.Adapter;

import android.content.Context;
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

import com.fury.facecar.Model.Home_News;
import com.fury.facecar.Model.Home_Video;
import com.fury.facecar.R;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by FURY on 2/2/2018.
 */

public class Adapter_Home_News extends RecyclerView.Adapter<Adapter_Home_News.VideoViewHolder> {

    List<Home_News> videoList;
    Context context;
    int r = 1;
    boolean isLoading = false;

    public Adapter_Home_News(List<Home_News> videoList, Context context, RecyclerView recyclerView) {
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_news,parent,false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        //Get file font
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile.ttf");

        final Home_News video = videoList.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            holder.Subject.setText(Html.fromHtml(video.getSub(), Html.FROM_HTML_MODE_COMPACT));
            holder.tit.setText(Html.fromHtml(video.getTit(), Html.FROM_HTML_MODE_COMPACT));
        }else {
            holder.Subject.setText(Html.fromHtml(video.getSub()));
            holder.tit.setText(Html.fromHtml(video.getTit()));
        }
        holder.view.setText(video.getView());
        holder.like.setText(video.getLike());

        //Set Font
        holder.Subject.setTypeface(typeface);

        Picasso.with(context)
                .load(video.getUrl_image())
                .fit()
                .error(R.drawable.shadow_1)
                .placeholder(R.drawable.shadow_1)
                .into(holder.Image);
        holder.btnItemNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to Show Recipe
                /**Intent show = new Intent(context, Recipe.class);
                String id = String.valueOf(video.getId_post());
                show.putExtra("ID", id);
                context.startActivity(show);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        TextView Subject;
        TextView tit;
        TextView view;
        TextView like;
        PorterShapeImageView Image;
        RelativeLayout btnItemNews;

        public VideoViewHolder(View itemView) {
            super(itemView);
            Image = (PorterShapeImageView)itemView.findViewById(R.id.picItemHomeNews);
            view = (TextView)itemView.findViewById(R.id.txtItemHomeNewsView);
            like = (TextView)itemView.findViewById(R.id.txtItemHomeNewsLike);
            tit = (TextView)itemView.findViewById(R.id.txtItemHomeNewsTit);
            Subject = (TextView)itemView.findViewById(R.id.txtItemHomeNewsSub);
            btnItemNews = (RelativeLayout) itemView.findViewById(R.id.btnItemHomeNews);
        }
    }
}
