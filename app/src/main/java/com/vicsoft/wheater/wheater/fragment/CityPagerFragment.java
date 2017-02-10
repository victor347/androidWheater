package com.vicsoft.wheater.wheater.fragment;


import android.app.Fragment;
import android.os.Bundle;


import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vicsoft.wheater.wheater.R;
import com.vicsoft.wheater.wheater.adapter.CityPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityPagerFragment extends Fragment {


    public CityPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_city_pager, container, false);

        ViewPager pager = (ViewPager) root.findViewById(R.id.view_pager);

        pager.setAdapter(new CityPagerAdapter(getFragmentManager()));

        return root;
    }



}
