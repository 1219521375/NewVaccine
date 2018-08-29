package com.example.pokestar.vaccineremind.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokestar.vaccineremind.ui.activity.BaseActivity;

/**
 * Created by PokeStar on 2018/8/14.
 */

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;


    //获取布局文件ID
    protected abstract int getLayoutId();

    //获取布局文件ID
    protected abstract int getContainerId();

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    public void replaceFragment(Fragment fragment) {
        if (fragment != null ) {
            getHoldingActivity().getSupportFragmentManager().beginTransaction()
                    .replace(getContainerId(), fragment)
                    .commit();
        }
    }
    public void addFragment(Fragment fragment,String tag) {

        if(fragment != null){
            FragmentTransaction transaction = getHoldingActivity().getSupportFragmentManager().beginTransaction();
            transaction.add(getContainerId(), fragment,tag);
            transaction.commit();
        }
    }


    public void removeFragment(String tag) {
        FragmentManager fm = getHoldingActivity().getSupportFragmentManager();
        if(fm.getBackStackEntryCount() > 1){
            Fragment fragment = fm.findFragmentByTag(tag);
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }

    }

}
