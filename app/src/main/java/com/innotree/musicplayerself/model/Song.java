package com.innotree.musicplayerself.model;

import java.io.Serializable;

/**
 * Created by AP01 on 1/6/16.
 */
public class Song implements Serializable {

    public long songId;
    public String title;
    public String artist;
    public String duration;
    public String genre;
    public String album;
    public String image;
    public String filename;

    public Song(long songId, String title, String artist, String duration,
                String album, String genre, String image, String filename) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.genre = genre;
        this.album = album;
        this.image = image;
        this.filename = filename;
    }
}
