package com.innotree.musicplayerself;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;

import com.innotree.musicplayerself.model.Song;
import com.innotree.musicplayerself.utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
                                                     MediaPlayer.OnErrorListener,
                                                     MediaPlayer.OnCompletionListener {

    public static MediaPlayer mMediaPlayer = null;

    public static List<Song> songsList; //current songs list
    private static int songPos; //current song playing position in list

    public static MusicService instance = null;

    MyApp m_myApp;

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        songPos = 0;

        initMusicPlayer();
        m_myApp = (MyApp) this.getApplication();

    }

    private void initMusicPlayer() {
        mMediaPlayer = new MediaPlayer();

        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (intent.getExtras() != null) {
            retrieveSongList(intent);
            songPos = intent.getIntExtra("SONG_ID", 0);
        }

        if (intent.getAction().equals(Utility.ACTION_PLAY)) {

            if (m_myApp.playerPaused){
                resumeSong();
            }
            else {
                playSong();
            }
        }

        else if (intent.getAction().equals(Utility.ACTION_PAUSE)) {
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.pause();
                m_myApp.playerPaused = true;
            }
        }

        instance = MusicService.this;

        return START_NOT_STICKY;
    }


    private void retrieveSongList(Intent intent) {

        Bundle b = intent.getExtras();
        songsList = (ArrayList<Song>) b.getSerializable("SONGS_LIST");

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onPrepared(MediaPlayer player) {
        player.start();
    }

    @Override
    public void onCompletion(MediaPlayer player) {
        //can play next song if available
        if (mMediaPlayer.getCurrentPosition() > 0) {
            mMediaPlayer.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // ... react appropriately ...
        // The MediaPlayer has moved to the Error state, must be reset!
        return false;
    }

    private void playSong() {
        mMediaPlayer.reset();

        Song playSong = songsList.get(songPos);

        try {
            mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(playSong.filename));
        } catch (IOException e) {
            e.printStackTrace();
            //not found
        }

        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.prepareAsync(); //prepare async to not block main thread

        m_myApp.nowPlaying = true;
        m_myApp.playerPaused = false;
    }

    public void resumeSong(){

        int length = mMediaPlayer.getCurrentPosition();

        if (length > 0) {
            mMediaPlayer.seekTo(length);
            mMediaPlayer.start();
        }
        else {
            playNext();
        }

        m_myApp.nowPlaying = true;
        m_myApp.playerPaused = false;
    }

    private void playNext() {
        songPos++;
        if (songPos >= songsList.size()){
            songPos = 0;
        }
        playSong();
    }

    public static Song getCurrentPlayingSong() {
        return songsList.get(songPos);
    }

//    private void broadcastSongUpdate() {
//        sendBroadcast(mediaIntent);
//    }


}
