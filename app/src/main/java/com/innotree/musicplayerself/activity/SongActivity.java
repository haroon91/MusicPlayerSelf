package com.innotree.musicplayerself.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.innotree.musicplayerself.MusicService;
import com.innotree.musicplayerself.R;
import com.innotree.musicplayerself.model.Song;
import com.innotree.musicplayerself.utility.MusicController;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

public class SongActivity extends BaseActivity implements View.OnClickListener, MediaController.MediaPlayerControl {

    CircleImageView playButton, pauseButton;
    MediaController musicController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        TextView songTitle = (TextView) findViewById(R.id.tv_song_title);
        TextView songArtist = (TextView) findViewById(R.id.tv_song_artist);
        ImageView songImage = (ImageView) findViewById(R.id.iv_song_image);
        playButton = (CircleImageView) findViewById(R.id.iv_play_song);
        pauseButton = (CircleImageView) findViewById(R.id.iv_pause_song);
        musicController = (MediaController) findViewById(R.id.mediaController);


//        playButton.setOnClickListener(this);
//        pauseButton.setOnClickListener(this);
        setController();
        songTitle.setSelected(true);

        Song currentSong = MusicService.getCurrentPlayingSong();

        songTitle.setText(currentSong.title);
        songArtist.setText(currentSong.artist);
//        ImageLoader.getInstance().displayImage(currentSong.image, songImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_song, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_play_song:
                //play song
                if (MusicService.mMediaPlayer != null){
                    pauseButton.setVisibility(View.VISIBLE);
                    playButton.setVisibility(View.INVISIBLE);
                }
                break;

            case R.id.iv_pause_song:
                //pause song
                if (MusicService.mMediaPlayer != null && MusicService.mMediaPlayer.isPlaying()){
                    MusicService.mMediaPlayer.pause();
                    pauseButton.setVisibility(View.INVISIBLE);
                    playButton.setVisibility(View.VISIBLE);
                }
                break;

        }
    }

    private void setController() {

        musicController.setMediaPlayer(this);
        musicController.setAnchorView(findViewById(R.id.rl_song_layout));
        musicController.setEnabled(true);

        musicController.setVisibility(View.VISIBLE);

    }

    @Override
    public void start() {
    }

    @Override
    public void pause() {
        MusicService.mMediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
