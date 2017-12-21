package com.example.vaibhav.vaibhplayer.fragments;

// main screen fragment(as song list will be displayed and now playing)
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vaibhav.vaibhplayer.R;
import com.example.vaibhav.vaibhplayer.Songs;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreenFragment extends Fragment {
    RelativeLayout nowPlayingBottomBar=null;
    ImageButton playPauseButton = null;
    TextView songTitle=null;
    RelativeLayout visibleLayout=null;
    RelativeLayout noSongs=null;
    RecyclerView recyclerView=null;
    Activity myActivity=null;




    public MainScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_main_screen, container, false);
        noSongs=(RelativeLayout) view.findViewById(R.id.noSongs);
        nowPlayingBottomBar=(RelativeLayout) view.findViewById(R.id.hiddenBarMainScreen);
        songTitle=(TextView) view.findViewById(R.id.songTitleMainScreen);
        playPauseButton=(ImageButton) view.findViewById(R.id.playPauseButton);
        recyclerView=(RecyclerView) view.findViewById(R.id.contentMain);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myActivity=(Activity)context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity=activity;
    }
    public ArrayList<Songs> getSongsFromPhone(){
        ArrayList<Songs> arrayList=new ArrayList<Songs>();
        ContentResolver contentResolver=myActivity.getContentResolver();
        Uri songuri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor= contentResolver.query(songuri,null,null,null,null);
        if((songCursor != null) && songCursor.moveToFirst() ) {
            int songId=songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist =songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int dateIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED);
            while (songCursor.moveToNext()){
               long currentId= songCursor.getLong(songId);
                String currentTitle= songCursor.getString(songTitle);
                String currentArtist= songCursor.getString(songArtist);
                String currentData= songCursor.getString(songData);
                long currentDate= songCursor.getLong(dateIndex);
                arrayList.add(new Songs(currentId,currentTitle,currentArtist,currentData,currentDate));


            }



        }
        return arrayList;
    }
}
