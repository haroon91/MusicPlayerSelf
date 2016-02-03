package com.innotree.musicplayerself.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innotree.musicplayerself.MediaManager;
import com.innotree.musicplayerself.MusicService;
import com.innotree.musicplayerself.MyApp;
import com.innotree.musicplayerself.R;
import com.innotree.musicplayerself.adapter.SongsRecyclerViewAdapter;
import com.innotree.musicplayerself.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SongsListActivity extends BaseActivity implements View.OnClickListener{

    private List<Song> songsList = new ArrayList<>();
    private SongsRecyclerViewAdapter mSongsRecyclerViewAdapter;

    TextView songNowPlaying, artistNowPlaying;
    ImageView pause, play;
    View nowplaying;
    ProgressBar seekBar;
    RelativeLayout layoutNowPlaying;

    MyApp myApp;

    private Runnable nowPlayingRunnable;
    private Handler mHandler = new Handler();

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // search for songs on restore
        searchSongs(null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);

        myApp = (MyApp) this.getApplication();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_songs);
        mSongsRecyclerViewAdapter = new SongsRecyclerViewAdapter(songsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mSongsRecyclerViewAdapter);

        getSongsList();

        nowplaying = findViewById(R.id.view_nowplaying);

        songNowPlaying = (TextView) nowplaying.findViewById(R.id.tv_nowplaying_song);
        artistNowPlaying = (TextView) nowplaying.findViewById(R.id.tv_nowplaying_artist);
        pause = (ImageView) nowplaying.findViewById(R.id.iv_pause);
        play = (ImageView) nowplaying.findViewById(R.id.iv_play);
        seekBar = (ProgressBar) nowplaying.findViewById(R.id.seekbar_nowplaying);
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.sea_green), PorterDuff.Mode.SRC_IN);

        seekBar.setMax(100);

        nowPlayingRunnable = new Runnable() {
            @Override
            public void run() {
                if (myApp.nowPlaying) {
                    customizeNowPlayingLayout();
                }
                else {
                    nowplaying.setVisibility(View.GONE);
                }
                mHandler.postDelayed(this, 100);
            }
        };

        mHandler.post(nowPlayingRunnable);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        nowplaying.setOnClickListener(this);

    }

    public void searchSongs(View v) {
    }

    public void getSongsList() {

        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get Columns
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            int durationColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            int genreColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.TITLE); //TODO genre
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int imageColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);
            int fileColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);


            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisDuration = musicCursor.getString(durationColumn);
                String thisGenre = musicCursor.getString(genreColumn);
                String thisAlbum = musicCursor.getString(albumColumn);
                String image = musicCursor.getString(imageColumn);
                String filename = musicCursor.getString(fileColumn);
                songsList.add(new Song(thisId, thisTitle, thisArtist, thisDuration, thisGenre, thisAlbum, image, filename));
            }
            while (musicCursor.moveToNext());
        }

        if (musicCursor != null) {
            musicCursor.close();
        }

        Collections.sort(songsList, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.title.compareTo(b.title);
            }
        });

        mSongsRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_songs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    public void customizeNowPlayingLayout() {

        if (myApp.nowPlaying) {
            nowplaying.setVisibility(View.VISIBLE);
            songNowPlaying.setText(MusicService.getCurrentPlayingSong().title);
            artistNowPlaying.setText(MusicService.getCurrentPlayingSong().artist);

            long totalDuration = MusicService.mMediaPlayer.getDuration();
            long currentDuration = MusicService.mMediaPlayer.getCurrentPosition();

            int progress = (int) ((double) (currentDuration * 100 / totalDuration) );
            seekBar.setProgress(progress);
        }
        else {
            nowplaying.setVisibility(View.GONE);
        }

        pause.setVisibility(myApp.playerPaused ? View.INVISIBLE : View.VISIBLE);
        play.setVisibility(myApp.playerPaused ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.view_nowplaying:
                Intent intent = new Intent(this, SongActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_pause:
                if (MusicService.mMediaPlayer != null && MusicService.mMediaPlayer.isPlaying()) {
                    pause.setVisibility(View.INVISIBLE);
                    play.setVisibility(View.VISIBLE);
                    MediaManager.getInstance().pause(this);
                }
                break;

            case R.id.iv_play:
                if (MusicService.mMediaPlayer != null){
                    pause.setVisibility(View.VISIBLE);
                    play.setVisibility(View.INVISIBLE);
                    MediaManager.getInstance().play(this);
                }
                break;

        }
    }
}
