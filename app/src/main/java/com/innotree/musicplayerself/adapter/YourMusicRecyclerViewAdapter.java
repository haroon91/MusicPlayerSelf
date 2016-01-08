package com.innotree.musicplayerself.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innotree.musicplayerself.R;
import com.innotree.musicplayerself.activity.SongsListActivity;
import com.innotree.musicplayerself.viewholder.OnElementClickListener;
import com.innotree.musicplayerself.viewholder.YourMusicViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AP01 on 1/6/16.
 */
public class YourMusicRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnElementClickListener {

    private List<String> options = new ArrayList<>();
    private List<String> optionImages = new ArrayList<>();

    public YourMusicRecyclerViewAdapter(List<String> options, List<String> optionImages){
        this.options = options;
        this.optionImages = optionImages;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        YourMusicViewHolder musicViewHolder = new YourMusicViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_your_music, viewGroup, false));
        musicViewHolder.setOnElementClickListener(this);

        return musicViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        String option = options.get(position);
        String image = optionImages.get(position);

        ((YourMusicViewHolder) viewHolder).setRow(option, image);
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    @Override
    public void onElementClick(RecyclerView.ViewHolder viewHolder, View view) {
        onYourMusicViewHolderClick(viewHolder, view);
    }

    private void onYourMusicViewHolderClick(RecyclerView.ViewHolder viewHolder, View view) {

        Log.d("Position", String.valueOf(viewHolder.getAdapterPosition()));

        switch (viewHolder.getAdapterPosition()) {

            case 1:
                Intent intent = new Intent(view.getContext(), SongsListActivity.class);
                view.getContext().startActivity(intent);
                break;

        }
    }
}
