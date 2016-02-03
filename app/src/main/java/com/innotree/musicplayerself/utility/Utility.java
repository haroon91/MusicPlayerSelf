package com.innotree.musicplayerself.utility;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by AP01 on 1/5/16.
 */
public class Utility {

    public static final int ONE_HOUR = 3600000;

    public static DisplayImageOptions displayImageOptions;
    public static DisplayImageOptions userIconOptions;

    public static final String ACTION_PLAY = "com.innotree.action.PLAY";
    public static final String ACTION_PAUSE = "com.innotree.action.PAUSE";

    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = totalDuration / 1000;
        currentDuration = (int) ((((double)progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

}
