package com.innotree.musicplayerself.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.innotree.musicplayerself.MediaManager;
import com.innotree.musicplayerself.MusicService;
import com.innotree.musicplayerself.R;
import com.innotree.musicplayerself.model.Song;
import com.innotree.musicplayerself.utility.Utility;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class SongActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    CircleImageView playButton, pauseButton;
//    MediaController musicController;
    SeekBar seekBar;
    TextView songTitle, songArtist, songDuration, songCurrentDuration;
    Song currentSong;
    ImageView songImage;

    private Handler mHandler = new Handler();

    private Runnable mUpdatePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        songTitle = (TextView) findViewById(R.id.tv_song_title);
        songArtist = (TextView) findViewById(R.id.tv_song_artist);
        songImage = (ImageView) findViewById(R.id.iv_song_image);
        playButton = (CircleImageView) findViewById(R.id.iv_play_song);
        pauseButton = (CircleImageView) findViewById(R.id.iv_pause_song);
        songDuration = (TextView) findViewById(R.id.tv_song_duration);
        songCurrentDuration = (TextView) findViewById(R.id.tv_song_currentduraiton);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(this);

        playButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        songTitle.setSelected(true);

        mUpdatePlayer = new Runnable() {
            @Override
            public void run() {

                if (MusicService.mMediaPlayer.isPlaying()) {
                    pauseButton.setVisibility(View.VISIBLE);
                    playButton.setVisibility(View.INVISIBLE);
                }
                else {
                    pauseButton.setVisibility(View.INVISIBLE);
                    playButton.setVisibility(View.VISIBLE);
                }

                currentSong = MusicService.getCurrentPlayingSong();

                long totalDuration = MusicService.mMediaPlayer.getDuration();
                long currentDuration = MusicService.mMediaPlayer.getCurrentPosition();

                if (totalDuration >= Utility.ONE_HOUR) {
                    songDuration.setText((new SimpleDateFormat("hh:mm:ss")).format(totalDuration));
                    songCurrentDuration.setText((new SimpleDateFormat("hh:mm:ss")).format(currentDuration));
                } else {
                    songDuration.setText((new SimpleDateFormat("mm:ss")).format(totalDuration));
                    songCurrentDuration.setText((new SimpleDateFormat("mm:ss")).format(currentDuration));
                }

                int progress = (int) ((double) (currentDuration * 100 / totalDuration) );
                seekBar.setProgress(progress);

                songTitle.setText(currentSong.title);
                songArtist.setText(currentSong.artist);

                mHandler.postDelayed(this, 100);
            }
        };

        updateProgressBar();

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
                if (MediaManager.getInstance().mediaPlayerReady()){
                    pauseButton.setVisibility(View.VISIBLE);
                    playButton.setVisibility(View.INVISIBLE);
                    MediaManager.getInstance().play(this);
                }
                break;

            case R.id.iv_pause_song:
                //pause song
                if (MediaManager.getInstance().mediaPlayerReady() && MediaManager.getInstance().mediaPlayerPlaying()){
                    MediaManager.getInstance().pause(this);
                    pauseButton.setVisibility(View.INVISIBLE);
                    playButton.setVisibility(View.VISIBLE);
                }
                break;

        }
    }

    private void updateProgressBar() {
        mHandler.postDelayed(mUpdatePlayer, 100);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (MusicService.mMediaPlayer != null && fromUser) {
            MusicService.mMediaPlayer.seekTo(progress * 1000);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdatePlayer);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        mHandler.removeCallbacks(mUpdatePlayer);
        int totalDuration = MusicService.mMediaPlayer.getDuration();
        int currentPosition = Utility.progressToTimer(seekBar.getProgress(), totalDuration);

        MusicService.mMediaPlayer.seekTo(currentPosition);
        updateProgressBar();
    }
}
