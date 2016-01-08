package com.innotree.musicplayerself.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.innotree.musicplayerself.R;
import com.innotree.musicplayerself.utility.Utility;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by AP01 on 1/6/16.
 */
public class YourMusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ElementClickable {

    private View mHolderView;
    private TextView textView;
    private ImageView textIcon, selectIcon;

    private OnElementClickListener onElementClickListener;

    public YourMusicViewHolder(View view) {
        super(view);

        mHolderView = view;
        textView = (TextView) mHolderView.findViewById(R.id.tv_list_text);
        textIcon = (ImageView) mHolderView.findViewById(R.id.iv_list_image);
        selectIcon = (ImageView) mHolderView.findViewById(R.id.iv_list_select);

        mHolderView.setOnClickListener(this);
    }

    public void setRow(String option, String image) {
        textView.setText(option);
        ImageLoader.getInstance().displayImage(image, textIcon, Utility.displayImageOptions);
    }

    @Override
    public void setOnElementClickListener(OnElementClickListener listener) {
        if (listener != null) onElementClickListener = listener;
    }

    @Override
    public boolean performElementClick(OnElementClickListener elementClickListener, View elementView) {
        if (elementClickListener != null && elementView != null) {
            elementClickListener.onElementClick(this, elementView);
            return true;
        }

        return false;
    }

    @Override
    public void onClick(View view) {
        this.performElementClick(onElementClickListener, view);
    }
}
