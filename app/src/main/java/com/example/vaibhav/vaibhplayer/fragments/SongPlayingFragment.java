package com.example.vaibhav.vaibhplayer.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cleveroad.audiovisualization.AudioVisualization;
import com.cleveroad.audiovisualization.DbmHandler;
import com.cleveroad.audiovisualization.GLAudioVisualizationView;
import com.cleveroad.audiovisualization.SpeechRecognizerDbmHandler;
import com.cleveroad.audiovisualization.VisualizerDbmHandler;
import com.example.vaibhav.vaibhplayer.CurrentSongHelper;
import com.example.vaibhav.vaibhplayer.R;
import com.example.vaibhav.vaibhplayer.Songs;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import static android.R.attr.contextUri;
import static android.R.attr.key;
import static android.os.Build.VERSION_CODES.M;
import static com.example.vaibhav.vaibhplayer.R.id.starTime;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongPlayingFragment extends Fragment {
    MediaPlayer mediaplayer=null;
    AudioVisualization audioVisualization=null;
    GLAudioVisualizationView glView=null;
    Activity myActivity=null;
    TextView startTimeText=null;
    TextView endTimeText=null;
    ImageButton playpauseImageButton=null;
    ImageButton previousImageButton=null;
    ImageButton nextImageButton=null;
    ImageButton loopImageButton=null;
    ImageButton shuffleImageButton=null;
    TextView songArtistView=null;
    TextView songTitleView=null;
    SeekBar seekbar=null;
    CurrentSongHelper currentSongHelper=null;
    Integer currentPosition=0;
    ArrayList<Songs> fetchSongs=null;
    android.os.Handler handler=new android.os.Handler();
    Runnable updateSongTime=new Runnable() {
        @Override
        public void run() {
            int getcurrent=mediaplayer.getCurrentPosition();
            startTimeText.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(getcurrent),
            TimeUnit.MILLISECONDS.toSeconds(getcurrent)-TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getcurrent))));

           handler.postDelayed(this,1000);
        }

    };



    public SongPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_song_playing, container, false);
        seekbar=(SeekBar)getView().findViewById(R.id.seekBar);
        startTimeText=(TextView)getView().findViewById(starTime);
        endTimeText=(TextView)getView().findViewById(R.id.endTime);
        songArtistView=(TextView)getView().findViewById(R.id.songArtist);
        songTitleView=(TextView)getView().findViewById(R.id.songTitle);
        playpauseImageButton=(ImageButton)getView().findViewById(R.id.playPauseButton);
        nextImageButton=(ImageButton)getView().findViewById(R.id.nextButton);
        previousImageButton=(ImageButton)getView().findViewById(R.id.previousButton);
        loopImageButton=(ImageButton)getView().findViewById(R.id.loopButton);
        shuffleImageButton=(ImageButton)getView().findViewById(R.id.shuffleButton);

        glView=(GLAudioVisualizationView) getView().findViewById(R.id.visualizer_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        audioVisualization = (AudioVisualization) glView;
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
    @Override
    public void onResume() {
        super.onResume();
        audioVisualization.onResume();
    }

    @Override
    public void onPause() {
        audioVisualization.onPause();
        super.onPause();
    }
    @Override
    public void onDestroyView() {
        audioVisualization.release();
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String path = null;
        String songTitle=null;

        currentSongHelper=new CurrentSongHelper();
        currentSongHelper.isPlaying=true;
        currentSongHelper.isLoop=false;
        currentSongHelper.isShuffle=false;

        String songArtist=null;
        Long songId=null;
        try{
            path= getArguments().getString("path");
            songArtist=getArguments().getString("songArtist");
            songId=(long)getArguments().getInt("songId");
            currentPosition=getArguments().getInt("songPosition");
            fetchSongs=getArguments().getParcelableArrayList("songData");
            currentSongHelper.songPath=path;
            currentSongHelper.songTitle=songTitle;
            currentSongHelper.songArtist=songArtist;
            currentSongHelper.songId=songId;
            currentSongHelper.currentPosition=currentPosition;

            updateTextViews((String)currentSongHelper.songTitle,(String)currentSongHelper.songArtist);

        }catch (Exception e){e.printStackTrace();}
        mediaplayer=new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaplayer.setDataSource(myActivity, Uri.parse(path));
        }catch(Exception e){e.printStackTrace();}
        mediaplayer.start();
        processInformation(mediaplayer);


        if((boolean)currentSongHelper.isPlaying){
            playpauseImageButton.setBackgroundResource(R.drawable.pause_icon);
        }
        else {
            playpauseImageButton.setBackgroundResource(R.drawable.play_icon);
        }

        VisualizerDbmHandler vizualizerHandler = DbmHandler.Factory.newVisualizerHandler((Context)myActivity, 0);
        audioVisualization.linkTo(vizualizerHandler);

        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onSongComplete();
            }
        });
        clickHandler();
    }

    public void clickHandler(){
        shuffleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((boolean)currentSongHelper.isShuffle){
                    shuffleImageButton.setBackgroundResource(R.drawable.shuffle_white_icon);
                    currentSongHelper.isShuffle=false;
                }
                else {
                    shuffleImageButton.setBackgroundResource(R.drawable.shuffle_icon);
                    currentSongHelper.isShuffle=true;
                    currentSongHelper.isLoop=false;
                    loopImageButton.setBackgroundResource(R.drawable.loop_white_icon);
                }
            }
        });

        nextImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSongHelper.isPlaying=true;
                if((boolean)currentSongHelper.isShuffle){
                    playnext("PlayNextLikeNormalShuffle");
                }
                else{
                    playnext("PlayNextNormal");
                }

            }
        });
        previousImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSongHelper.isPlaying=true;
                if((boolean)currentSongHelper.isLoop){
                    loopImageButton.setBackgroundResource(R.drawable.loop_white_icon);
                }
                playPrevious();
            }
        });


        loopImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((boolean)currentSongHelper.isLoop){
                    currentSongHelper.isLoop=false;
                    loopImageButton.setBackgroundResource(R.drawable.loop_white_icon);
                }
                else{
                    currentSongHelper.isLoop=true;
                    currentSongHelper.isShuffle=false;
                    loopImageButton.setBackgroundResource(R.drawable.loop_icon);
                    shuffleImageButton.setBackgroundResource(R.drawable.shuffle_white_icon);

                }

            }
        });


        playpauseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((boolean) mediaplayer.isPlaying()){
                    mediaplayer.pause();
                    currentSongHelper.isPlaying=false;
                    playpauseImageButton.setBackgroundResource(R.drawable.play_icon);
                }
                else{
                    mediaplayer.start();
                    currentSongHelper.isPlaying=true;
                    playpauseImageButton.setBackgroundResource(R.drawable.pause_icon);
                }
            }
        });
    }
    public void playnext(String check){
        if(check.equals("PlayNextNormal")){
            currentPosition=currentPosition+1;
        }
        else if(check.equals("PlayNextLikeNormalShuffle")){
            Random randomObject=new Random();
            int randomPositon=randomObject.nextInt(( fetchSongs.size()+1));
            currentPosition=randomPositon;

        }
        if (currentPosition==fetchSongs.size()) {
            currentPosition=0;
        }
        currentSongHelper.isLoop=false;
        Songs nextSong = fetchSongs.get(currentPosition);
        currentSongHelper.songTitle=nextSong.songTitle;
        currentSongHelper.songPath=nextSong.songData;
        currentSongHelper.songId=(Long)nextSong.songID;
        currentSongHelper.currentPosition=currentPosition;

        updateTextViews((String)currentSongHelper.songTitle,(String)currentSongHelper.songArtist);

        mediaplayer.reset();
        try{
          mediaplayer.setDataSource(myActivity,Uri.parse(currentSongHelper.songPath));
          mediaplayer.prepare();
          mediaplayer.start();
            processInformation(mediaplayer);
        }catch (Exception e){e.printStackTrace();}



    }
    public void playPrevious(){
        currentPosition=currentPosition-1;
        if(currentPosition==-1){
            currentPosition=0;
        }
        if((boolean)currentSongHelper.isPlaying){
            playpauseImageButton.setBackgroundResource(R.drawable.pause_icon);
        }
        else {
            playpauseImageButton.setBackgroundResource(R.drawable.play_icon);
        }
        currentSongHelper.isLoop=false;
        Songs nextSong = fetchSongs.get(currentPosition);
        currentSongHelper.songTitle=nextSong.songTitle;
        currentSongHelper.songPath=nextSong.songData;
        currentSongHelper.songId=(Long)nextSong.songID;
        currentSongHelper.currentPosition=currentPosition;

        updateTextViews((String)currentSongHelper.songTitle,(String)currentSongHelper.songArtist);

        mediaplayer.reset();
        try{
            mediaplayer.setDataSource(myActivity,Uri.parse(currentSongHelper.songPath));
            mediaplayer.prepare();
            mediaplayer.start();
            processInformation(mediaplayer);
        }catch (Exception e){e.printStackTrace();}

    }
    public void onSongComplete(){
        if((boolean)currentSongHelper.isShuffle){
            playnext("PlayNextLikeNormalShuffle");
            currentSongHelper.isPlaying=true;
        }
        else{
            if((boolean)currentSongHelper.isLoop){
                currentSongHelper.isPlaying=true;
                Songs nextSong = fetchSongs.get(currentPosition);
                currentSongHelper.songTitle=nextSong.songTitle;
                currentSongHelper.songPath=nextSong.songData;
                currentSongHelper.songId=(Long)nextSong.songID;
                currentSongHelper.currentPosition=currentPosition;

                updateTextViews((String)currentSongHelper.songTitle,(String)currentSongHelper.songArtist);

                mediaplayer.reset();
                try{
                    mediaplayer.setDataSource(myActivity,Uri.parse(currentSongHelper.songPath));
                    mediaplayer.prepare();
                    mediaplayer.start();
                    processInformation(mediaplayer);
                }catch (Exception e){e.printStackTrace();}


            }
            else{
                playnext("PlayNextNormal");
                currentSongHelper.isPlaying=true;
            }
        }
    }
    public void updateTextViews(String songtitle,String songArtist){
         songTitleView.setText(songtitle);
        songArtistView.setText(songArtist);
    }
    public void processInformation(MediaPlayer mediaplayer){
        int finalTime=mediaplayer.getDuration();
        int startTime=mediaplayer.getCurrentPosition();
        seekbar.setMax(finalTime);
        startTimeText.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(startTime),
                TimeUnit.MILLISECONDS.toSeconds(startTime)-TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime))));

        endTimeText.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(finalTime),
                TimeUnit.MILLISECONDS.toSeconds(finalTime)-TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finalTime))));
         seekbar.setProgress(startTime);
        handler.postDelayed(updateSongTime,1000);
    }

}



