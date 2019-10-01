package com.example.apkfin4.ui.Favorite;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apkfin4.R;
import com.example.apkfin4.adapter.ValuePagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle bundle) {
        super.onViewCreated(v,bundle);
        viewPager = v.findViewById(R.id.viewPager);
        tabLayout = v.findViewById(R.id.tabMode);
        ValuePagerAdapter pager = new ValuePagerAdapter(getContext(),getChildFragmentManager());
        viewPager.setAdapter(pager);
        tabLayout.setupWithViewPager(viewPager);}

}
