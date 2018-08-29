package com.example.pokestar.vaccineremind.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.pokestar.vaccineremind.R;

/**
 * Created by PokeStar on 2018/7/24.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private FragmentManager fm;
    private Fragment mFragment;


    protected abstract Fragment createFragment();

    //布局文件
    @LayoutRes
    public abstract int getLayoutResId();

    //fragment容器
    @IdRes
    public abstract int getContainerId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        fm = getSupportFragmentManager();
        mFragment = fm.findFragmentById(getContainerId());

        if (mFragment == null) {
            mFragment = createFragment();
            fm.beginTransaction()
                    .add(getContainerId(), mFragment)
                    .commit();
        }
        init();
    }


    public void init() {

    }

    public void replaceFragment(Fragment fragment) {
        if (mFragment == null ||
                !fragment.getClass().getName().equals(mFragment.getClass().getName())) {
            fm.beginTransaction()
                    .replace(getContainerId(), fragment)
                    .commit();
            mFragment = fragment;
        }
    }
    public void addFragment(Fragment fragment,String tag) {

        if(fragment != null){
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(getContainerId(), fragment,tag);
            transaction.commit();
        }
    }


    public void removeFragment(String tag) {
        if(fm.getBackStackEntryCount() > 1){
            Fragment fragment = fm.findFragmentByTag(tag);
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }

    }


}
