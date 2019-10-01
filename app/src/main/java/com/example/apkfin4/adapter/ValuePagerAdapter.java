package com.example.apkfin4.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.apkfin4.R;
import com.example.apkfin4.ui.Favorite.FilmFavoriteFragment;
import com.example.apkfin4.ui.Favorite.TvShowFavoriteFragment;

public class ValuePagerAdapter  extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB = new int[] {R.string.title_home,R.string.title_dashboard};
    private final Context context;

    public ValuePagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FilmFavoriteFragment();
                break;
            case 1:
                fragment = new TvShowFavoriteFragment();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle (int position) {
        return context.getResources().getString(TAB[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
