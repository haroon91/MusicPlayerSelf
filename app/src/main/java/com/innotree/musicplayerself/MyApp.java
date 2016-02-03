package com.innotree.musicplayerself;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

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
public class MyApp extends Application {

    private static final String TAG = "MyApp";

    private boolean inited = false;

    private Activity currentActivity = null;

    private Context mContext;

    public boolean nowPlaying;
    public boolean playerPaused;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

//        BroadcastReceiver m_nowPlayingReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Log.v("OnReceive", "Show NowPlaying Layout");
//            }
//        };

//        registerReceiver(m_nowPlayingReceiver, new IntentFilter(MusicService.BROADCAST_MEDIAPLAYER_PLAYING));

    }

    public Activity getCurrentActivity() { return currentActivity; }

    public boolean isInBackground() { return ( currentActivity == null ); }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
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
