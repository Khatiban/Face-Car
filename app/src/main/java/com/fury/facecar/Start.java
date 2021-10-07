package com.fury.facecar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Thread() {

            @Override
            public void run() {
                super.run();

                //Go to Page Slider
                Intent uou = new Intent(Start.this, Home.class);
                startActivity(uou);
                Start.this.finish();

            }
        }, 3000);

    }
}
