package com.example.aniomi.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SocialTab extends Fragment {

    public  static TabLayout tabLayout;
    public  static ViewPager viewPager;
    public  static int int_items= 4;
    public SocialTab() {
        // Required empty public constructor
    }

       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_social_tab, container, false);
           tabLayout=(TabLayout)v.findViewById(R.id.tabs);
           getActivity().setTitle("SOCIAL");
           viewPager=(ViewPager)v.findViewById(R.id.viewpager);
           //set an adpater

           viewPager.setAdapter(new SocialAdapter( getChildFragmentManager()));

           tabLayout.post(new Runnable() {
               @Override
               public void run() {
                   tabLayout.setupWithViewPager(viewPager);
               }
           });
           return v;
    }


}
