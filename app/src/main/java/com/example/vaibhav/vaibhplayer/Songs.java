package com.example.vaibhav.vaibhplayer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vaibhav on 18-11-2017.
 */

public class Songs implements Parcelable {

    public Long songID, dateAdded;
    public   String songTitle, artist, songData;

    public  Songs(Long songID, String songTitle, String artist, String songData , Long dateAdded) {
        this.songID = songID;
        this.songTitle = songTitle;
        this.artist = artist;
        this.songData = songData;
        this.dateAdded = dateAdded;
    }

    protected Songs(Parcel in) {
        songTitle = in.readString();
        artist = in.readString();
        songData = in.readString();
    }

    public static final Creator<Songs> CREATOR = new Creator<Songs>() {
        @Override
        public Songs createFromParcel(Parcel in) {
            return new Songs(in);
        }

        @Override
        public Songs[] newArray(int size) {
            return new Songs[size];
        }
    };

    public Long getSongID() {
        return songID;
    }

    public void setSongID(Long songID) {
        this.songID = songID;
    }

    public Long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongData() {
        return songData;
    }

    public void setSongData(String songData) {
        this.songData = songData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songTitle);
        dest.writeString(artist);
        dest.writeString(songData);
    }
}
