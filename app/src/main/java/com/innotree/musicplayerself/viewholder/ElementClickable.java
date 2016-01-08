package com.innotree.musicplayerself.viewholder;

import android.view.View;

/**
 * Created by AP01 on 1/6/16.
 */
public interface ElementClickable {
    /**
     * Click event on an element
     * @param elementClickListener answers and handles the click event
     * @param elementView which was clicked
     * @return false if click not performed properly
     */
    boolean performElementClick(OnElementClickListener elementClickListener, View elementView);

    /**
     * Set the up the listener to handle element click event
     * @param elementClickListener handles element click event
     */
    void setOnElementClickListener(OnElementClickListener elementClickListener);
}
