package com.example.pokestar.vaccineremind.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PokeStar on 2018/8/20.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fmList=new ArrayList<Fragment>();

    public FragmentAdapter(FragmentManager fm, List<Fragment>fmList) {
        super(fm);
        this.fmList=fmList;
    }

    @Override
    public Fragment getItem(int position) {
        return fmList.get(position);
    }

    @Override
    public int getCount() {
        if(fmList==null){
            return 0;
        }else{
            return fmList.size();
        }
    }
}
