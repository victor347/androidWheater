package com.vicsoft.wheater.wheater.fragment;



import android.app.Fragment;
import android.os.Bundle;


import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vicsoft.wheater.wheater.R;
import com.vicsoft.wheater.wheater.adapter.CityPagerAdapter;
import com.vicsoft.wheater.wheater.model.Cities;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityPagerFragment extends Fragment {

    private Cities mCities;

    private ViewPager mPager;

    public CityPagerFragment() {
        // Required empty public constructor
        mCities = new Cities();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_city_pager, container, false);

        mPager = (ViewPager) root.findViewById(R.id.view_pager);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                updateCityinfo(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPager.setAdapter(new CityPagerAdapter(getFragmentManager(), mCities));
        updateCityinfo(0);
        return root;
    }

    private void updateCityinfo(int position){

        if(getActivity() instanceof AppCompatActivity){
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            ActionBar actionBar = activity.getSupportActionBar();

            if(actionBar!=null){
                actionBar.setTitle(mCities.getCity(position).getName());
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_payer, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superClase = super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.previous){

            mPager.setCurrentItem(mPager.getCurrentItem()-1);
            return true;
        }
        else if(item.getItemId() == R.id.next){

            mPager.setCurrentItem(mPager.getCurrentItem()+1);
            return true;
        }

        return superClase;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuPrev = menu.findItem(R.id.previous);
        MenuItem menuNext = menu.findItem(R.id.next);

        menuPrev.setEnabled(mPager.getCurrentItem()>0);
        menuNext.setEnabled(mPager.getCurrentItem()<mCities.getCount()-1);
    }
}
