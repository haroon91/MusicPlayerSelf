package com.innotree.musicplayerself.viewholder;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innotree.musicplayerself.MusicService;
import com.innotree.musicplayerself.R;
import com.innotree.musicplayerself.model.Song;
import com.innotree.musicplayerself.utility.Utility;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AP01 on 1/6/16.
 */
public class SongsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private List<Song> songsList = new ArrayList<>();
    private Song song;
    public static Song selectedSong;
    private View mHolderView;

    TextView songTitle, songArtist;
    CircleImageView songImage;
    RelativeLayout songLayout, moreLayout;

    public SongsViewHolder(View view) {
        super(view);

        mHolderView = view;
        songTitle = (TextView) mHolderView.findViewById(R.id.tv_song_title);
        songArtist = (TextView) mHolderView.findViewById(R.id.tv_song_artist);
        songImage = (CircleImageView) mHolderView.findViewById(R.id.iv_song_image);
        songLayout = (RelativeLayout) mHolderView.findViewById(R.id.rl_song);
        moreLayout = (RelativeLayout) mHolderView.findViewById(R.id.rl_more);

        songLayout.setOnClickListener(this);
        moreLayout.setOnClickListener(this);
    }

    public void setRow(Song song, List<Song> songsList){
        this.songsList = songsList;
        this.song = song;

        songTitle.setText(song.title);
        songArtist.setText(song.artist);
        ImageLoader.getInstance().displayImage(song.image, songImage, Utility.displayImageOptions);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rl_song:

                selectedSong = song;
                Bundle bundle = new Bundle();

                Intent intent = new Intent(mHolderView.getContext(), MusicService.class);

                intent.setAction(Utility.ACTION_PLAY);
                intent.putExtra("SONG_ID", songPositionInList(selectedSong));
                bundle.putSerializable("SONGS_LIST", (ArrayList<Song>)songsList);
                intent.putExtras(bundle);
                mHolderView.getContext().startService(intent);

                //start Song Activity
//                intent = new Intent(mHolderView.getContext(), SongActivity.class);
//                mHolderView.getContext().startActivity(intent);

                break;

            case R.id.rl_more:
                //more option
                break;

        }
    }

    private boolean isMusicServiceRunning(Class <?> serviceClass) {
        ActivityManager manager = (ActivityManager) mHolderView.getContext()
                                                    .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }

    private int songPositionInList (Song song) {
        for (int i=0; i<songsList.size(); i++){
            if (songsList.get(i).equals(song)){
                return i;
            }
        }
        return 0;
    }
}
