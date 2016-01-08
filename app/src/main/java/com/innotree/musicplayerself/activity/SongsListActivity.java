package com.innotree.musicplayerself.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.innotree.musicplayerself.R;
import com.innotree.musicplayerself.adapter.SongsRecyclerViewAdapter;
import com.innotree.musicplayerself.model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SongsListActivity extends BaseActivity {

    private List<Song> songsList = new ArrayList<>();
    private SongsRecyclerViewAdapter mSongsRecyclerViewAdapter;
    File musicFolder;

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_songs);
        mSongsRecyclerViewAdapter = new SongsRecyclerViewAdapter(songsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mSongsRecyclerViewAdapter);

        getSongsList();

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
            public int compare (Song a, Song b) {
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
}
