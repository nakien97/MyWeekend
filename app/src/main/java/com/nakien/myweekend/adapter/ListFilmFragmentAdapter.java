package com.nakien.myweekend.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nakien.myweekend.fragment.ComingSoonFilmFragment;
import com.nakien.myweekend.fragment.NowShowingFilmFragment;

public class ListFilmFragmentAdapter extends FragmentStatePagerAdapter{
    private String []tabTitle = {"đang chiếu", "sắp chiếu"};
    private ComingSoonFilmFragment comingSoonFilm;
    private NowShowingFilmFragment nowShowingFilm;

    public ListFilmFragmentAdapter(FragmentManager fm) {
        super(fm);
        comingSoonFilm = new ComingSoonFilmFragment();
        nowShowingFilm = new NowShowingFilmFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return nowShowingFilm;
        }
        if(position==1){
            return comingSoonFilm;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
