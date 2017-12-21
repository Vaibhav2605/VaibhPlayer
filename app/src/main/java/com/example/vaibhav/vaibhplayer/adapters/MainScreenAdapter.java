package com.example.vaibhav.vaibhplayer.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaibhav.vaibhplayer.R;
import com.example.vaibhav.vaibhplayer.Songs;
import com.example.vaibhav.vaibhplayer.activities.MainActivity;
import com.example.vaibhav.vaibhplayer.fragments.MainScreenFragment;
import com.example.vaibhav.vaibhplayer.fragments.SongPlayingFragment;

import java.util.ArrayList;

/**
 * Created by vaibhav on 22-11-2017.
 */

public class MainScreenAdapter extends RecyclerView.Adapter<MainScreenAdapter.MyViewHolder> {

    ArrayList<Songs> songDetails = new ArrayList<Songs>();
    Context nContext = null;

    MainScreenAdapter(ArrayList<Songs> _songDetails, Context _context){
        this.songDetails=_songDetails;
        this.nContext=_context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_custom_mainscreen_adapter,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Songs songObject=songDetails.get(position);
        holder.trackArtist.setText(songObject.artist);
        holder.trackTitle.setText(songObject.songTitle);
        holder.contentHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongPlayingFragment songPlayingFragment=new SongPlayingFragment();
                Bundle args=new Bundle();
                args.putString("songArtist",songObject.artist);
                args.putString("songTitle",songObject.songTitle);
                args.putString("path",songObject.songData);
                args.putInt("songPosition",position);
                args.putInt("songID", Integer.parseInt(songObject.songData));
                args.putParcelableArrayList("songData",songDetails);
                songPlayingFragment.setArguments(args);
                ((FragmentActivity)nContext).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.details_fragment,songPlayingFragment)
                        .commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        if(songDetails==null){return 0;}
        else{return (int)songDetails.size();}
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView trackTitle=null;
        TextView trackArtist = null;
        RelativeLayout contentHolder = null;
        public MyViewHolder(View itemView) {
            super(itemView);
            trackTitle=(TextView)itemView.findViewById(R.id.trackTitle);
            trackArtist=(TextView)itemView.findViewById(R.id.trackArtist);
            contentHolder =(RelativeLayout)itemView.findViewById(R.id.contentRow);
        }
    }
}
