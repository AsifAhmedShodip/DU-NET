package com.example.aniomi.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link popuptab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link popuptab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class popuptab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public  static TabLayout tabLayout;
    public  static ViewPager viewPager;
    public  static int int_items= 2;
    public popuptab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment popuptab.
     */
    // TODO: Rename and change types and number of parameters
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab,null);
        tabLayout=(TabLayout)v.findViewById(R.id.tabs);
        viewPager=(ViewPager)v.findViewById(R.id.viewpager);
        //set an adpater

        viewPager.setAdapter(new ProfileAdapter( getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event

}
