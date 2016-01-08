package com.innotree.musicplayerself.utility;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSession;

/**
 * Created by AP01 on 1/8/16.
 */
public class MusicController extends android.widget.MediaController {
    /**
     * Create a new MediaController from a session's token.
     *
     * @param context The caller's context.
     */
    public MusicController(Context context) {
        super(context);
    }

}
