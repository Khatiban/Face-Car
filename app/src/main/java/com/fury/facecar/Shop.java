package com.fury.facecar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by FURY on 5/12/2018.
 */

public class Shop extends Activity {

    TextView comeSoon, btn_menu_buy_text, btn_menu_image_text, btn_menu_home_text, btn_menu_video_text, btn_menu_post_text;
    LinearLayout btn_menu_post, btn_menu_video, btn_menu_home, btn_menu_image, btn_menu_buy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shop);

        comeSoon = (TextView)findViewById(R.id.comeSoon);

        btn_menu_post = (LinearLayout) findViewById(R.id.btn_menu_post);
        btn_menu_video = (LinearLayout) findViewById(R.id.btn_menu_video);
        btn_menu_home = (LinearLayout) findViewById(R.id.btn_menu_home);
        btn_menu_image = (LinearLayout) findViewById(R.id.btn_menu_image);
        btn_menu_buy = (LinearLayout) findViewById(R.id.btn_menu_buy);

        btn_menu_buy_text = (TextView) findViewById(R.id.btn_menu_buy_text);
        btn_menu_image_text = (TextView) findViewById(R.id.btn_menu_image_text);
        btn_menu_home_text = (TextView) findViewById(R.id.btn_menu_home_text);
        btn_menu_video_text = (TextView) findViewById(R.id.btn_menu_video_text);
        btn_menu_post_text = (TextView) findViewById(R.id.btn_menu_post_text);


        //Get file font
        Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSansMobile.ttf");

        btn_menu_buy_text.setTypeface(typeface);
        btn_menu_image_text.setTypeface(typeface);
        btn_menu_home_text.setTypeface(typeface);
        btn_menu_video_text.setTypeface(typeface);
        btn_menu_post_text.setTypeface(typeface);


        btn_menu_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Video = new Intent(Shop.this,VideoList.class);
                startActivity(Video);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn_menu_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Video = new Intent(Shop.this,Images.class);
                startActivity(Video);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn_menu_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Video = new Intent(Shop.this,Home.class);
                startActivity(Video);
                overridePendingTransition(0, 0);
                finish();
            }
        });

    }
}
