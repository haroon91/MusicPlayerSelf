package com.innotree.musicplayerself.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.innotree.musicplayerself.NavigationDrawerFragment;
import com.innotree.musicplayerself.R;
import com.innotree.musicplayerself.utility.Utility;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by AP01 on 1/5/16.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resourceId;
    private List<String> choices;
    private List<String> images;

    public NavigationDrawerAdapter(Context context, int resourceId, List<String> choices, List<String> images) {
        super(context, resourceId, choices);

        this.context = context;
        this.resourceId = resourceId;
        this.choices = choices;
        this.images = images;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(resourceId, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.tv_silde_item_text);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_slide_item_image);
        View selector = convertView.findViewById(R.id.view_selector);

        if (position == NavigationDrawerFragment.mCurrentSelectedPosition){
            selector.setVisibility(View.VISIBLE);
        }
        else {
            selector.setVisibility(View.INVISIBLE);
        }

        textView.setText(choices.get(position));
        ImageLoader.getInstance().displayImage(images.get(position), imageView, Utility.displayImageOptions);
        return convertView;
    }
}
