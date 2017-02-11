package com.vicsoft.wheater.wheater.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vicsoft.wheater.wheater.R;
import com.vicsoft.wheater.wheater.model.Cities;
import com.vicsoft.wheater.wheater.model.City;

/**
 *
 */
public class CityListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnCitySelectedListener mOnCitySelectedListener;

    public void setOnCitySelectedListener(OnCitySelectedListener onCitySelectedListener) {
        mOnCitySelectedListener = onCitySelectedListener;
    }

    public CityListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CityListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CityListFragment newInstance(String param1, String param2) {
        CityListFragment fragment = new CityListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_city_list, container, false);

        ListView listView = (ListView) root.findViewById(R.id.list);

        final Cities cities = new Cities();

        ArrayAdapter<City> adapter = new ArrayAdapter<City>(getActivity(), R.layout.list_item_city, cities.getCities());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(mOnCitySelectedListener!=null)
                mOnCitySelectedListener.onCitySelected(cities.getCity(position), position);
            }
        });

        return root;
    }

    public interface OnCitySelectedListener {

     void onCitySelected(City city, int position);
    }

}
