package com.example.vaibhav.vaibhplayer.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vaibhav.vaibhplayer.R;
import com.example.vaibhav.vaibhplayer.activities.MainActivity;
import com.example.vaibhav.vaibhplayer.fragments.AboutUsFragment;
import com.example.vaibhav.vaibhplayer.fragments.FavouriteFragment;
import com.example.vaibhav.vaibhplayer.fragments.MainScreenFragment;
import com.example.vaibhav.vaibhplayer.fragments.SettingsFragment;

import java.util.ArrayList;

/**
 * Created by vaibhav on 14-11-2017.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.NavViewHolder>
{
    ArrayList<String> contentlist=null;
    ArrayList<Integer> getImages=null;
    Context nContext=null;
    public NavigationDrawerAdapter(ArrayList<String> _contentlist,ArrayList<Integer> _getImages,Context _context){
        this.contentlist=_contentlist;
        this.getImages=_getImages;
        this.nContext=_context;
      }



    @Override
    public NavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_custom_navigationdrawer,parent,false);
        return new NavViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(NavViewHolder holder, final int position) {
        holder.icon_GET.setBackgroundResource(getImages.get(position));
        holder.text_GET.setText(contentlist.get(position));
        holder.contentHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==0)
                {
                    MainScreenFragment mainScreenFragment=new MainScreenFragment();
                    ((MainActivity)nContext).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.details_fragment,mainScreenFragment)
                            .commit();
               }
               else if (position==1){
                    FavouriteFragment favouriteFragment=new FavouriteFragment();
                    ((MainActivity)nContext).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.details_fragment,favouriteFragment)
                            .commit();

                }
                else if(position ==2){
                    SettingsFragment settingsFragment=new SettingsFragment();
                    ((MainActivity)nContext).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.details_fragment,settingsFragment)
                            .commit();
                }
                else{
                    AboutUsFragment aboutUsFragment=new AboutUsFragment();
                    ((MainActivity)nContext).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.details_fragment,aboutUsFragment)
                            .commit();
                }

            }
        });






    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NavViewHolder extends RecyclerView.ViewHolder {


        ImageView icon_GET=null;
        TextView text_GET=null;
        RelativeLayout contentHolder = null;
        public NavViewHolder(View itemView) {
            super(itemView);
            icon_GET = (ImageView) itemView.findViewById(R.id.icon_navdrawer );
            text_GET=(TextView)itemView.findViewById(R.id.text_navdrawer);
            contentHolder=(RelativeLayout)itemView.findViewById(R.id.navdrawer_item_content_holder);
        }

    }
}
