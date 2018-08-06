package com.example.pokestar.vaccineremind.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by PokeStar on 2018/7/24.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    private FragmentManager fm;
    private Fragment mFragment;


    protected abstract Fragment createFragment();

    @LayoutRes
    public abstract int getLayoutResId();

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

    public void switchFragment(Fragment fragment) {
        if (mFragment == null ||
                !fragment.getClass().getName().equals(mFragment.getClass().getName())) {
            fm.beginTransaction()
                    .replace(getContainerId(), fragment)
                    .commit();
            mFragment = fragment;
        }
    }


}
