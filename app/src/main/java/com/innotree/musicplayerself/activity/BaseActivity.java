package com.innotree.musicplayerself.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.innotree.musicplayerself.MyApp;
import com.innotree.musicplayerself.R;
import com.innotree.musicplayerself.utility.Utility;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by AP01 on 1/5/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private MyApp m_myApp;
    protected Context mContext;

    private boolean inited = false;


    public BaseActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        m_myApp = (MyApp) this.getApplication();

        initializeIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("ActivityLifeCycle", this + " onResume()");

//        m_myApp.setCurrentActivity(this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("ActivityLifeCycle", this + " onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("ActivityLifeCycle", this + " onDestroy()");
    }

    public void initializeIfNeeded() {
        if (!inited) {

            //init for image loader
            File cacheDir = StorageUtils.getCacheDirectory(this);
            ImageLoaderConfiguration config = (new ImageLoaderConfiguration.Builder(this))
                    .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                    .memoryCacheSize(2 * 1024 * 1024)
                    .diskCache(new UnlimitedDiscCache(cacheDir))
                    .diskCacheSize(50 * 1024 * 1024)
                    .diskCacheFileCount(100)
                    .build();
            ImageLoader.getInstance().init(config);

            //If fetch image fails, show the white bird icon
            Utility.displayImageOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .showImageForEmptyUri(R.drawable.icon)
                    .showImageOnFail(R.drawable.icon)
                    .build();

            //If fetch image fails, show the default empty icon
            Utility.userIconOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .showImageForEmptyUri(R.drawable.icon)
                    .showImageOnFail(R.drawable.icon)
                    .build();

            inited = true;
        }
    }


}
