package com.innotree.musicplayerself;

import android.content.Context;
import android.content.Intent;

import com.innotree.musicplayerself.utility.Utility;

/**
 * Created by AP01 on 2/3/16.
 */
public class MediaManager {

    private static MediaManager instance;
    private Intent intent;

    private MediaManager(){

    }

    public static MediaManager getInstance() {
        if (instance == null){
            instance = new MediaManager();
        }
        return instance;
    }

    public boolean mediaPlayerReady () {
        return MusicService.mMediaPlayer != null;
    }

    public boolean mediaPlayerPlaying () {
        return MusicService.mMediaPlayer.isPlaying();
    }

    public void play (Context context){
        intent = new Intent(context, MusicService.class);
        intent.setAction(Utility.ACTION_PLAY);
        context.startService(intent);
    }

    public void pause (Context context) {
        intent = new Intent(context, MusicService.class);
        intent.setAction(Utility.ACTION_PAUSE);
        context.startService(intent);
    }


}
