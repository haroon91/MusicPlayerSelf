package com.innotree.musicplayerself.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.innotree.musicplayerself.R;
import com.innotree.musicplayerself.model.Song;
import com.innotree.musicplayerself.viewholder.SongsViewHolder;

import java.util.List;

/**
 * Created by AP01 on 1/6/16.
 */
public class SongsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Song> songsList;

    public SongsRecyclerViewAdapter(List<Song> songsList) {
        this.songsList = songsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        return new SongsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_song, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Song song = songsList.get(position);

        ((SongsViewHolder) viewHolder).setRow(song, songsList);
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }
}
