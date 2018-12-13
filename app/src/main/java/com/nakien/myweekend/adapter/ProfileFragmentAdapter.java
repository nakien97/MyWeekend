package com.nakien.myweekend.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nakien.myweekend.fragment.ProfileFragment;
import com.nakien.myweekend.fragment.TransHistoryFragment;

public class ProfileFragmentAdapter extends FragmentStatePagerAdapter {
    private String []tabTitle = {"Tài khoản", "Lịch sử"};
    private ProfileFragment profile;
    private TransHistoryFragment history;
    public ProfileFragmentAdapter(FragmentManager fm) {
        super(fm);
        profile = new ProfileFragment();
        history = new TransHistoryFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return profile;
        }
        if(position==1){
            return history;
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
