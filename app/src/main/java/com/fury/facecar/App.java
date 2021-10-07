package com.fury.facecar;

import android.app.Application;


/**
 * Created by FURY on 4/3/2018.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * IMPORTANT! Enable the configuration below, if you expect to open really large images.
         * Also you can add the {@code android:largeHeap="true"} to Manifest file to avoid an OOM error.*/
    }

}
