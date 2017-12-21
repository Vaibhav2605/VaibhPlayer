package com.example.vaibhav.vaibhplayer.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.vaibhav.vaibhplayer.R;
import com.example.vaibhav.vaibhplayer.fragments.MainScreenFragment;

import static android.R.interpolator.linear;
import static android.os.Build.VERSION_CODES.N;

public class MainActivity extends AppCompatActivity
         {
             @Override
             protected void onCreate( Bundle savedInstanceState) {
                 super.onCreate(savedInstanceState);
                 setContentView(R.layout.activity_main);
                 Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                 setSupportActionBar(toolbar);

                 DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                 ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                         this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                 drawer.setDrawerListener(toggle);
                 toggle.syncState();

                 MainScreenFragment mainScreenFragment=new MainScreenFragment();
                 // to make the fragment appear on the screen
                 this.getSupportFragmentManager()
                         .beginTransaction()
                         .add(R.id.details_fragment,mainScreenFragment,"MainScreenFragment")
                         .commit();
                 RecyclerView navigation_recycler_view=(RecyclerView)findViewById(R.id.navview);
                 LinearLayoutManager layoutManager
                         = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                 navigation_recycler_view.setLayoutManager(layoutManager);


             }



             @Override
             protected void onStart() {
                 super.onStart();
             }

         }

